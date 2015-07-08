package com.asiantech.auction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Item;

public interface BidRepository extends JpaRepository<Bid, Integer>{

	Bid findFirstByOrderByAmountDesc();
	List<Bid> findByItemId(Item item); 
}