package com.ems.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ems.model.User;
import com.ems.repository.UserRepository;


@Controller
public class AppController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private UserRepository userRepo;
		
	
	@GetMapping("")
	public String viewHomePage() {
		logger.info("index page");
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());	
		logger.info("Register user");
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		logger.info("Register user : {}", user.getFirstName());
		
		userRepo.save(user);
		
		return "register_success";
	}
	
}
