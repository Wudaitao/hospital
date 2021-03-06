package com.hosptialsys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.ResInfo;

public interface ResInfoMapper {
	/**
	 * 根据主键查找
	 * @param resvId
	 * @return
	 */
	@Select("select * from reservationinfo where resv_id = #{resvId}")
	ResInfo findById(@Param("resvId")Integer resvId);
	
	@Select("select * from reservationinfo where user_id=#{userId} and resv_doctor_id=#{resvDoctorId} "
			+ "and resv_department=#{resvDepartment} and resv_time_slot=#{resvTimeSlot} and resv_date=#{resvDate} and resv_is_valid=1 ")
	ResInfo findByUserIdAndDate(@Param("userId") String userId,@Param("resvDoctorId") String resvDoctorId,
			@Param("resvDepartment") String resvDepartment,@Param("resvTimeSlot") String resvTimeSlot,@Param("resvDate") String resvDate);
	
	@Select("select * from reservationinfo where user_id=#{userId} and resv_department=#{resvDepartment} and resv_time_slot=#{resvTimeSlot} "
			+ "and resv_date=#{resvDate} and resv_is_valid=1 ")
	ResInfo findByUserIdAndDate1(@Param("userId") String userId,@Param("resvDepartment") String resvDepartment,
			@Param("resvTimeSlot") String resvTimeSlot,@Param("resvDate") String resvDate);
	
	
	@Update("UPDATE reservationinfo SET resv_is_valid=#{resvIsValid} WHERE resv_id=#{resvId}")
	int updateState(@Param("resvIsValid")String resvIsValid,@Param("resvId")Integer resvId);
	
	@Insert("INSERT INTO `reservationinfo` ( `user_id`, `resv_type`, `resv_department`, " +
	        "`resv_doctor_id`, `resv_doctor_name`, `resv_is_valid`, "
	        + "`resv_num`,`resv_time_slot`,`resv_date`,`resv_online`)" +
	        "VALUES" +
	        "(#{userId},#{resvType},#{resvDepartment},#{resvDoctorId},"
	        + "#{resvDoctorName},#{resvIsValid},#{resvNum},#{resvTimeSlot},#{resvDate},#{resvOnline})")
    @Options(useGeneratedKeys=true, keyProperty="resvId", keyColumn="resv_id")//将自增ID映射到实体类的id字段
	int save(ResInfo resInfo);
}
