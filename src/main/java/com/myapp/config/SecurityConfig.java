package com.myapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

import com.myapp.entity.User;
import com.myapp.repository.UserRepository;
import com.myapp.services.UserService;

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration
//https://stackoverflow.com/questions/3785706/whats-the-difference-between-secured-and-preauthorize-in-spring-security-3
//https://www.baeldung.com/spring-security-expressions	

@Configuration
@EnableGlobalMethodSecurity(
	    securedEnabled = true,
	    jsr250Enabled = true,
	    prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserService userService;
    //private UserRepository userRepository;
    
    @Autowired
    private MySimpleUrlAuthenticationSuccessHandler successLoginHandler;
    @Autowired
    @Qualifier("successLogoutBeanName")
    private MyLogoutSuccessHandler successLogoutHandler;
    
    /* @Bean
    public PasswordEncoder passwordEncoder() {
    	//return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	return NoOpPasswordEncoder.getInstance();
    }*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/mgmt/**").access("hasAnyAuthority('ADMIN')")
                .antMatchers("/*.css").permitAll()
                //.antMatchers("/jobs/**").access("hasAnyAuthority('ADMIN')") alternativa alle annotation, //hasRole non funziona, forse devono combaciare tutti i ruoli
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
}
