package com.asiantech.auction.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  
import org.springframework.stereotype.Service;

import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.repository.AccountRepository;
import com.asiantech.auction.repository.BidRepository;
import com.asiantech.auction.repository.ItemRepository;
 
@Service(BidService.NAME)
public class BidServiceImpl implements BidService{
	@Resource
	BidRepository bidRepository; 
	
	@Resource
	ItemRepository itemRepository;
	
	@Resource
	AccountRepository accountRepository;
	
	@Override
	public boolean insertBid(Bid bid, int itemId){
		try{
			Item item = itemRepository.findOne(itemId);
			bid.setItemId(item);
			if(bid.getAmount()==0)
				bid.setAmount(bidRepository.findFirstByOrderByAmountDesc().getAmount()+item.getBidIncremenet());
			
			
			bid.setBidderId(accountRepository.findOne(5));
			bidRepository.save(bid);
			item.setBids(item.getBids()+1);
			itemRepository.save(item);
			return true;
		}
		catch(Exception ex){
			return false;
		}
	
		
	}


	@Override
	public Bid updateBid(Bid bid) {
		return bidRepository.save(bid);
	}


	@Override
	public List<Bid> getAllByItemId(Item it) {
		 
		return bidRepository.findByItemId(it);
	}


	@Override
	public Bid getById(int id) { 
		return bidRepository.findOne(id);
	}
	
	@Override
	public Bid getByAmountTop() {
		return bidRepository.findFirstByOrderByAmountDesc();
	}


	@Override
	public void deleteById(int id) {
		bidRepository.delete(id); 
		
	}

	@Override
	public List<Bid> getAll() {
		return bidRepository.findAll();
	}

	@Override
	public Page<Bid> getAllBidAndPagination(Pageable pageable) {
		return bidRepository.findAll(pageable);
	}
	
}
