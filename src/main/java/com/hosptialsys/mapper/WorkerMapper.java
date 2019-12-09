package com.hosptialsys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hosptialsys.domain.Worker;


public interface WorkerMapper {
	/**
	 * 根据主键查找
	 * @param userId
	 * @return
	 */
	@Select("select * from worker where user_id = #{userId}")
	Worker findById(@Param("userId")String userId);
	
	@Select("select * from worker where user_name = #{userName}")
	Worker findByName(@Param("userName")String userName);
	
	@Select("SELECT * FROM worker WHERE user_id=#{userId} and user_password=#{userPassword}")
	Worker findUser(@Param("userId") String userId,@Param("userPassword") String userPassword);

	@Insert("INSERT INTO `worker` ( `user_id`, `user_name`, " +
	        "`user_gender`, `user_password`, `user_age`,`worker_type`, `worker_department`)" +
	        "VALUES" +
	        "(#{userId},#{userName},#{userGender},#{userPassword},#{userAge},#{workerType},#{workerDepartment})")
    //@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")//将自增ID映射到实体类的id字段
	int save(Worker worker);
}
