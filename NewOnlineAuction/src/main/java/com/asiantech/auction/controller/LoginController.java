package com.asiantech.auction.controller;
 

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.service.AccountService;
 

@Controller
@RequestMapping("/account/")
public class LoginController {
	
	@Autowired 
	private AccountService accountSr; 
	 
	 
	
	@RequestMapping(value="login")
	public String getLoginPage(@RequestParam(required= false) boolean error, ModelMap model){/*
		Account test = accountSr.getUserByEmail("asd@gmail.com");
		System.out.println(test.getEmail());
		System.out.println(test.getPassword());*/
		model.put("errorLogin", error);
		return "login";
	}
	
	@RequestMapping(value="logout")
	public String logOut(){
		return "login";
	}
}
