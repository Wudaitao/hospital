package com.hosptialsys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.Item;


public interface ItemMapper {

	/**
	 * 根据主键查找
	 * @param ItemName
	 * @return
	 */
	@Select("select * from item where item_name = #{itemName}")
	Item findByName(@Param("itemName")String itemName);
	
	@Insert("INSERT INTO `item` ( `item_name`, `item_price`)" +
	        "VALUES" +
	        "(#{itemName},#{itemPrice})")
    @Options(useGeneratedKeys=true, keyProperty="itemName", keyColumn="item_name")//将自增ID映射到实体类的id字段
	int save(Item item);
	
	@Update("UPDATE item SET item_price=#{itemPrice} WHERE item_name=#{itemName}")
	int updateItemPrice(@Param("itemName")String itemName, @Param("itemPrice")Float itemPrice);
}
