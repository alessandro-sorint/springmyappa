package com.myapp.config;


import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myapp.jwt.TokenUtil;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtil jwtTokenUtil;

    private static final String tokenHeader = "x-auth-token";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(tokenHeader);
        System.out.println("authToken: " + authToken);
        System.out.println("JwtAuthenticationTokenFilter " + request.getPathInfo());
        
        
        String username = null;

        if(authToken != null){
        	username = (String)jwtTokenUtil.getClaimsFromToken(authToken).get("username");
        }
        
        if(username == null) {
        	throw new UnavailableException("Non autorizzato!!!");
        }

        /*if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Ricostruisco l userdetails con i dati contenuti nel token


            // controllo integrita' token
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }*/

        //chain.doFilter(request, response);
    }
}