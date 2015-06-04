package sy.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sy.model.Tuser;
import sy.service.UserServiceI;

public class TestHibernate {
	@Test
	public void test() {
		/*
		ApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml","classpath:spring-hibernate.xml"});
		UserServiceI userSerice=(UserServiceI)ac.getBean("userService");
		Tuser t=new Tuser();
		t.setId(UUID.randomUUID().toString());
		t.setName("李意峰");
		t.setPwd("123456");
		t.setModifydatetime(new Date());
		t.setCreatedatetime(new Date());
		userSerice.save(t);*/
	}
}
