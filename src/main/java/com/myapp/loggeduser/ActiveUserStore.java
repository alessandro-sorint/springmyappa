package com.myapp.loggeduser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.MessageContext.Scope;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ActiveUserStore {
	 
    private List<LoggedUser> users;
 
    public ActiveUserStore() {
        users = new ArrayList<LoggedUser>();
    }
    
    public List<LoggedUser> getUsers(){
    	return users;
    }
}
