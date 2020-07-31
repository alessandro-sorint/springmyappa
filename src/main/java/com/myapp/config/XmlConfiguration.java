package com.myapp.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.myapp.entity.User;
import com.myapp.loggeduser.ActiveUserStore;
import com.myapp.loggeduser.LoggedUser;

@Configuration
@ImportResource({ "classpath*:applicationContext.xml" })
@EnableJpaRepositories(basePackages="com.myapp", entityManagerFactoryRef="sessionFactory")//se tolgo errore entityManagerFactory
public class XmlConfiguration {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		resolver.setViewClass(JstlView.class); //La classe che ho creato per aggiungere il template

		return resolver;
	}

	@Bean(name = "successLogoutBeanName")
	// @Bean
	public MyLogoutSuccessHandler getLogoutHandler() {
		return new MyLogoutSuccessHandler();
	}

	@Bean
	// @ConditionalOnMissingBean//(value = MyLogoutSuccessHandler.class, name =
	// "successLogoutBeanName") //se non trova nessun bean per una determinata
	// classe
	// @ConditionalOnBean(name = "dataSource") //Crea se un bean è presente
	@Primary // serve se ci sono più candidati disponibili
	public MyLogoutSuccessHandler getLogoutHandlerPrimary() {
		return new MyLogoutSuccessHandler();
	}
}