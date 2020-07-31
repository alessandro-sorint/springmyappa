package com.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.entity.User;
import com.myapp.framework.MyController;
import com.myapp.framework.MyControllerMVC;
import com.myapp.loggeduser.ActiveUserStore;

@Controller
public class MainController extends MyController{
	
	@Autowired
    private ActiveUserStore activeUserStore;
	
	@GetMapping("/")
    public String mainPage(User user, Model model) {
		return "redirect:/index"; //per redirect
	}
	
	@GetMapping("/login")
    public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("numeroUtenti", activeUserStore.getUsers().size());
		
		System.out.println("ciao utente " + getUser());
		
	    return "index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/fail")
	public void fail() {
		throw new RuntimeException();
	}

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public String error() {
		return "error";
	}
}
