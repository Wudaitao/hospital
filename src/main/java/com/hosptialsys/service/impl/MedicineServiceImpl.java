package com.hosptialsys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosptialsys.domain.Medicine;
import com.hosptialsys.mapper.MedicineMapper;
import com.hosptialsys.service.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService {	
	@Autowired 
	private MedicineMapper medicineMapper;
	
	@Override
	public Medicine findByName(String medicineName) {
		return medicineMapper.findByName(medicineName);
	}

	@Override
	public Medicine findById(Integer medicineId) {
		return medicineMapper.findById(medicineId);
	}

	@Override
	public int save(Medicine medicine) {
		return medicineMapper.save(medicine);
	}

	@Override
	public int updateMedicineName(Integer medicineId, String newName) {
		return medicineMapper.updateMedicineName(medicineId, newName);
	}

	@Override
	public int updateMedicineStorage(String medicineName, Integer newStorage) {
		return medicineMapper.updateMedicineStorage(medicineName, newStorage);
	}

	@Override
	public int updateMedicinePrice(Integer medicineId, Float newPrice) {
		return medicineMapper.updateMedicinePrice(medicineId, newPrice);
	}

	@Override
	public List<String> findAll() {
		return medicineMapper.findAll();
	}

	@Override
	public Integer findStorgeByName(String medicineName) {
		return medicineMapper.findStorageByName(medicineName);
	}
	
}
