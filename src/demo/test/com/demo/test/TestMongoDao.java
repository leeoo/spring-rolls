package com.demo.test;

import java.sql.SQLException;

import org.paramecium.ioc.ApplicationContext;

import com.demo.service.system.MessageService;

public class TestMongoDao {
	
	public static void main(String[] args) throws SQLException {
		MessageService messageService = (MessageService)ApplicationContext.getNotSecurityBean("messageService");
		/*Message message = new Message();
		message.setAuth("2曹阳");
		message.setTitle("2动车组出现事故");
		message.setContent("2我操，完了。我操，完了。我操，完了。我操，完了。我操，完了。");
		message.setPublishDate(new Date());
		long id = messageService.save(message);
		System.out.println(id);*/
		 messageService.delete(1311579834875l);
		/*System.out.println(message.getId());
		System.out.println(message.getTitle());
		System.out.println(message.getAuth());
		System.out.println(message.getPublishDate());
		System.out.println(message.getContent());*/
		/*message.setAuth("曹阳");
		messageService.update(message);*/
		/*Page page = messageService.get(new Page(5));
		Collection<Message> beans = (Collection<Message>) page.getResult();
		for(Message message : beans){
			System.out.println(message.getId());
			System.out.println(message.getTitle());
			System.out.println(message.getAuth());
			System.out.println(message.getPublishDate());
			System.out.println(message.getContent());
		}*/
	}
	
}
