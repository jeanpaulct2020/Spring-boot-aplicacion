package com.jeanpaul.apliacion.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeanpaul.apliacion.entity.User;
import com.jeanpaul.apliacion.service.RoleService;
import com.jeanpaul.apliacion.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired RoleService roleService;
	
	@GetMapping({"/","/login"})
	public String index() {
		
		return "index";
	}
	
	@GetMapping("/userForm")
	public String form(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",  roleService.findAll());
		model.addAttribute("userList", userService.findAllUsers());
		model.addAttribute("listTab", "active");
		return "user-form/user-view";
	}
}
