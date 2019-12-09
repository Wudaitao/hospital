package com.hosptialsys.service;

import com.hosptialsys.domain.Item;

public interface ItemService {

	Item findByName(String itemName);
	
	int save(Item item);
	
	int updateItemPrice(String itemName, Float itemPrice);
}
