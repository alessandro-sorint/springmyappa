package com.myapp.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.handler.MessageContext.Scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.myapp.entity.User;
import com.myapp.loggeduser.ActiveUserStore;
import com.myapp.loggeduser.LoggedUser;

//default handler è SavedRequestAwareAuthenticationSuccessHandler

@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
	@Autowired
    private ActiveUserStore activeUserStore;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
        	LoggedUser user = new LoggedUser((User)authentication.getPrincipal(), activeUserStore, session);
        	session.setAttribute("visitor", user);
        	response.sendRedirect("/index"); //defaultSuccessUrl sembra non funzionare con AuthenticationSuccessHandler
        }
    }
}
