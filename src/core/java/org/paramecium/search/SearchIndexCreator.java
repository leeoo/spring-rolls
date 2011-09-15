package org.paramecium.search;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.MergePolicy.OneMerge;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.paramecium.commons.BeanUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.search.annotation.Index;
import org.paramecium.search.annotation.TextWord;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;

/**
 * 功能描述(Description):<br>
 * <b> 利用Lucene建立文件索引，将大文本信息存入lucene，建立索引后可以快速检索数据库。 </b><br>
 * 作 者(Author): <i><b>曹阳(Cao.Yang)</b></i> <br>
 * 建立日期(Create Date): <b>2011-8-30下午07:22:06</b> <br>
 * 项目名称(Project Name): <b>paramecium</b> <br>
 * 包及类名(Package Class): <b>org.paramecium.search.SearchIndexCreator.java</b>
 */
public class SearchIndexCreator {

	private static Log logger = LoggerFactory.getLogger();

	public static String INDEX_PATH = null;

	public static String getPath() {
		if (INDEX_PATH == null) {
			INDEX_PATH = PathUtils.getClassRootPath().replaceFirst("classes","index");
		}
		return INDEX_PATH;
	}

	private static class ReportingMergeScheduler extends MergeScheduler {
		
		public void merge(IndexWriter writer) throws CorruptIndexException,IOException {
			OneMerge merge = null;
			while ((merge = writer.getNextMerge()) != null) {
				writer.merge(merge);
			}
		}

		public void close() throws CorruptIndexException, IOException {
		}

	}

	/**
	 * 功能描述：创建索引文件 <br>
	 * @param bean对象实体，用于装载数据
	 */
	public synchronized static void createIndex(Object bean) {
		IndexWriter writer = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(getPath()+ getIndexName(bean.getClass()) + "//"));
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_33, new IKAnalyzer());
			conf.setMergeScheduler(new ReportingMergeScheduler());
			conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(directory, conf);
			Document doc = new Document();
			String kekwordName = getKeywordName(bean.getClass());
			String kekwordValue = (String)BeanUtils.getFieldValue(bean, kekwordName);
			Field keyField = new Field(kekwordName, kekwordValue,Field.Store.YES, Field.Index.NOT_ANALYZED);
			doc.add(keyField);
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
				for(java.lang.reflect.Field field : fields){
					field.setAccessible(true);
					String propertyName = field.getName();
					if(isTextWord(bean.getClass(), propertyName)){
						String propertyValue = (String)field.get(bean);
						if (propertyValue == null || propertyValue.trim().isEmpty()) {
							continue;
						}
						if (isFilterHtmlTags(bean.getClass(), propertyName)) {
							propertyValue = html2Text(propertyValue);
						}
						Field textField = new Field(propertyName, propertyValue,Field.Store.YES, Field.Index.ANALYZED);
						doc.add(textField);
					}
				}
			}
			writer.addDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("索引文件建立错误!");
		}
		try {
			writer.optimize();
		} catch (Exception e) {
			logger.error("索引写入器优化错误!");
		}
		try {
			writer.close();
		} catch (Exception e) {
			logger.error("索引写入器关闭错误!");
		}
		try {
			directory.close();
		} catch (Exception e) {
			logger.error("索引目录关闭错误!");
		}
	}

	/**
	 * 功能描述：删除索引文件 <br>
	 * @param bean实体，用于装载数据
	 */
	public synchronized static void removeIndex(Object bean) {
		IndexWriter writer = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(getPath()+ getIndexName(bean.getClass()) + "//"));
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_33, new IKAnalyzer());
			conf.setMergeScheduler(new ReportingMergeScheduler());
			writer = new IndexWriter(directory, conf);
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
				for(java.lang.reflect.Field field : fields){
					field.setAccessible(true);
					String propertyName = field.getName();
					if(isTextWord(bean.getClass(), propertyName)){
						String propertyValue = (String)field.get(bean);
						if (propertyValue == null || propertyValue.trim().isEmpty()) {
							continue;
						}
						if (isFilterHtmlTags(bean.getClass(), propertyName)) {
							propertyValue = html2Text(propertyValue);
						}
						Term term = new Term(propertyName, propertyValue);
						writer.deleteDocuments(term);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("索引文件建立错误!");
		}
		try {
			writer.optimize();
		} catch (Exception e) {
			logger.error("索引写入器优化错误!");
		}
		try {
			writer.close();
		} catch (Exception e) {
			logger.error("索引写入器关闭错误!");
		}
		try {
			directory.close();
		} catch (Exception e) {
			logger.error("索引目录关闭错误!");
		}
	}

	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合，如果查询范围较广，检索结果较多，只返回100个.
	 * BooleanClause.Occur.MUST，BooleanClause
	 * .Occur.MUST_NOT，BooleanClause.Occur.SHOULD。有以下6种组合：
	 * A．MUST和MUST：取得连个查询子句的交集。
	 * B．MUST和MUST_NOT：表示查询结果中不能包含MUST_NOT所对应得查询子句的检索结果。
	 * C．MUST_NOT和MUST_NOT：无意义，检索无结果。
	 * D．SHOULD与MUST、SHOULD与MUST_NOT：SHOULD与MUST连用时
	 * ，无意义，结果为MUST子句的检索结果。与MUST_NOT连用时，功能同MUST。
	 * E．SHOULD与SHOULD：表示“或”关系，最终检索结果为所有检索子句的并集。 <br>
	 * @param clazz 实体类型 <br>
	 * @param queryText 文本内容 <br>
	 * @param textPropertyNames 文本检索bean属性名组 <br>
	 * @return 唯一索引即主键值集合
	 */
	public synchronized static Collection<Object> searchKeyword(Class<?> clazz,String queryText, String... textPropertyNames) {
		Collection<Object> ids = new LinkedList<Object>();
		IndexReader reader = null;
		IndexSearcher searcher = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(getPath()+ getIndexName(clazz) + "//"));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);
		    Query query = IKQueryParser.parseMultiField(textPropertyNames, queryText);
			TopDocs topDocs = searcher.search(query, 100);
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				int DocId = hits[i].doc;
				Document doc = searcher.doc(DocId);
				ids.add(doc.get(getKeywordName(clazz)));
			}
		} catch (Exception e) {
			logger.error("索引错误!");
		}
		try {
			searcher.close();
		} catch (Exception e) {
			logger.error("查询器关闭错误!");
		}
		try {
			reader.flush();
		} catch (Exception e) {
			logger.error("索引读取器栈清空错误!");
		}
		try {
			reader.close();
		} catch (Exception e) {
			logger.error("索引读取器关闭错误!");
		}
		try {
			directory.close();
		} catch (Exception e) {
			logger.error("索引目录关闭错误!");
		}
		return ids;
	}
	
	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合，如果查询范围较广，检索结果较多，只返回100个. <br>
	 * @param clazz 实体类型 <br>
	 * @param queryText 文本内容 <br>
	 * @return 唯一索引即主键值集合
	 */
	public synchronized static Collection<Object> searchKeyword(Class<?> clazz,String queryText) {
		Collection<Object> textPropertyNameList = new LinkedList<Object>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
			for(java.lang.reflect.Field field : fields){
				field.setAccessible(true);
				String propertyName = field.getName();
				if (isTextWord(clazz, propertyName)) {
					textPropertyNameList.add(propertyName);
				}
			}
		}
		String[] textPropertyNames = (String[]) textPropertyNameList.toArray(new String[textPropertyNameList.size()]);
		return searchKeyword(clazz, queryText, textPropertyNames);
	}

	private static String getIndexName(Class<?> clazz) {
		Index index = clazz.getAnnotation(Index.class);
		if (index == null) {
			return clazz.getSimpleName();
		}
		String indexName = index.indexName();
		if (indexName == null) {
			return clazz.getSimpleName();
		}
		return indexName;
	}

	private static String getKeywordName(Class<?> clazz) {
		Index index = clazz.getAnnotation(Index.class);
		if (index == null) {
			return "id";
		}
		String keyword = index.keywordPropertyName();
		if (keyword == null) {
			return "id";
		}
		return keyword;
	}

	private static boolean isTextWord(Class<?> clazz, String propertyName) {
		TextWord textWord = null;
		try {
			textWord = clazz.getDeclaredField(propertyName).getAnnotation(TextWord.class);
		} catch (Exception e) {
		}
		if (textWord != null) {
			return true;
		}
		return false;
	}

	private static boolean isFilterHtmlTags(Class<?> clazz, String propertyName) {
		TextWord textWord = null;
		try {
			textWord = clazz.getDeclaredField(propertyName).getAnnotation(TextWord.class);
		} catch (Exception e) {
		}
		if (textWord != null) {
			return textWord.isFilterHtmlTags();
		}
		return false;
	}
	
	public static String html2Text(String inputString) {
		return inputString.replaceAll("\r\n", "").replaceAll("<[.[^<]]*>","").replaceAll("	", ""); 
	}

}
