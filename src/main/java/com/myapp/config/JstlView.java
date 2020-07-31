package com.myapp.config;

import java.util.*;
import java.util.regex.Pattern;

import org.springframework.web.servlet.view.InternalResourceView;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JstlView extends InternalResourceView {
	private static final String loginUri = "/login";
	
	Logger logger = LoggerFactory.getLogger(JstlView.class);
	
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if(!request.getRequestURI().toString().equals(loginUri)) {
    	
        	String dispatcherPath = prepareForRendering(request, response);

        	// set original view being asked for as a request parameter
	        request.setAttribute("partial", dispatcherPath.substring(dispatcherPath.lastIndexOf("/") + 1));
	
	        // force everything to be template.jsp
	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/template.jsp");
	        response.setContentType("text/html");
	        super.exposeModelAsRequestAttributes(model, request);//mappa i campi del model nella request
	        rd.include(request, response);
        }else {
        	super.renderMergedOutputModel(model, request, response);
        }
    }
}