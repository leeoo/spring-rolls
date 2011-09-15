package com.demo.service.system;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.MongoDBOrmDao;

import com.demo.entity.system.Message;

@Service
public class MessageService {

	private MongoDBOrmDao<Message, Long> ormDao = new MongoDBOrmDao<Message, Long>("mg1", Message.class);
	
	public void clear(){
		ormDao.getMongoDao().getDB().getCollection("t_message").drop();
	}
	
	public long save(Message message) throws Exception{
		return ormDao.insert(message).longValue();
	}
	
	public void update(Message message) throws Exception{
		ormDao.update(message);
	}
	
	public void delete(long id) throws Exception{
		ormDao.delete(id);
	}
	
	public Message get(long id){
		return ormDao.select(id);
	}
	
	public Page get(Page page){
		return ormDao.select(page);
	}
	
	public Page get(Page page,Message message){
		return ormDao.select(page, message);
	}
	
}
