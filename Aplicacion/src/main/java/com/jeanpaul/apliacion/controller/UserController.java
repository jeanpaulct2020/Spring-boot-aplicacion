package com.jeanpaul.apliacion.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String userForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",  roleService.findAll());
		model.addAttribute("userList", userService.findAllUsers());
		model.addAttribute("listTab", "active");
		return "user-form/user-view";
	}
	
	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model){
		
		//si se detectan errores
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);			
			model.addAttribute("formTab", "active");
		}
		else {
			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {				
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);			
				model.addAttribute("formTab", "active");
				model.addAttribute("roles",  roleService.findAll());
				model.addAttribute("userList", userService.findAllUsers());
			}
		
		}
		model.addAttribute("roles",  roleService.findAll());
		model.addAttribute("userList", userService.findAllUsers());
		
		return "user-form/user-view";
	}
}
