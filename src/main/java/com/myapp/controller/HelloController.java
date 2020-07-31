package com.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.myapp.entity.User;
import com.myapp.framework.MyController;
import com.myapp.framework.MyControllerMVC;
import com.myapp.pojo.Prova;

@Controller
public class HelloController extends MyControllerMVC<Prova> {

	private Prova model;

	@Override
	public Prova getModel() {
		if (model == null) {
			model = new Prova();
		}
		return model;
	}

	@RequestMapping("/hello")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("hello");
		modelAndView.addObject("id", 123);
		modelAndView.addObject("name", "Bharath");
		modelAndView.addObject("salary", 10000);

		return modelAndView;
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "thymeleaf/greeting";
	}

	@GetMapping("/helloJSP")
	public String helloJSP(User user,
			@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
		System.out.println("start helloJSP");

		model.addAttribute("name", name);

		return "prova";
	}
}
