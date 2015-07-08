package com.asiantech.auction.service;

import java.util.List;

import javax.annotation.Resource;
 




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.repository.ItemRepository;
 
@Service(ItemService.NAME)
@Transactional(propagation=Propagation.SUPPORTS)
public class ItemServiceImpl implements ItemService{
	@Resource
	ItemRepository itemRepository; 
	
	

	@Override
	public Item insertItem(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getAllByCategory(Category cate) {
		return itemRepository.findByCategory(cate); 
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
	public Page<Item> getAllItemAndPagination(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}
	
}
