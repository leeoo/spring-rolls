package com.demo.service.system;

import java.sql.SQLException;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.NosqlOrmDao;

import com.demo.entity.system.Message;

@Service
public class MessageService {

	private NosqlOrmDao<Message, Long> ormDao = new NosqlOrmDao<Message, Long>("mg1", Message.class);
	
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
