package com.hosptialsys.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.LayuiData;
import com.hosptialsys.domain.MedicineList;
import com.hosptialsys.service.MedicineListService;
import com.hosptialsys.service.MedicineService;
import com.hosptialsys.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/medlist/")
public class MedicineListController {
	
	
	@Autowired
	MedicineListService medicineListService;
	@Autowired
	MedicineService medicineService;
	// 新增处方单
	@RequestMapping(value="addML",method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object addML(HttpServletRequest request) {
		String userId = request.getParameter("user_id");
		String doctorId = request.getParameter("doctor_id");
		System.out.println("IDS:" + userId + " " + doctorId);
		MedicineList mlList = new MedicineList();
		Integer rowCnt = Integer.valueOf(request.getParameter("rows_cnt"));
		mlList.setUserId(userId);
		mlList.setMlDoctorId(doctorId);
		mlList.setMlIsPaid("0");
		mlList.setMlDate(CommonUtil.getFormatedSystemTime());
		mlList.setMlState("未取药");
		for(int i = 0;i < rowCnt; i++) {
			String[] line = request.getParameterValues("prescription[" + i + "][]");
			Float price = Float.valueOf(medicineService.findByName(line[0]).getMedicinePrice());
			mlList.setMedName(line[0]);
			mlList.setMedNum(Integer.valueOf(line[1]));
			mlList.setMlDosage(line[2]);
			mlList.setMlTotalPrice(price * Float.valueOf(line[1]));
			medicineListService.save(mlList);
		}
		return JsonData.buildSuccess(1,"提交成功！");
	}
	
	// 查询指定用户ID的所有药单记录
	@RequestMapping(value="showML",method=RequestMethod.POST)
	public Object showML(HttpServletRequest request) {
		String userId = request.getParameter("user_id");
		List<MedicineList> result = medicineListService.findByUserIdAndDate(userId, CommonUtil.getFormatedSystemTime());
		if(result.size() != 0)
			return JsonData.buildSuccess(result);
		else 
			return JsonData.buildError("该病人无开药信息");
	}
	
	// 查询指定用户ID的药单记录
	@RequestMapping(value="showMLs",method=RequestMethod.GET)
	public Object showMLs(HttpServletRequest request) {
		String userId = request.getParameter("user_id");
		List<MedicineList> result = medicineListService.findByUserIdAndDate(userId, CommonUtil.getFormatedSystemTime());
		Integer count = result.size();
		if(count != 0)
			return LayuiData.buildSuccess(result,count);
		else 
			return LayuiData.buildError("该病人无开药信息");
	}
	
	@RequestMapping(value="defaultMLs",method=RequestMethod.GET)
	public Object defaultMLs(HttpServletRequest request) {
		String userId = "0";
		List<MedicineList> result = medicineListService.findByUserIdAndDate(userId, CommonUtil.getFormatedSystemTime());
		Integer count = result.size();
		if(count != 0)
			return LayuiData.buildSuccess(result,count);
		else 
			return LayuiData.buildError("暂无药单信息");
	}
	
	//退药
	@RequestMapping(value="withdrawMl",method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object withdrawMl(@RequestParam(value = "ml_id",required=true)Integer mlId) {
		MedicineList medicineList = medicineListService.findById(mlId);
		if (medicineList == null) {
			return JsonData.buildError("没有该药单信息！");
		}
		Integer num = medicineList.getMedNum();
		String medName = medicineList.getMedName();
		medicineListService.updateMlState(mlId,"已退药");
		medicineService.updateMedicineStorage(medName, num);
		return JsonData.buildSuccess(num);
	}
}
