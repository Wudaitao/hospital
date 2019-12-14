package com.hosptialsys.service;

import java.util.List;

import com.hosptialsys.domain.Medicine;;

public interface MedicineService {
	Medicine findById(Integer medicineId);
	Medicine findByName(String medicineName);
	List<String> findAll();
	Integer findStorgeByName(String medicineName);
	int save(Medicine medicine);
	int updateMedicineName(Integer medicineId, String newName);
	int updateMedicineStorage(String medicineName, Integer newStorage);
	int updateMedicinePrice(Integer medicineId, Float newPrice);
}
