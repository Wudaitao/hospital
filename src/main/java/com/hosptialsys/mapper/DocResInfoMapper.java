package com.hosptialsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.DocResInfo;

public interface DocResInfoMapper {

	/**
	 * 根据主键查找
	 * @param drId
	 * @return
	 */
	@Select("select * from docresinfo where dr_id = #{drId}")
	DocResInfo findById(@Param("drId")Integer drId);
	
	@Select("SELECT * FROM docresinfo WHERE user_id=#{userId} and dr_department=#{drDepartment} and dr_time_slot=#{drTimeSlot} and dr_date=#{drDate}")
	DocResInfo findDocInfo(@Param("userId") String userId,@Param("drDepartment") String drDepartment,@Param("drTimeSlot") String drTimeSlot,@Param("drDate") String drDate);

	@Select("SELECT * FROM docresinfo WHERE user_id is NULL and dr_department=#{drDepartment} and dr_time_slot=#{drTimeSlot} and dr_date=#{drDate}")
	DocResInfo findDocInfo1(@Param("drDepartment") String drDepartment,@Param("drTimeSlot") String drTimeSlot,@Param("drDate") String drDate);

	@Select("SELECT DISTINCT(dr_date) FROM docresinfo WHERE user_name=#{userName} and dr_department=#{drDepartment} and dr_date >= DATE_FORMAT(now(),'20%y-%m-%d')")		
	List<String> findDate(@Param("userName") String userName,@Param("drDepartment") String drDepartment);
	
	@Select("SELECT DISTINCT(dr_date) FROM docresinfo WHERE user_name is NULL and dr_department=#{drDepartment} and dr_date >= DATE_FORMAT(now(),'20%y-%m-%d')")		
	List<String> findDate1(@Param("drDepartment") String drDepartment);

	@Select("SELECT dr_time_slot FROM docresinfo WHERE user_name=#{userName} and dr_department=#{drDepartment} and dr_date=#{drDate}")		
	List<String> findTimeSlot(@Param("userName") String userName,@Param("drDepartment") String drDepartment, @Param("drDate") String drDate);

	@Select("SELECT dr_time_slot FROM docresinfo WHERE user_name is NULL and dr_department=#{drDepartment} and dr_date=#{drDate}")		
	List<String> findTimeSlot1(@Param("drDepartment") String drDepartment, @Param("drDate") String drDate);

	@Insert("INSERT INTO `docresinfo` ( `user_id`, `user_name`, `dr_department`, " +
	        "`dr_resv_num`, `dr_max_resv_num`, `dr_date`,`dr_time_slot`)" +
	        "VALUES" +
	        "(#{userId},#{userName},#{drDepartment},#{drResvNum},#{drMaxResvNum},#{drDate},#{drTimeSlot})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")//将自增ID映射到实体类的id字段
	int save(DocResInfo docResInfo);
	
	@Update("UPDATE docresinfo SET dr_resv_num=#{drResvNum} WHERE dr_id=#{drId}")
	int updateDocResInfo(DocResInfo docResInfo);
}
