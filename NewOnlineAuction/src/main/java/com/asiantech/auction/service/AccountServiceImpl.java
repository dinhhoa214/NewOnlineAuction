package com.asiantech.auction.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Account.Role;
import com.asiantech.auction.repository.AccountRepository;

@Service(AccountService.NAME)
public class AccountServiceImpl implements AccountService,UserDetailsService {
	@Autowired
	AccountRepository userRepositoty;

	/*
	 * @Override public User saveOrUpdate(User user) { // kiem tra userName co
	 * trung ko? String newUserName = user.getUserName(); String oldUserName =
	 * userRepositoty.findOneByName(user.getUserName()).getUserName();
	 * if(newUserName.equalsIgnoreCase(oldUserName)) return user;
	 * userRepositoty.findOneByUserName(user.getUserName());
	 * if(userRepositoty.findByUserName(user.getUserName())==null) return null;
	 * 
	 * return userRepositoty.save(user); }
	 */

	public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}
	
	public List<String> getRoles(Role role){
		List<String> roles = new ArrayList<>();
		roles.add(role.name());
		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	 

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		System.out.println(email);
		Account acLogin = getUserByEmail(email);
		
		User user = new User(acLogin.getEmail(), acLogin.getPassword(), 
				true, true, true,true, getAuthorities(acLogin.getRole()));
		return user;
	}

	@Override
	public Account getUserByEmail(String email) { 
		Account account = userRepositoty.findByEmail(email);
		
		return account;
	}

	@Override
	public Account getById(int id) {
		return userRepositoty.findOne(id);
	}
	
	@Override
	public boolean checkByUserName(Account user) {
		Account check = userRepositoty.findByUserName(user.getUserName()); 
		System.out.println(check.getUserName());
		if(check.getPassword().equalsIgnoreCase(user.getPassword())) 
			return true;
		return false;
	}
	
	@Override
	public Account getByUserName(String userName) { 
		return userRepositoty.findByUserName(userName);
	}

	@Override
	public Account insertUser(Account user) {
		// kiem tra userName co trung ko? 
		if(userRepositoty.findByUserName(user.getUserName())!=null) 
			return null;
		user.setRole(Role.ROLE_USER); 
		if(user.getImage()==null)
			user.setImage("user/default.jpg");
		return userRepositoty.save(user);
	}

	@Override
	public Account updateUser(Account user) {
		return userRepositoty.save(user);
	}

	@Override
	public void deleteById(int id) {
		userRepositoty.delete(id);
	}

	@Override
	public List<Account> getAll() {
		return userRepositoty.findAll();
	}

	@Override
	public Page<Account> getAllUserAndPagination(Pageable pageable) {
		return userRepositoty.findAll(pageable);
	}

	/*@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		Account accLogin = userRepositoty.findByEmail(email);
		User user = new User(accLogin.getEmail(), accLogin.getPassword(), true,
				true, true, true, getAuthorities(accLogin.getRole()));
		return user;
	}*/

	
}
