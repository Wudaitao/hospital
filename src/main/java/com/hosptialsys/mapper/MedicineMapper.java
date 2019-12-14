package com.hosptialsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.Medicine;

public interface MedicineMapper {

	// 根据主键查找
	@Select("select * from medicine where medicine_id = #{medicineId}")
	Medicine findById(@Param("medicineId")Integer medicineId);
	
	// 根据药名查询
	@Select("select * from medicine where medicine_name = #{medicineName}")
	Medicine findByName(@Param("medicineName")String medicineName);
	
	//查询所有药品名
	@Select("select medicine_name from medicine")
	List<String> findAll();
	
	//根据药名查库存
	@Select("select medicine_storage from medicine where medicine_name = #{medicineName}")
	Integer findStorageByName(@Param("medicineName")String medicineName);
	
	// 插入新药
	@Insert("Insert into `medicine` (`medicine_id`, `medicine_name`,`medicine_storage`,`medicine_price`) values "
			+ "(#{medicineId},#{medicineName},#{medicineStorage},#{medicinePrice})")
	@Options(useGeneratedKeys = true, keyProperty = "medicineId",keyColumn = "medicine_id")
	int save(Medicine medicine);
	
	// 更新药名
	@Update("Update medicine set medicine_name=#{newName} where medicine_id=#{medicineId}")
	int updateMedicineName(@Param("medicineId")Integer medicineId, @Param("newName")String newName);
	
	// 更新药库存
	@Update("Update medicine set medicine_storage = medicine_storage + #{newStorage} where medicine_name=#{medicineName}")
	int updateMedicineStorage(@Param("medicineName")String medicineName, @Param("newStorage")Integer newStorage);
	
	// 更新药价格
	@Update("Update medicine set medicine_price=#{newPrice} where medicine_id=#{medicineId}")
	int updateMedicinePrice(@Param("medicineId")Integer medicineId, @Param("newPrice")Float newPrice);
	
	
}
