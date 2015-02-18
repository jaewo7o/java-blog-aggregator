package com.jaewoo.jba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jaewoo.jba.service.UserService;

@Controller
@RequestMapping("/users")
public class AdminController {
	@Autowired
	private UserService userService;
	 
	@RequestMapping
	public String users(Model model){
		model.addAttribute("users", userService.findAll());
		return "users";
	}
	
	@RequestMapping(value="/{id}")
	public String detail(Model model, @PathVariable int id) {
		System.out.println("test!!!");
		model.addAttribute("user", userService.findOneWithBlog(id));
		return "user-detail";
	}
		
	@RequestMapping(value="/remove/{id}")
	public String removeUser(@PathVariable int id) {
		userService.delete(id);
		return "redirect:/users.html";
	}
}
