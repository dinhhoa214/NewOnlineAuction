package com.asiantech.auction.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;   

import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
public interface ItemService {
	public static String NAME = "itemService";
	// CRUD operations
	// Save or Update
	Item insertItem(Item item);
	Item updateItem(Item item);

	// Read
	Item getById(int id);

	// Delete
	void deleteById(int id);

	// Get All
	List<Item> getAll();
	List<Item> getAllByCategory(Category cate);

	// Get All and Paging
	Page<Item> getAllItemAndPagination(Pageable pageable); 
}
