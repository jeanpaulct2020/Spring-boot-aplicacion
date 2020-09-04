package com.jeanpaul.apliacion.controller;


import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jeanpaul.apliacion.dto.ChangePasswordForm;
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
				
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
		return "user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		//si se detectan errores
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);			
				model.addAttribute("formTab", "active");
				model.addAttribute("editMode", "true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
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
					model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
				}
			
			}
			model.addAttribute("roles",  roleService.findAll());
			model.addAttribute("userList", userService.findAllUsers());
			
			return "user-form/user-view";	
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
	
		try {
			userService.deleteUserById(id);
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", "El usuario no puede eliminarse");
		}
		return "redirect:/userForm";
	}
	
	@GetMapping("/cancelar")
	public String cancelEditUser() {
		return "redirect:/userForm";
	}
	
	
	@PostMapping("/editUser/changePassword")
	public ResponseEntity<String> postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			if(errors.hasErrors()) {
				String result = errors.getAllErrors()
						.stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(""));
				
				throw new Exception(result);
			}
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
	}
	
}
