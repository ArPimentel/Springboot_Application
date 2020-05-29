package com.arturPimentelApp.applicationspring.controlleur;

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

import com.arturPimentelApp.applicationspring.User;
import com.arturPimentelApp.applicationspring.dto.ChangePassword;
import com.arturPimentelApp.applicationspring.repository.RoleRepository;
import com.arturPimentelApp.applicationspring.service.UserService;

@Controller
public class UserControlleur {

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/userForm")
	public String userForm (Model model){
		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("listTab", "active");
		return 	"user-form/user-view";
	}



	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
		}else {
			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab", "active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());   
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";
	}


	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable (name = "id") Long id) throws Exception{
		User userToEdit = userService.getUserById(id);

		model.addAttribute("userForm",(userToEdit));
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("formTab", "active");
		model.addAttribute("editMode", "true");
		model.addAttribute("passwordForm",new ChangePassword(id));

		return "user-form/user-view";

	}

	@PostMapping ("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
			model.addAttribute("editMode", "true");
			model.addAttribute("passwordForm",new ChangePassword(user.getId()));

		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab", "active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll()); 
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePassword(user.getId()));

			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";
	}
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id")Long id) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());		
		}
		return userForm(model);
	}
	@PostMapping("/editUser/changePassword")
	public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePassword form, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("success");
	}
}

