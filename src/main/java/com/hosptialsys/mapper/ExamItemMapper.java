package com.hosptialsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.ExamItem;


public interface ExamItemMapper {

	/**
	 * 根据主键查找
	 * @param checkItemId
	 * @return
	 */
	@Select("select * from examitem where check_item_id = #{checkItemId}")
	ExamItem findById(@Param("checkItemId")Integer checkItemId);
	
	@Select("SELECT * FROM examitem WHERE user_id=#{userId} AND check_date=#{checkDate}")
	List<ExamItem> findByUserDate(@Param("userId") String userId, @Param("checkDate")String checkDate);
	
	@Select("SELECT SUM(item_price) FROM examitem JOIN item ON item_name=check_item_name "
			+ "WHERE user_id=#{userId} AND check_date=#{checkDate} AND check_result IS NOT NULL")
	Float checkSum(@Param("userId")String userId, @Param("checkDate")String checkDate);

	@Insert("INSERT INTO `examitem` ( `user_id`, `check_item_name`, `check_result`," +
	        "`check_is_paid`, `check_date`, `check_user_name`,`check_item_content`)" +
	        "VALUES" +
	        "(#{userId},#{checkItemName},#{checkResult},#{checkIsPaid},#{checkDate},#{checkUserName},#{checkItemContent})")
    @Options(useGeneratedKeys=true, keyProperty="checkItemId", keyColumn="check_item_id")//将自增ID映射到实体类的id字段
	int save(ExamItem examItem);
	
	@Update("UPDATE examitem SET check_is_paid=1 WHERE user_id=#{userId} AND check_date=#{checkDate} AND check_result IS NOT NULL")
	int updateCheckIsPaid(@Param("userId")String userId,@Param("checkDate")String checkDate);
	
	@Update("UPDATE examitem SET check_result=#{checkResult} WHERE check_item_id=#{checkItemId}")
	int updateCheckResult(@Param("checkItemId")Integer checkItemId, @Param("checkResult")String checkResult);
	
}
