package com.asiantech.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam;

import com.asiantech.auction.entity.Item;
import com.asiantech.auction.service.ItemService;

@Controller
@RequestMapping("/admin")
public class AdminItemsController {

	@Autowired 
	ItemService itemSv;
	
	@RequestMapping("/adminItems")
    public String getAdminItemsPage(
    		@RequestParam(required = false, defaultValue = "0") Integer page, 
			@RequestParam(required = false, defaultValue = "5") Integer maxRows,
			@RequestParam(required = false, defaultValue = "itemId") String sort,
			Item item,Model model){  
		Order order = new Order(Direction.DESC,sort); 
		Page<Item> itemsPage = itemSv.getAllItemAndPagination(new PageRequest(
				page, maxRows, new Sort(order)));    
		model.addAttribute("items",itemsPage); 
		model.addAttribute("pageview", "admin/admin-items"); 
		model.addAttribute(item); 
		return "admin/admin-views";
    }
}
