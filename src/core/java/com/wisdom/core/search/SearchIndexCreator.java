package com.wisdom.core.search;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.wisdom.core.annotation.Index;
import com.wisdom.core.annotation.TextWord;
import com.wisdom.core.utils.BeanUtils;
import com.wisdom.core.utils.HtmlUtils;
/**
 * 功能描述：利用Lucene建立文件索引，将大文本信息存入lucene，建立索引后可以快速检索数据库。
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-6</b>
 * <br>创建时间：<b>下午06:00:01</b>
 * <br>文件结构：<b>spring:com.wisdom.core.search/SearchIndexCreator.java</b>
 */
public class SearchIndexCreator {
	private static Logger logger = LoggerFactory.getLogger(SearchIndexCreator.class);
	public static String INDEX_PATH = null;
	
	public static String getPath(){
		if(INDEX_PATH==null){
			INDEX_PATH=ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getRealPath("/").concat("//WEB-INF//index//");
		}
		return INDEX_PATH;
	}
	
	/**
	 * 功能描述：创建索引文件
	 * <br>@param bean对象实体，用于装载数据
	 */
	public synchronized static void createIndex(Object bean){
		IndexWriter writer=null;
		Directory directory=null;
		boolean isRecreate=false;
		try{
			directory=FSDirectory.open(new File(getPath()+getIndexName(bean.getClass())+"//"));
			Analyzer analyzer=new IKAnalyzer();
	        try{
	        	writer = new IndexWriter(directory,analyzer, isRecreate,IndexWriter.MaxFieldLength.LIMITED);
	        }catch (Exception e) {
	        	isRecreate=true;
	        	writer = new IndexWriter(directory,analyzer, isRecreate,IndexWriter.MaxFieldLength.LIMITED);
			}
            Document doc=new Document();
            String kekwordName=getKeywordName(bean.getClass());
            String kekwordValue=BeanUtils.getFieldValue(bean, kekwordName).toString();
            Field keyField=new Field(kekwordName,kekwordValue,Field.Store.YES, Field.Index.NOT_ANALYZED);
            doc.add(keyField);
           	PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
    		for (int i = 0; i < descriptors.length; i++) {
    			String propertyName = descriptors[i].getName();
    			if(isTextWord(bean.getClass(),propertyName)){
    				String propertyValue=BeanUtils.getFieldValue(bean, propertyName).toString();
    				if(propertyValue==null||propertyValue.trim().isEmpty()){
    					continue;
    				}
    				if(isFilterHtmlTags(bean.getClass(),propertyName)){
    					propertyValue = HtmlUtils.Html2Text(propertyValue);
    				}
    				Field textField=new Field(propertyName,propertyValue, Field.Store.YES, Field.Index.ANALYZED);
    				doc.add(textField);
    			}
    		}
            writer.addDocument(doc);
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("索引文件建立错误{}",e.getMessage());
	    }
		try{writer.optimize();}catch (Exception e) { logger.error("索引写入器优化错误{}",e.getMessage());}
		try{writer.close();}catch (Exception e) { logger.error("索引写入器关闭错误{}",e.getMessage());}
		try{directory.close();}catch (Exception e) { logger.error("索引目录关闭错误{}",e.getMessage());}
	}
	
	/**
	 * 功能描述：删除索引文件
	 * <br>@param bean实体，用于装载数据
	 */
	public synchronized static void removeIndex(Object bean){
		IndexWriter writer=null;
		Directory directory=null;
		try{
			directory = FSDirectory.open(new File(getPath()+getIndexName(bean.getClass())+"//"));
			Analyzer analyzer=new IKAnalyzer();
			writer = new IndexWriter(directory,analyzer, false,IndexWriter.MaxFieldLength.LIMITED);
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
    		for (int i = 0; i < descriptors.length; i++) {
    			String propertyName = descriptors[i].getName();
    			if(isTextWord(bean.getClass(),propertyName)){
    				String propertyValue=BeanUtils.getFieldValue(bean, propertyName).toString();
    				if(propertyValue==null||propertyValue.trim().isEmpty()){
    					continue;
    				}
    				if(isFilterHtmlTags(bean.getClass(),propertyName)){
    					propertyValue = HtmlUtils.Html2Text(propertyValue);
    				}
    				Term term=new Term(propertyName, propertyValue);
        	        writer.deleteDocuments(term);
    			}
    		}
	    }catch(Exception e){
	    	logger.error("删除索引文件错误{}",e.getMessage());
	    }
		try{writer.optimize();}catch (Exception e) { logger.error("索引写入器优化错误{}",e.getMessage());}
		try{writer.close();}catch (Exception e) { logger.error("索引写入器关闭错误{}",e.getMessage());}
		try{directory.close();}catch (Exception e) { logger.error("索引目录关闭错误{}",e.getMessage());}
	}
	
	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合，如果查询范围较广，检索结果较多，只返回100个.
	 * BooleanClause.Occur.MUST，BooleanClause.Occur.MUST_NOT，BooleanClause.Occur.SHOULD。有以下6种组合：   
     * A．MUST和MUST：取得连个查询子句的交集。   
     * B．MUST和MUST_NOT：表示查询结果中不能包含MUST_NOT所对应得查询子句的检索结果。   
     * C．MUST_NOT和MUST_NOT：无意义，检索无结果。   
     * D．SHOULD与MUST、SHOULD与MUST_NOT：SHOULD与MUST连用时，无意义，结果为MUST子句的检索结果。与MUST_NOT连用时，功能同MUST。   
     * E．SHOULD与SHOULD：表示“或”关系，最终检索结果为所有检索子句的并集。   
	 * <br>@param clazz 实体类型
	 * <br>@param queryText 文本内容
	 * <br>@param textPropertyNames 文本检索bean属性名组
	 * <br>@return 唯一索引即主键值集合
	 */
	@SuppressWarnings("unchecked")
	public synchronized static Collection<Object> searchKeyword(Class clazz,String queryText,String... textPropertyNames){
		Collection<Object> ids=new LinkedList<Object>();
		IndexReader reader=null;
		Searcher searcher=null;
		Directory directory=null;
		try{
			directory=FSDirectory.open(new File(getPath()+getIndexName(clazz)+"//"));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);
			searcher.setSimilarity(new IKSimilarity());
			Query query = IKQueryParser.parseMultiField(textPropertyNames, queryText);
			TopDocs topDocs = searcher.search(query, 100); 
			ScoreDoc[] hits = topDocs.scoreDocs;
			for(int i=0;i< hits.length;i++){ 
			    int DocId = hits[i].doc;
			    Document doc = searcher.doc(DocId);
			    ids.add(doc.get(getKeywordName(clazz)));
			}
		}catch(Exception e){
	    	 logger.error("索引错误{}",e.getMessage());
		}
		try{searcher.close();}catch (Exception e) { logger.error("查询器关闭错误{}",e.getMessage());}
		try{reader.flush();}catch (Exception e) { logger.error("索引读取器栈清空错误{}",e.getMessage());}
		try{reader.close();}catch (Exception e) { logger.error("索引读取器关闭错误{}",e.getMessage());}
		try{directory.close();}catch (Exception e) { logger.error("索引目录关闭错误{}",e.getMessage());}
		return ids;
	}
	
	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合，如果查询范围较广，检索结果较多，只返回100个.
	 * <br>@param clazz 实体类型
	 * <br>@param queryText 文本内容
	 * <br>@return 唯一索引即主键值集合
	 */
	@SuppressWarnings("unchecked")
	public synchronized static Collection<Object> searchKeyword(Class clazz,String queryText){
		Collection<Object> textPropertyNameList=new LinkedList<Object>();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++) {
			String propertyName = descriptors[i].getName();
			if(isTextWord(clazz,propertyName)){
				textPropertyNameList.add(propertyName);
			}
		}
		String[] textPropertyNames=(String[]) textPropertyNameList.toArray(new String[textPropertyNameList.size()]);
		return searchKeyword(clazz, queryText, textPropertyNames);
	}
	
	@SuppressWarnings("unchecked")
	private static String getIndexName(Class clazz){
		Index index=AnnotationUtils.findAnnotation(clazz, Index.class);
		if(index==null){
			return clazz.getSimpleName();
		}
		String indexName=index.indexName();
		if(indexName==null){
			return clazz.getSimpleName();
		}
		return indexName;
	}

	@SuppressWarnings("unchecked")
	private static String getKeywordName(Class clazz){
		Index index=AnnotationUtils.findAnnotation(clazz, Index.class);
		if(index==null){
			return "id";
		}
		String keyword=index.keywordPropertyName();
		if(keyword==null){
			return "id";
		}
		return keyword;
	}
	
	@SuppressWarnings("unchecked")
	private static boolean isTextWord(Class clazz,String propertyName){
		TextWord textWord=null;
		try {
			textWord = clazz.getDeclaredField(propertyName).getAnnotation(TextWord.class);
		} catch (Exception e) {}
		if(textWord!=null){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static boolean isFilterHtmlTags(Class clazz,String propertyName){
		TextWord textWord=null;
		try {
			textWord = clazz.getDeclaredField(propertyName).getAnnotation(TextWord.class);
		} catch (Exception e) {}
		if(textWord!=null){
			return textWord.isFilterHtmlTags();
		}
		return false;
	}
	
}
