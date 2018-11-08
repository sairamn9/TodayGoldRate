package com.spring.boot.practice.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
  
	@RequestMapping(value="/rest")
	public String helloWorld(){
		return " Hello Rest World";
	}
	@RequestMapping(value="/gold")
	public String goldRate(){
		return "Today Rate : 11:43";
	}
}