package org.paramecium.nosql.mongodb;

import java.util.Collection;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class GenericMonogDBDao {

	private DB mongoDB;
	
	public GenericMonogDBDao(String mongoDBName){
		this.mongoDB = MongoDBManager.getMongoDB(mongoDBName);
	}
	
	public Collection<String> getTableNames(){
		return mongoDB.getCollectionNames();
	}
	
	public CommandResult command(String cmd){
		return mongoDB.command(cmd);
	}
	
	public void save(String tableName,DBObject object){
		DBCollection table = mongoDB.getCollection(tableName);
		table.save(object);
	}
	
}
