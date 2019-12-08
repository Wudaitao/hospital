package com.hosptialsys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hosptialsys.domain.User;


public interface UserMapper {

	/**
	 * 根据主键查找
	 * @param userId
	 * @return
	 */
	@Select("select * from user where user_id = #{userId}")
	User findById(@Param("userId")String userId);
	
	@Select("SELECT * FROM user WHERE user_id=#{userId} and user_password=#{userPassword}")
	User findUser(@Param("userId") String userId,@Param("userPassword") String userPassword);

	@Insert("INSERT INTO `user` ( `user_id`, `user_name`, " +
	        "`user_gender`, `user_password`, `user_age`)" +
	        "VALUES" +
	        "(#{userId},#{userName},#{userGender},#{userPassword},#{userAge})")
    //@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")//将自增ID映射到实体类的id字段
	int save(User user);
	
}
