package org.zerock.ex7.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@GetMapping("/login")
	public void login(){
	}

	@GetMapping("/logout")
	public void logout(){

	}
}
