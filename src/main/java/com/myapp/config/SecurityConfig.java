package com.myapp.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.stereotype.Component;

import com.myapp.TokenFilter;
import com.myapp.entity.User;
import com.myapp.jwt.TokenUtil;
import com.myapp.repository.UserRepository;
import com.myapp.services.UserService;

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration
//https://stackoverflow.com/questions/3785706/whats-the-difference-between-secured-and-preauthorize-in-spring-security-3
//https://www.baeldung.com/spring-security-expressions	
//https://www.baeldung.com/spring-security-taglibs   TAGLIB

@Configuration
@EnableGlobalMethodSecurity(
	    securedEnabled = true,
	    jsr250Enabled = true,
	    prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private MySimpleUrlAuthenticationSuccessHandler successLoginHandler;
    @Autowired
    @Qualifier("successLogoutBeanName")
    private MyLogoutSuccessHandler successLogoutHandler;
    
    /*@Autowired
    private TokenFilter tokenFiler;*/
    
    /*@Bean
    public TokenFilter getTokenFilter() {
    	return new TokenFilter();
    }*/
    
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }*/

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/mgmt/**").access("hasAnyAuthority('ADMIN')")
                .antMatchers("/*.css").permitAll()
                //.antMatchers("/jobs/**").access("hasAnyAuthority('ADMIN')") alternativa alle annotation, //hasRole non funziona, forse devono combaciare tutti i ruoli
                .antMatchers("/shop/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .successHandler(successLoginHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(successLogoutHandler)
                .permitAll() 
            .and().csrf().disable();
        //http.antMatcher("/shop/**").addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.antMatcher("/shop/**").addFilter(tokenFiler);//.addFilterBefore(new JwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class);
        //http.addFilterBefore(tokenFiler, WebAsyncManagerIntegrationFilter.class);
    }
    
    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<TokenFilter>();
        registration.setFilter(new TokenFilter());
        registration.addUrlPatterns("/shop/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("tokenFilter");
        registration.setOrder(1);
        return registration;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
        	User user = userService.findByUsername(username);
        	if(user != null) {
        		return user;
        	}

        	throw new UsernameNotFoundException("User '" + username + "' not found.");
        });
                /*.and()
                .inMemoryAuthentication()
                .withUser("manager").password("{noop}s3cr3t").roles("ACTUATOR", "ADMIN");*/ //non funziona non crea oggetto User
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
        	User user = userService.findByUsername(username);
        	if(user != null) {
        		return user;
        	}
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        };
    }
    
    /*@Bean
    public FilterRegistrationBean securityFilterChain(
            @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(0);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }*/
    
}
