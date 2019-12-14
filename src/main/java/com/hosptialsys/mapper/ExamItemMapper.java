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
	
	@Select("select sum(check_payment) from examitem where user_id=#{userId} AND check_date=#{checkDate} "
			+ "AND check_result='未做检查' AND check_is_paid='1' ")
	Float checkSum(@Param("userId")String userId, @Param("checkDate")String checkDate);

	@Select("select sum(check_payment) from examitem where user_id=#{userId} AND check_date=#{checkDate}")
	Float getTotal(@Param("userId")String userId, @Param("checkDate")String checkDate);

	@Insert("INSERT INTO `examitem` ( `user_id`, `check_item_name`, `check_result`," +
	        "`check_is_paid`, `check_date`, `check_user_name`,`check_item_content`,"
	        + "`doctor_id`,`check_doctor_id`,`check_payment`)" +
	        "VALUES" +
	        "(#{userId},#{checkItemName},#{checkResult},#{checkIsPaid},#{checkDate},"
	        + "#{checkUserName},#{checkItemContent},#{doctorId},#{checkDoctorId},#{checkPayment})")
    @Options(useGeneratedKeys=true, keyProperty="checkItemId", keyColumn="check_item_id")//将自增ID映射到实体类的id字段
	int save(ExamItem examItem);
	
	@Update("UPDATE examitem SET check_is_paid=#{checkIsPaid} WHERE user_id=#{userId} AND check_date=#{checkDate}")
	int updateCheckIsPaid(@Param("userId")String userId,@Param("checkDate")String checkDate,@Param("checkIsPaid")String checkIsPaid);
	
	@Update("UPDATE examitem SET check_result=#{checkResult} WHERE check_item_id=#{checkItemId}")
	int updateCheckResult(@Param("checkItemId")Integer checkItemId, @Param("checkResult")String checkResult);
	
}
