package com.hosptialsys.domain;

public class Item {

	private String itemName;     //检查科目名称
	private Float  itemPrice;    //该检查科目的价格
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Float getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	
}
