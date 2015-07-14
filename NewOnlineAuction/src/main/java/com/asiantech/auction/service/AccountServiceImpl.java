package com.asiantech.auction.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asiantech.auction.common.MailMail;
import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Account.Role;
import com.asiantech.auction.repository.AccountRepository;

@Service(AccountService.NAME)
public class AccountServiceImpl implements AccountService,UserDetailsService {
	@Autowired
	AccountRepository userRepositoty;

	@Autowired
	PasswordEncoder passwordEncoder ;
	
	@Autowired
	MailMail mailMail;
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
		Account acLogin = getUserByEmail(email); 
		User user = new User(acLogin.getEmail(), acLogin.getPassword(), 
				true, true, true,true, getAuthorities(acLogin.getRole()));
		return user;
	}

	@Override
	public Account getUserByEmail(String email) {   
		return userRepositoty.findByEmail(email);
	}

	@Override
	public Account getById(int id) {
		return userRepositoty.findOne(id);
	}
	
	@Override
	public boolean checkByUserName(Account user) {
		Account check = userRepositoty.findByUserName(user.getUserName());  
		if(check.getPassword().equalsIgnoreCase(user.getPassword())) 
			return true;
		return false;
	}
	
	@Override
	public Account getByUserName(String userName) { 
		return userRepositoty.findByUserName(userName);
	}

	@Override
	public boolean insertUser(Account user) {
		// kiem tra userName co trung ko? 
		if(getUserByEmail(user.getEmail())!=null) 
			return false;
		String pwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		user.setRole(Role.ROLE_USER); 
		if(user.getImage()==null)
			user.setImage("user/default.jpg");
		userRepositoty.save(user);
		mailMail.sendMail("leductrongqb@gmail.com",
	    		   "dinhhoa.vta@gmail.com",
	    		   "Xac nhan dang ky thanh cong", 
	    		   "Testing only <a> User name: "+user.getUserName()+" </a> Hello to Online Auction.");
		/*mailMail = new MailMail("leductrongqb@gmail.com",
	    		   "dinhhoa.vta@gmail.com",
	    		   "Xac nhan dang ky thanh cong", 
	    		   "Testing only <a> User name: "+user.getUserName()+" </a> Hello to Online Auction."); */
		return true;
	}

	@Override
	public Account updateUser(Account user) {
		return userRepositoty.save(user);
	}

	@Override
	public boolean changePass(String oldPass, String newPass) { 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Account account = getUserByEmail(email);
		if(!passwordEncoder.matches(oldPass, account.getPassword()))
			return false;
		account.setPassword(passwordEncoder.encode(newPass));
		userRepositoty.save(account);
		return true;
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
