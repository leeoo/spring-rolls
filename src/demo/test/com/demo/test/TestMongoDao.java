package com.demo.test;

import java.sql.SQLException;
import java.util.Date;

import org.paramecium.ioc.ApplicationContext;

import com.demo.entity.system.Message;
import com.demo.service.system.MessageService;

public class TestMongoDao {
	
	public static void main(String[] args) throws SQLException {
		MessageService messageService = (MessageService)ApplicationContext.getNotSecurityBean("messageService");
		Message message = new Message();
		message.setAuth("曹阳");
		message.setTitle("动车组出现事故");
		message.setContent("我操，完了。我操，完了。我操，完了。我操，完了。我操，完了。");
		message.setPublishDate(new Date());
		long id = messageService.save(message);
		System.out.println(id);
	}
	
}
