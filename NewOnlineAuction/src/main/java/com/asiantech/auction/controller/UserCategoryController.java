package com.asiantech.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
 
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ItemService;
@Controller
public class UserCategoryController {

	@Autowired 
	CategoryService categorySv;
	
	@Autowired 
	ItemService itemSv;
	 
	
	@RequestMapping("/category/{name}")
    public String getCategoryPage(@PathVariable("name") String categoryName, Model model){
		
		model.addAttribute("categories", categorySv.getAll());
		model.addAttribute("categoryName",categoryName);
		model.addAttribute("categoriesItem", itemSv.getAllByCategory(categorySv.getByName(categoryName)));
		model.addAttribute("pageview", "category");
        return "pages-views"; 
    }
}
