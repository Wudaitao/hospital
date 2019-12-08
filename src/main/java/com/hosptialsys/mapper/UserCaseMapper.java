package com.hosptialsys.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.UserCase;


public interface UserCaseMapper {

	/**
	 * 根据主键查找
	 * @param caseId
	 * @return
	 */
	@Select("select * from usercase where case_id = #{caseId}")
	UserCase findById(@Param("caseId")Integer caseId);
	
	@Select("SELECT * FROM usercase WHERE user_id=#{userId}")
	List<UserCase> findByUserId(@Param("userId") String userId);

	@Insert("INSERT INTO `usercase` ( `user_id`, `case_is_finish`, " +
	        "`case_date`, `case_result`)" +
	        "VALUES" +
	        "(#{userId},#{caseIsFinish},#{caseDate},#{caseResult})")
    @Options(useGeneratedKeys=true, keyProperty="caseId", keyColumn="case_id")//将自增ID映射到实体类的id字段
	int save(UserCase userCase);
	
	@Update("UPDATE usercase SET case_is_finish=#{caseIsFinish} WHERE case_id=#{caseId}")
	int updateCaseIsFinish(UserCase userCase);
}
