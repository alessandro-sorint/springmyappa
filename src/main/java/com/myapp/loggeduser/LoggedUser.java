package com.myapp.loggeduser;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myapp.entity.User;

public class LoggedUser implements HttpSessionBindingListener {
	
	private Logger logger = LoggerFactory.getLogger(LoggedUser.class);
 
    private User user;
    private HttpSession session;
    private ActiveUserStore activeUserStore;
    
    public LoggedUser(User user, ActiveUserStore activeUserStore, HttpSession session) {
        this.user = user;
        this.activeUserStore = activeUserStore;
        this.session = session;
    }
    
    @Override
    public boolean equals(Object o) {
    	if(!(o instanceof LoggedUser)) {
    		return false;
    	}else {
    		return this.getUser().equals(((LoggedUser)o).getUser());
    	}
    }
    
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<LoggedUser> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();

        logger.debug("contains user: " + users.contains(user));
        
        if(users.contains(user)) {
        	LoggedUser oldUser = users.stream().filter(u -> u.getUser().equals(user.getUser())).findFirst().get();
        	oldUser.getSession().invalidate();
        	users.remove(user);
        }
        users.add(user);
    }
 
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<LoggedUser> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users.contains(user)) {
            users.remove(user);
        }
    }
 
    // standard getter and setter
    
    public User getUser() {
    	return user;
    }

	public ActiveUserStore getActiveUserStore() {
		return activeUserStore;
	}
	
	public HttpSession getSession() {
		return session;
	}
}