package com.test.demo;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapp.dao.impl.UserDaoImpl;
import com.myapp.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootApplicationTests.class)
public class SpringBootApplicationTests {
	@Autowired
	ApplicationContext context;

	@Test
	public void contextLoads() {
		
	}
	
	/*@Test
	public void testService() {
		Service service = context.getBean(Service.class);
		service.save();
	}*/
	
	/*@Test
	public void testProduct() {
		ProductRepository repository = context.getBean(ProductRepository.class);
		
        System.out.println(repository.findByDescription("ciao"));
        System.out.println(repository.findByDescriptionAndName("ciao", "testP"));
	}*/
	
	@Test
	public void testUser() {
		/*UserDaoImpl dao = context.getBean(UserDaoImpl.class);
		User user = dao.findUser(1);
		
		System.out.println("User: " + user);*/
	}
}
