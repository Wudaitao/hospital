package com.hosptialsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.MedicineList;

public interface MedicineListMapper {
	
	@Select("select * from medicinelist where  ml_id=#{medicineListId}") 
	MedicineList findById(@Param("medicineListId")Integer medicineListId);
	
	@Select("select * from medicinelist where user_id=#{userId} and ml_date=#{mlDate}")
	List<MedicineList> findByUserIdAndDate(@Param("userId")String userId, @Param("mlDate")String mlDate);
	
	@Select("select sum(ml_total_price) from medicinelist where user_id=#{userId} and ml_date=#{mlDate}")
	Float getTotal(@Param("userId")String userId, @Param("mlDate")String mlDate);
	
	@Select("select sum(ml_total_price) from medicinelist where user_id=#{userId} and ml_date=#{mlDate} and ml_is_paid='1' and ml_state='已退药' ")
	Float returnTotal(@Param("userId")String userId, @Param("mlDate")String mlDate);

	@Insert("insert into `medicinelist` (`user_id`, `ml_doctor_id`,`med_name`, `med_num`, `ml_dosage`, `ml_date`, `ml_total_price`, `ml_is_paid`,`ml_state`) values"
			+ "(#{userId}, #{mlDoctorId}, #{medName}, #{medNum}, #{mlDosage}, #{mlDate}, #{mlTotalPrice}, #{mlIsPaid},#{mlState})")
	@Options(useGeneratedKeys = true, keyProperty = "mlId",keyColumn = "ml_id")
	int save(MedicineList ml);
	
	@Update("update medicinelist set ml_is_paid=#{mlIsPaid} where user_id=#{userId} and ml_date=#{mlDate}")
	int updateIsPaid(@Param("userId")String userId, @Param("mlDate")String mlDate, @Param("mlIsPaid")String mlIsPaid);
	
	@Update("update medicinelist set ml_state='已退药' where ml_id=#{medicineListId}")
	int updateMlState(@Param("medicineListId")Integer medicineListId);

}
