package com.hosptialsys.domain;

public class Medicine {
	private Integer medicineId;			//药的ID，主键
	private String medicineName;		//药的名字
	private Integer medicineStorage;	//药的剩余库存
	private Float medicinePrice;		//药的价格
	
	public Integer getMedicineId() {
		return medicineId;
	}
	
	public void setMedicineId(Integer medicineId) {
		this.medicineId = medicineId;
	}
	
	public String getMedicineName() {
		return medicineName;
	}
	
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	
	public Integer getMedicineStorage() {
		return medicineStorage;
	}
	
	public void setMedicineStorage(Integer medicineStorage) {
		this.medicineStorage = medicineStorage;
	}
	
	public Float getMedicinePrice() {
		return medicinePrice;
	}
	
	public void setMedicinePrice(Float medicinePrice) {
		this.medicinePrice = medicinePrice;
	}


	
	
}
