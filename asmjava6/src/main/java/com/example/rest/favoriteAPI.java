package com.example.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Favorite;
import com.example.jparepository.AccountRepository;
import com.example.jparepository.FavoriteRepository;

@RestController
@CrossOrigin("*")
public class favoriteAPI {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	FavoriteRepository favoriteRepository;

	@GetMapping("/rest/accounts/{username}")
	public Account getAccountByUsername(@PathVariable String username) {
		Account account = accountRepository.findByUserName(username);
		return account;
	}
	
	@GetMapping("/rest/favorites")
	public List<Favorite> getFavorite() {
		 List<Favorite> favorites = favoriteRepository.findAll();
		return favorites;
	}
	
	
}
