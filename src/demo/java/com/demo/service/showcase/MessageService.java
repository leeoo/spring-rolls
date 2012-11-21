package com.demo.service.showcase;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.MongoDBOrmDao;

import com.demo.entity.showcase.Message;

/**
 * 功 能 描 述:<br>
 * mongoDB的演示
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20下午02:39:49
 * <br>项 目 信 息:paramecium:com.demo.service.showcase.MessageService.java
 */
@Service
public class MessageService {

	private MongoDBOrmDao<Message, Long> ormDao = new MongoDBOrmDao<Message, Long>(Message.class);
	
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
