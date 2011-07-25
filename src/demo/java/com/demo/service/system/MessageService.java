package com.demo.service.system;

import java.sql.SQLException;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;

import com.demo.entity.system.Message;

@Service
public class MessageService {

	private GenericOrmDao<Message, Long> ormDao = new GenericOrmDao<Message, Long>("mg1", Message.class,true);
	
	public void clear(){
		ormDao.getMongoDao().getDB().getCollection("t_message").drop();
	}
	
	public long save(Message message) throws SQLException{
		return ormDao.insert(message).longValue();
	}
	
	public void update(Message message) throws SQLException{
		ormDao.update(message);
	}
	
	public void delete(long id) throws SQLException{
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
