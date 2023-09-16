package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UiController {


	@GetMapping("/home")
	public String home() {
		return "index";
	}
	
	@GetMapping("/success")
	public String success() {
		return "success";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	@GetMapping("/edit")
	public String edit() {
		return "edit";
	}
	
	@GetMapping("/create")
	public String create() {
		return "create";
	}
	
	
	
}
