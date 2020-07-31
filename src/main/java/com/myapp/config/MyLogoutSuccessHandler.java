package com.myapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

//@Component
//@Qualifier(value="successLogoutBeanName") //ho messo il bean dentro MyAppConfig, altrimenti si poteva mettere questa annotazione
public class MyLogoutSuccessHandler implements LogoutSuccessHandler{
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null){
            session.removeAttribute("user");
        }
        response.sendRedirect("/login");
    }
}