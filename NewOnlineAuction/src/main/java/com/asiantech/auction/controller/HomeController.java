package com.asiantech.auction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.service.AccountService;
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ImageService;

@Controller 
public class HomeController {

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired 
	CategoryService categorySv;
	
	@Autowired 
	AccountService accountSv;
	
	@Autowired
	ImageService imageSv;
	
	@RequestMapping({"/","/home","/index"})
    public String getHomePage(Account account, Model model){
		model.addAttribute(account); 
	    model.addAttribute("categories", categorySv.getAll());     
        model.addAttribute("pageview", "homepage");
        return "pages-views";
    }

	@RequestMapping("/register")
	public String getRegisterPage(Account account, Model model) {
		return "register";
	}

	// extract to method with block if - else
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveAccount(
			@Valid Account account, 
			BindingResult bindingResult, Model model) { 
				
		String pwd = passwordEncoder.encode(account.getPassword());
		account.setPassword(pwd);
		accountSv.insertUser(account);
				return "index";  
	}
	 
	
}
