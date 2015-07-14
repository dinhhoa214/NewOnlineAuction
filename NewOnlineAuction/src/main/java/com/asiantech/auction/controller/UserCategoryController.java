package com.asiantech.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiantech.auction.entity.Category;
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ItemService;
@Controller
public class UserCategoryController {

	@Autowired 
	CategoryService categorySv;
	
	@Autowired 
	ItemService itemSv;
	 
	@ModelAttribute("categories")
	public List<Category> seeAllCategories() {
	    return categorySv.getAll();
	}
	
	@RequestMapping("/category/{name}")
    public String getCategoryPage(@PathVariable("name") String categoryName, Model model){
		
		model.addAttribute("categoryName",categoryName);
		model.addAttribute("categoriesItem", itemSv.getAllByCategoryAndStatusActive(categorySv.getByName(categoryName)));
		model.addAttribute("pageview", "category");
        return "PAGES-VIEWS"; 
    }
}
