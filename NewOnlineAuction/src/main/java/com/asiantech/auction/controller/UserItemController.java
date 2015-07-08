package com.asiantech.auction.controller;
 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;

import com.asiantech.auction.entity.Bid; 
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.service.AccountService;
import com.asiantech.auction.service.BidService;
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ItemService;
@Controller
public class UserItemController {
	
	@Autowired 
	ItemService itemSv; 
	
	@Autowired 
	CategoryService categorySv;

	@Autowired 
	BidService bidSv;
	
	@Autowired 
	AccountService accountSv;
	
	@RequestMapping("/item/{id}")
    public String getItemPage(@PathVariable("id") Integer itemId, Bid bid, Model model){
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = sdf.format(new Date()); 
		System.out.println(date); //15/10/2013
*/		String dateInString = "2015/07/12 17:13:15"; 
		model.addAttribute("endtime",dateInString);
		/*Date date2;
		try {
			date2 = sdf.parse(dateInString);
			Date end = date - date2;
			System.out.println(sdf.format(date2)); 
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}*/
		Item item = itemSv.getById(itemId);
		model.addAttribute("categories", categorySv.getAll());
		model.addAttribute("categoryName", item.getCategory().getName());
		model.addAttribute("item", item);
		model.addAttribute("bidtop", bidSv.getByAmountTop());
		model.addAttribute("bids", bidSv.getAllByItemId(item));
		model.addAttribute("pageview", "item");
        return "pages-views"; 
    }
	
	@RequestMapping(value = "/bidItem/{id}", method = RequestMethod.POST)
	public String insertBid(@PathVariable("id") Integer itemId, 
			@ModelAttribute Bid bid,Model model) {  
		System.out.println(bid.getItemId());
		if(bidSv.insertBid(bid, itemId) == true)
			return "redirect:/item/"+itemId;
		return "redirec:/index";
	}
	 
	
	@RequestMapping(value="/user/cruditem", method=RequestMethod.GET)
    public String getCrudItemPage(Model model){
		model.addAttribute("categories", categorySv.getAll());
		model.addAttribute("pageview", "user/cruditem");
        return "user/USER-VIEWS"; 
    } 
	
}
