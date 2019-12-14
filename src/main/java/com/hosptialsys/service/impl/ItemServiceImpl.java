package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.Item;
import com.hosptialsys.mapper.ItemMapper;
import com.hosptialsys.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired 
	private ItemMapper itemMapper;
	
	@Override
	public Item findByName(String itemName) {
		return itemMapper.findByName(itemName);
	}

	@Override
	public int save(Item item) {
		return itemMapper.save(item);
	}

	@Override
	public int updateItemPrice(String itemName, Float itemPrice) {
		return itemMapper.updateItemPrice(itemName, itemPrice);
	}

	@Override
	public List<String> findAll() {
		return itemMapper.findAll();
	}

	@Override
	public Float getPrice(String itemName) {
		return itemMapper.getPrice(itemName);
	}

	
}
