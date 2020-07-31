package com.myapp.framework;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.entity.User;
import com.myapp.loggeduser.ActiveUserStore;
import com.myapp.loggeduser.LoggedUser;

public abstract class MyController {
	@Autowired
    private ActiveUserStore activeUserStore;
	
	@Autowired
	protected HttpServletRequest request;
	
	protected User getUser() {
		for(LoggedUser l : activeUserStore.getUsers()) {
			if(l.getSession().getId().equals(request.getSession().getId())) {
				return l.getUser();
			}
		}
		
		return null;
	}
}
