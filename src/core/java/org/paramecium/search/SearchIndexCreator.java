package org.paramecium.search;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.ConcurrentMergeScheduler;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.paramecium.commons.BeanUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.search.annotation.Index;
import org.paramecium.search.annotation.KeyWord;
import org.paramecium.search.annotation.SortWord;
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
	
	private static Version version = Version.LUCENE_35;

	public static String getPath() {
		if (INDEX_PATH == null) {
			INDEX_PATH = PathUtils.getClassRootPath().replaceFirst("classes","index");
		}
		return INDEX_PATH;
	}
	
	private static void close(IndexWriter writer,Directory directory){
		try {
			if(writer!=null){
				writer.commit();
			}
		} catch (Throwable e) {
			logger.error("索引写入器提交错误!");
		}
		try {
			if(writer!=null){
				writer.forceMerge(1);
			}
		} catch (Throwable e) {
			logger.error("索引写入器优化错误!");
		}
		try {
			if(writer!=null){
				writer.close();
			}
		} catch (Throwable e) {
			logger.error("索引写入器关闭错误!");
		}
		try {
			if(directory!=null){
				directory.close();
			}
		} catch (Throwable e) {
			logger.error("索引目录关闭错误!");
		}
	}

	private static void close(IndexReader reader,IndexSearcher searcher,Directory directory){
		try {
			if(searcher!=null){
				searcher.close();
			}
		} catch (Throwable e) {
			logger.error("查询器关闭错误!");
		}
		try {
			if(reader!=null){
				reader.flush();
			}
		} catch (Throwable e) {
			logger.error("索引读取器栈清空错误!");
		}
		try {
			if(reader!=null){
				reader.close();
			}
		} catch (Throwable e) {
			logger.error("索引读取器关闭错误!");
		}
		try {
			if(directory!=null){
				directory.close();
			}
		} catch (Throwable e) {
			logger.error("索引目录关闭错误!");
		}
	}

	/**
	 * 功能描述：创建索引文件 <br>
	 * Field.Store.YES:存储字段值（未分词前的字段值）
     * Field.Store.NO:不存储,存储与索引没有关系
     * Field.Store.COMPRESS:压缩存储,用于长文本或二进制，但性能受损
	 * Field.Index.ANALYZED:分词建索引
	 * Field.Index.ANALYZED_NO_NORMS:分词建索引，但是Field的值不像通常那样被保存，而是只取一个byte，这样节约存储空间
	 * Field.Index.NOT_ANALYZED:不分词且索引
	 * Field.Index.NOT_ANALYZED_NO_NORMS:不分词建索引，Field的值去一个byte保存
	 * TermVector表示文档的条目（由一个Document和Field定位）和它们在当前文档中所出现的次数
	 * Field.TermVector.YES:为每个文档（Document）存储该字段的TermVector
	 * Field.TermVector.NO:不存储TermVector
	 * Field.TermVector.WITH_POSITIONS:存储位置
	 * Field.TermVector.WITH_OFFSETS:存储偏移量
	 * Field.TermVector.WITH_POSITIONS_OFFSETS:存储位置和偏移量
	 * @param bean对象实体，用于装载数据
	 */
	public static void createIndex(Object bean) {
		IndexWriter writer = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(getPath()+ getIndexName(bean.getClass()) + "//"));
			IndexWriterConfig conf = new IndexWriterConfig(version, new IKAnalyzer());
			conf.setMergeScheduler(new ConcurrentMergeScheduler());
			conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(directory, conf);
			Document doc = new Document();
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
				for(java.lang.reflect.Field field : fields){
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					field.setAccessible(true);
					String propertyName = field.getName();
					if(isKeyWord(superClass, propertyName)){
						Object value = BeanUtils.getFieldValue(bean, field.getName(), field.getType());
						if (value == null || value.toString().trim().isEmpty()) {
							continue;
						}
						Field textField = new Field(propertyName, value.toString(),Field.Store.YES, Field.Index.NOT_ANALYZED);
						doc.add(textField);
					}else if(isTextWord(superClass, propertyName)){
						String propertyValue = (String)BeanUtils.getFieldValue(bean, field.getName(), field.getType());
						if (propertyValue == null || propertyValue.trim().isEmpty()) {
							continue;
						}
						if (isFilterHtmlTags(superClass, propertyName)) {
							propertyValue = html2Text(propertyValue);
						}
						Field textField = new Field(propertyName, propertyValue,Field.Store.NO, Field.Index.ANALYZED);
						doc.add(textField);
					}else if(isSortWord(superClass, propertyName)){
						Object value = BeanUtils.getFieldValue(bean, field.getName(), field.getType());
						if (value == null || value.toString().trim().isEmpty()) {
							continue;
						}
						Field textField = new Field(propertyName, value.toString(),Field.Store.NO, Field.Index.NOT_ANALYZED);
						doc.add(textField);
					}
				}
			}
			writer.addDocument(doc);
		} catch (Throwable e) {
			logger.error("索引文件建立错误!");
			logger.error(e);
		}
		close(writer, directory);
	}

	/**
	 * 功能描述：删除索引文件 <br>
	 * @param bean实体，用于装载数据
	 */
	public static void removeIndex(Object bean) {
		IndexWriter writer = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(getPath()+ getIndexName(bean.getClass()) + "//"));
			IndexWriterConfig conf = new IndexWriterConfig(version, new IKAnalyzer());
			conf.setMergeScheduler(new ConcurrentMergeScheduler());
			writer = new IndexWriter(directory, conf);
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
				for(java.lang.reflect.Field field : fields){
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					field.setAccessible(true);
					String propertyName = field.getName();
					if(isKeyWord(superClass, propertyName)||isSortWord(superClass, propertyName)){
						Object value = BeanUtils.getFieldValue(bean, field.getName(), field.getType());
						if (value == null || value.toString().trim().isEmpty()) {
							continue;
						}
						Term term = new Term(propertyName, value.toString());
						writer.deleteDocuments(term);
					}else if(isTextWord(superClass, propertyName)){
						String propertyValue = (String)BeanUtils.getFieldValue(bean, field.getName(), field.getType());
						if (propertyValue == null || propertyValue.trim().isEmpty()) {
							continue;
						}
						if (isFilterHtmlTags(superClass, propertyName)) {
							propertyValue = html2Text(propertyValue);
						}
						Term term = new Term(propertyName, propertyValue);
						writer.deleteDocuments(term);
					}
				}
			}
		} catch (Throwable e) {
			logger.error(e);
			logger.error("索引文件建立错误!");
		}
		close(writer, directory);
	}

	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合.
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
	 * @param queryCount 查询结果数量 <br>
	 * @param textPropertyNames 文本检索bean属性名组 <br>
	 * @return 唯一索引即主键值集合
	 */
	public static Collection<String> searchKeyword(Class<?> clazz,String queryText,int queryCount, String... textPropertyNames) {
		Collection<String> ids = new LinkedList<String>();
		IndexReader reader = null;
		IndexSearcher searcher = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(getPath()+ getIndexName(clazz) + "//"));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);
		    Query query = IKQueryParser.parseMultiField(textPropertyNames, queryText);
		    TopDocs topDocs = null;
		    SortField[] sortFields = getSortFields(clazz);
		    if(sortFields!=null && sortFields.length>0){
		    	Sort sort = new Sort(sortFields);
		    	topDocs = searcher.search(query, queryCount,sort);
		    }else{
		    	topDocs = searcher.search(query, queryCount);
		    }
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				int DocId = hits[i].doc;
				Document doc = searcher.doc(DocId);
				ids.add(doc.get(getKeywordName(clazz)));
			}
		} catch (Throwable e) {
			logger.error(e);
			logger.error("索引错误!");
		}
		close(reader, searcher, directory);
		return ids;
	}
	
	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合 <br>
	 * @param clazz 实体类型 <br>
	 * @param queryText 文本内容 <br>
	 * @param queryCount 查询结果数量 <br>
	 * @return 唯一索引即主键值集合
	 */
	public static Collection<String> searchKeyword(Class<?> clazz,String queryText,int queryCount) {
		Collection<Object> textPropertyNameList = new LinkedList<Object>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
			for(java.lang.reflect.Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				String propertyName = field.getName();
				if (isTextWord(superClass, propertyName)) {
					textPropertyNameList.add(propertyName);
				}
			}
		}
		String[] textPropertyNames = (String[]) textPropertyNameList.toArray(new String[textPropertyNameList.size()]);
		return searchKeyword(clazz, queryText,queryCount, textPropertyNames);
	}
	
	/**
	 * 功能描述：检索文本内容，获得数据库主键值集合，如果查询范围较广，检索结果较多，只返回100个. <br>
	 * @param clazz 实体类型 <br>
	 * @param queryText 文本内容 <br>
	 * @return 唯一索引即主键值集合
	 */
	public static Collection<String> searchKeyword(Class<?> clazz,String queryText) {
		return searchKeyword(clazz, queryText,100);
	}
	
	private static SortField[] getSortFields(Class<?> clazz) {
		List<SortField> sfs = new ArrayList<SortField>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
			for(java.lang.reflect.Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				String propertyName = field.getName();
				if(isSortWord(superClass, propertyName)){
					SortWord sortWord = field.getAnnotation(SortWord.class);
					sfs.add(new SortField(propertyName, sortWord.type(), sortWord.reverse()));
				}
			}
		}
		SortField[] sfArray = new SortField[sfs.size()];
		for(int i = 0 ; i < sfs.size();i++){
			sfArray[i] = sfs.get(i);
		}
		return sfArray;
	}

	private static String getIndexName(Class<?> clazz) {
		Index index = clazz.getAnnotation(Index.class);
		if (index == null) {
			return clazz.getSimpleName();
		}
		String indexName = index.value();
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
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			java.lang.reflect.Field[] fields = superClass.getDeclaredFields();
			for(java.lang.reflect.Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				String propertyName = field.getName();
				if(isKeyWord(superClass, propertyName)){
					return propertyName;
				}
			}
		}
		return "id";
	}

	private static boolean isTextWord(Class<?> clazz, String propertyName) {
		TextWord textWord = null;
		try {
			textWord = clazz.getDeclaredField(propertyName).getAnnotation(TextWord.class);
		} catch (Throwable e) {
			logger.error(e);
		}
		if (textWord != null) {
			return true;
		}
		return false;
	}
	
	private static boolean isSortWord(Class<?> clazz, String propertyName) {
		SortWord sortWord = null;
		try {
			sortWord = clazz.getDeclaredField(propertyName).getAnnotation(SortWord.class);
		} catch (Throwable e) {
			logger.error(e);
		}
		if (sortWord != null) {
			return true;
		}
		return false;
	}
	
	private static boolean isKeyWord(Class<?> clazz, String propertyName) {
		KeyWord keyWord = null;
		try {
			keyWord = clazz.getDeclaredField(propertyName).getAnnotation(KeyWord.class);
		} catch (Throwable e) {
			logger.error(e);
		}
		if (keyWord != null) {
			return true;
		}
		return false;
	}

	private static boolean isFilterHtmlTags(Class<?> clazz, String propertyName) {
		TextWord textWord = null;
		try {
			textWord = clazz.getDeclaredField(propertyName).getAnnotation(TextWord.class);
		} catch (Throwable e) {
			logger.error(e);
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
