package org.paramecium.nosql.mongodb;

import java.util.Collection;
import java.util.List;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
/**
 * 功 能 描 述:<br>
 * 通用的MongoDB操作数据操作类，可操作原生MongoDB。
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-22上午09:51:26
 * <br>项 目 信 息:paramecium:org.paramecium.nosql.mongodb.GenericMonogDBNativeDao.java
 */
public class GenericMonogDBNativeDao {

	private DB mongoDB;
	
	/**
	 * 默认数据库名称
	 */
	public GenericMonogDBNativeDao(){
		this(MongoDBManager.defaultDBName);
	}
	
	public GenericMonogDBNativeDao(String mongoDBName){
		this.mongoDB = MongoDBManager.getMongoDB(mongoDBName);
	}
	
	/**
	 * 获得所有集合名称，即数据库表名集合
	 * @return
	 */
	public Collection<String> getCollectionNames(){
		return mongoDB.getCollectionNames();
	}
	
	/**
	 * 获取所在DB
	 * @return
	 */
	public DB getDB(){
		return mongoDB;
	}
	
	/**
	 * 运行CMD命令行或Shell脚本命令行
	 * @param cmd
	 * @return
	 */
	public CommandResult command(String cmd){
		return mongoDB.command(cmd);
	}
	
	/**
	 * 保存
	 * @param collName
	 * @param object
	 * @return
	 */
	public WriteResult save(String collName,DBObject object){
		DBCollection coll = mongoDB.getCollection(collName);
		return coll.save(object);
	}
	
	/**
	 * 批量插入数组
	 * @param collName
	 * @param objects
	 * @return
	 */
	public WriteResult insert(String collName,DBObject... objects){
		DBCollection coll = mongoDB.getCollection(collName);
		return coll.insert(objects);
	}

	/**
	 * 批量插入列表
	 * @param collName
	 * @param objects
	 * @return
	 */
	public WriteResult insert(String collName,List<DBObject> objects){
		DBCollection coll = mongoDB.getCollection(collName);
		return coll.insert(objects);
	}
	
	/**
	 * 更新
	 * @param collName
	 * @param oldObject
	 * @param newObject
	 * @return
	 */
	public WriteResult update(String collName,DBObject where,DBObject newObject){
		DBCollection coll = mongoDB.getCollection(collName);
		return coll.update(where, newObject);
	}
	
	/**
	 * 删除
	 * @param collName
	 * @param object
	 * @return
	 */
	public WriteResult remove(String collName,DBObject where){
		DBCollection coll = mongoDB.getCollection(collName);
		return coll.remove(where);
	}
	
	/**
	 * 查找单条数据
	 * @param collName
	 * @return
	 */
	public DBObject findOne(String collName){
		return findOne(collName,null);
	}
	
	/**
	 * 带条件查找单条数据
	 * @param collName
	 * @param object
	 * @return
	 */
	public DBObject findOne(String collName,DBObject where){
		DBCollection coll = mongoDB.getCollection(collName);
		if(where==null){
			return coll.findOne();
		}
		return coll.findOne(where);
	}
	
	/**
	 * 无条件查询多条数据，无排序
	 * @param collName
	 * @return
	 */
	public DBCursor find(String collName){
		return find(collName,null,null);
	}
	
	
	/**
	 * 带条查询多条数据，无排序
	 * @param collName
	 * @param where
	 * @return
	 */
	public DBCursor find(String collName,DBObject where){
		return find(collName,where,null);
	}
	
	/**
	 * 无条查询多条数据，有排序
	 * @param collName
	 * @param where
	 * @return
	 */
	public DBCursor find(DBObject orderBy,String collName){
		return find(collName,null,orderBy);
	}
	
	/**
	 * 带条查询多条数据，可排序
	 * @param collName
	 * @param where
	 * @param orderBy
	 * @return
	 */
	public DBCursor find(String collName,DBObject where,DBObject orderBy){
		DBCollection coll = mongoDB.getCollection(collName);
		if(where==null){
			if(orderBy==null){
				return coll.find();
			}
			return coll.find().sort(orderBy);
		}
		if(orderBy==null){
			return coll.find(where);
		}
		return coll.find(where).sort(orderBy);
	}
	
	/**
	 * 带条件分页查询，有排序
	 * @param collName
	 * @param where
	 * @param orderBy
	 * @param limit
	 * @param skip
	 * @return
	 */
	public DBCursor find(String collName,DBObject where,DBObject orderBy,int limit,int skip){
		return find(collName,where,orderBy).limit(getPageNumber(limit)).skip(getPageNumber(skip));
	}
	
	/**
	 * 无条件，无排序分页查询
	 * @param collName
	 * @param limit
	 * @param skip
	 * @return
	 */
	public DBCursor find(String collName,int limit,int skip){
		return find(collName).limit(getPageNumber(limit)).skip(getPageNumber(skip));
	}
	
	/**
	 * 无条件，有排序分页查询
	 * @param collName
	 * @param limit
	 * @param skip
	 * @return
	 */
	public DBCursor find(String collName,int limit,int skip,DBObject orderBy){
		return find(orderBy,collName).limit(getPageNumber(limit)).skip(getPageNumber(skip));
	}
	
	/**
	 * 有条件，无排序分页查询
	 * @param collName
	 * @param limit
	 * @param skip
	 * @return
	 */
	public DBCursor find(String collName,DBObject where,int limit,int skip){
		return find(collName,where).limit(getPageNumber(limit)).skip(getPageNumber(skip));
	}
	
	/**
	 * 带条件获得数据总数
	 * @param collName
	 * @param where
	 * @return
	 */
	public long count(String collName,DBObject where){
		DBCollection coll = mongoDB.getCollection(collName);
		if(where==null){
			return coll.count();
		}
		return coll.count(where);
	}
	
	/**
	 * 无条件获得数据总数
	 * @param collName
	 * @return
	 */
	public long count(String collName){
		return count(collName,null);
	}

	/**
	 * 获得分组数据集合
	 * 类似于SQL的GROUPBY和DISTINCT
	 * @param collName
	 * @param object
	 * @param key
	 * @return
	 */
	public List<?> distinct(String collName,DBObject where,String key){
		DBCollection coll = mongoDB.getCollection(collName);
		if(where==null){
			return coll.distinct(key);
		}
		return coll.distinct(key,where);
	}
	
	private static int getPageNumber(int number){
		if(number<0){
			return 0;
		}
		return number;
	}
	
}
