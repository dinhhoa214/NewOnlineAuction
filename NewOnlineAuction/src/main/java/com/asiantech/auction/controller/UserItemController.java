package com.asiantech.auction.controller;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.service.AccountService;
import com.asiantech.auction.service.BidService;
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ItemService;
@Controller
@RequestMapping("/item")
public class UserItemController {
	
	@Autowired 
	ItemService itemSv; 
	
	@Autowired 
	CategoryService categorySv;

	@Autowired 
	BidService bidSv;
	
	@Autowired 
	AccountService accountSv;
	
	@ModelAttribute("categories")
	public List<Category> seeAllCategories() {
	    return categorySv.getAll();
	}
	
	@RequestMapping("/{id}")
    public String getItemPage(@PathVariable("id") Integer itemId, Bid bid, Model model){
		Item item = itemSv.getById(itemId); 
		model.addAttribute(item);
		model.addAttribute(bid);
		model.addAttribute("bidtop", bidSv.getByItemOrderAmountTop(item));
		model.addAttribute("bids", bidSv.getTop10ByItemId(item));
		model.addAttribute("pageview", "item");
		return "PAGES-VIEWS";
    }
	
	@RequestMapping(value = "bidItem{id}", method = RequestMethod.POST)
	public String insertBid(@PathVariable("id") Integer itemId,
			@ModelAttribute("bid") Bid bid, Model model) {  
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Account account = accountSv.getUserByEmail(email);
		Item item = itemSv.getById(itemId);
		bid.setBidderId(account);
		bid.setItemId(item);
		
		if(!bidSv.insertBid(bid))
			return "redirect:/item/"+itemId;
		return "redirect:/item/"+itemId;
	} 
}
