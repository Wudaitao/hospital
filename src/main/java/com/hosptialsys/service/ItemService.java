package com.hosptialsys.service;

import java.util.List;

import com.hosptialsys.domain.Item;

public interface ItemService {

	Item findByName(String itemName);
	
	List<String> findAll();
	
	int save(Item item);
	
	int updateItemPrice(String itemName, Float itemPrice);
	
	Float getPrice(String itemName);
}
