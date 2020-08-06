package com.myapp.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.myapp.entity.User;
import com.myapp.framework.MyController;
import com.myapp.jwt.TokenUtil;
import com.myapp.loggeduser.ActiveUserStore;
import com.myapp.repository.ArticoloRepository;

import io.jsonwebtoken.impl.DefaultClaims;

@Controller
public class MainController extends MyController{
	
	private String tokenHeader = "x-auth-token";
	
	@Autowired
    private ActiveUserStore activeUserStore;
	@Autowired
	private ArticoloRepository articoloRepository;
	
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
	
	@GetMapping("shopMainPage")
	public String shop(HttpServletResponse response) {
		TokenUtil util = new TokenUtil();
		DefaultClaims claims = new DefaultClaims();
		claims.put("username",  getUser().getUsername());
		String token =  util.generateToken(claims, null);
		Cookie cookie = new Cookie(tokenHeader, token);
        response.addCookie(cookie); 
		
		request.setAttribute("articoli", articoloRepository.getAll());
		
		return "shopMainPage";
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
