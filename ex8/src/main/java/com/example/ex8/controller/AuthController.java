package com.example.ex8.controller;


import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/auth")
public class AuthController {
	@GetMapping("/login")
	public void login(){
		log.info("login..........");
	}

	@GetMapping("/logout")
	public void logout(){
		log.info("logout..........");
	}

	@GetMapping("/accessDenied")
	public void accessDenied(Authentication auth, Model model){
		log.info("accessDenied..........");
		model.addAttribute("msg","Access Denied");
	}
}
