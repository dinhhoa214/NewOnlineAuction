package com.asiantech.auction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.security.core.userdetails.UserDetailsService;

import com.asiantech.auction.entity.Account;

public interface AccountService  extends UserDetailsService{
	public static String NAME = "accountService";
	// CRUD operations
	// Save or Update
	Account insertUser(Account user);
	Account updateUser(Account user);

	// Read
	Account getById(int id);
	boolean checkByUserName(Account user);
	Account getByUserName(String userName);
	Account getUserByEmail(String email);

	// Delete
	void deleteById(int id);

	// Get All
	List<Account> getAll();

	// Get All and Paging
	Page<Account> getAllUserAndPagination(Pageable pageable); 
	 
}
