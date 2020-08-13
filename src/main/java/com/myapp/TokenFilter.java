package com.myapp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myapp.jwt.TokenUtil;

//@Component
//@Order(1)
//@WebFilter("/shop")
//@Order(SecurityProperties.IGNORED_ORDER)
/*public class TokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		TokenUtil util = new TokenUtil();
		String token = util.getToken(req);
		
		System.out.println("TokenFilter START " + req.getRequestURI() + " token: " + token);
		
		
		if(token == null || util.isTokenExpired(token)) {
			throw new UnavailableException("Non autorizzato!!!");
		}
		
		super.doFilter(request, response, filterChain);
	}
	
}*/

public class TokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		TokenUtil util = new TokenUtil();
		String token = util.getToken(req);
		
		System.out.println("TokenFilter START " + req.getRequestURI() + " token: " + token);
		
		
		if(token == null || util.isTokenExpired(token)) {
			throw new UnavailableException("Non autorizzato!!!");
		}
		
		chain.doFilter(request, response);
	}
	
}