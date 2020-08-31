package com.jeanpaul.apliacion.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		return "redirect:/userForm";
	}
	
	@GetMapping("/editUser/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) throws Exception {
		
		User userToEdit = userService.findUserById(id);
		//mostrar el usuario encontrado en el formulario
		model.addAttribute("userForm", userToEdit);
		//para los roles del formulario
		model.addAttribute("roles",  roleService.findAll());
		//mostrarme todos los usuarios en la lista
		model.addAttribute("userList", userService.findAllUsers());
		//mostrarme el form activdado
		model.addAttribute("formTab", "active");
		
		model.addAttribute("editMode", "true");
				
		
		return "user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		//si se detectan errores
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);			
				model.addAttribute("formTab", "active");
				model.addAttribute("editMode", "true");
			}
			else {
				try {
					userService.updateUser(user);
					model.addAttribute("userForm", new User());
					model.addAttribute("listTab", "active");
				} catch (Exception e) {				
					model.addAttribute("formErrorMessage",e.getMessage());
					model.addAttribute("userForm", user);			
					model.addAttribute("formTab", "active");
					model.addAttribute("roles",  roleService.findAll());
					model.addAttribute("userList", userService.findAllUsers());
					model.addAttribute("editMode", "true");
				}
			
			}
			model.addAttribute("roles",  roleService.findAll());
			model.addAttribute("userList", userService.findAllUsers());
			
			return "user-form/user-view";	
	}
	@GetMapping("/cancelar")
	public String cancelEditUser() {
		
		return "redirect:/userForm";
	}
	
}
