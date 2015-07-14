package com.asiantech.auction.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.entity.Item.StatusBid;
import com.asiantech.auction.repository.ItemRepository;
 
@Service(ItemService.NAME)
@Transactional(propagation=Propagation.SUPPORTS)
public class ItemServiceImpl implements ItemService{
	@Resource
	ItemRepository itemRepository; 
	
	@Autowired 
	AccountService accountSv; 
	
	@Autowired
	BidService bidSv;

	@Override
	public Item insertItem(Item item) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Account account = accountSv.getUserByEmail(email);
		item.setCreaterId(account);
		item.setStatusBid(StatusBid.I);
		if(item.getItemImage()==null)
			item.setItemImage("image.item/default.jpg");
		return itemRepository.save(item);
	}

	@Override
	public Item updateItem(Item item) {
		return itemRepository.save(item);
	}
	
	

	@Override
	public void activeItem(int id) {
		Item item = getById(id);
		item.setStatusBid(StatusBid.A);
		Bid bidfirst = new Bid(item.getMiniumBid(), item, item.getCreaterId());
		bidSv.insertBid(bidfirst);
		itemRepository.save(item);
	}

	@Override
	public void inActiveItem(int id) {
		Item item = getById(id);
		item.setStatusBid(StatusBid.I);
		itemRepository.save(item);
	}

	@Override
	public List<Item> getAllByCategory(Category cate) {
		return itemRepository.findByCategory(cate); 
	}

	@Override
	public List<Item> getAllByCategoryAndStatusActive(Category cate) {
		return itemRepository.findByCategoryAndStatusBid(cate, StatusBid.A);
	}

	@Override
	public Item getById(int id) {
 
		return itemRepository.findOne(id);
	}

	@Override
	public void deleteById(int id) {
		itemRepository.delete(id);
		
	}

	@Override
	public List<Item> getAll() {
		return itemRepository.findAll();
	}
	
	@Override
	public List<Item> getAllByUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Account account = accountSv.getUserByEmail(email);
		return itemRepository.findByCreaterIdOrderByItemIdDesc(account);
	}

	@Override
	public Page<Item> getAllItemAndPagination(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}
	
}
