package com.asiantech.auction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.entity.Item.StatusBid;
 

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	List<Item> findByCategory(Category category);
	List<Item> findByCategoryAndStatusBid(Category category,StatusBid stt);
	
	List<Item> findByCreaterIdOrderByItemIdDesc(Account account);

}
