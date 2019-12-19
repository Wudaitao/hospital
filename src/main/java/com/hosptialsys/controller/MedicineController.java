package com.hosptialsys.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.LayuiData;
import com.hosptialsys.domain.MedicineList;
import com.hosptialsys.service.MedicineListService;
import com.hosptialsys.service.MedicineService;
import com.hosptialsys.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/med/")
public class MedicineController {
	
	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static Queue<String> prescribeQueue = new LinkedList<String>();
	public static Queue<String> takeMedicineQueue = new LinkedList<String>();
	@Autowired
	private  MedicineListService medicineListService;
	@Autowired
	private  MedicineService medicineService;
	
	// 返回队列中的下一个病人的相关信息
	@RequestMapping(value="nextPatient",method=RequestMethod.GET)
	public Object nextPatientML() {

		String userId = prescribeQueue.peek();
		// 检查是否还有记录
		System.out.println(userId);
		if(userId == null)
			return JsonData.buildError("暂时没有病人需要配药了");		
		// 构建返回数据
		List<MedicineList> medList = medicineListService.findByUserIdAndDate(userId, CommonUtil.getFormatedSystemTime());		
		System.out.println(userId);
		if(medList != null && medList.size() != 0)
			return LayuiData.buildSuccess(medList, medList.size());
		else 
			return LayuiData.buildError("该病人没有开药信息");
		
	}
	
	// 配药
	@RequestMapping(value="premed",method=RequestMethod.GET)
	public Object premed() {

		LocalDate date = LocalDate.now();
		String dateNow = date.format(formatter1);
		
		String userId = prescribeQueue.poll();
    	// 将已配药完成的加入到取药队列中
		takeMedicineQueue.add(userId);		
		// 构建返回数据
		List<MedicineList> medList = medicineListService.findByUserIdAndDate(userId, dateNow);
		
		// 更改药的库存
		for (MedicineList medicineList : medList) {
			String medName = medicineList.getMedName();
			Integer medNum = medicineList.getMedNum();
			// 未添加存量是否足够
			// 取药为负
			medicineService.updateMedicineStorage(medName, -medNum);
		}
		return JsonData.buildSuccess(userId, "配药成功！");		
	}
	
	// 取药
	@RequestMapping(value="takeMed",method=RequestMethod.GET)
	public Object takeMed() {
		LocalDate date = LocalDate.now();
		String dateNow = date.format(formatter1);
		//TODO: 更新状态  为 "已取药"
		String userId = takeMedicineQueue.poll();
			
		// 检查是否还有记录
		if(userId == null)
			return JsonData.buildError("没有病人需要取药了");
		
		// 查询user的今天的已付款条目
		List<MedicineList> medList = medicineListService.findByUserIdAndDate(userId, dateNow);
		
		// 更改药单的状态
		for (MedicineList medicineList : medList) {
			medicineListService.updateMlState(medicineList.getMlId(), "已取药");
		}		
		
		// 返回取药成功的ID
		return JsonData.buildSuccess(userId,"取药成功！");
		
	}
	
	// 返回病人的状态
	@RequestMapping(value="checkState",method=RequestMethod.GET)
	public Object checkState(HttpServletRequest request) {
		//TODO: 查看状态，输入ID，返回 "等待配药"|"等待取药"|"已取药"|"未缴费"
		String userId = request.getParameter("user_id");
		
		List<MedicineList> medList = medicineListService.findByUserIdAndDate(userId, CommonUtil.getFormatedSystemTime());
		
		if (medList.size() == 0) {
			return JsonData.buildError("该病人还没有开药！");
		}
		if(medList.get(0).getMlState().equals("已取药"))
			return JsonData.buildSuccess("已取药");

		for (String id : prescribeQueue) {
			if(id.equals(userId)) {
				return JsonData.buildSuccess("等待配药");
			}
		}
		for (String id : takeMedicineQueue) {
			if(id.equals(userId)) {
				return JsonData.buildSuccess("等待取药");
			}
		}		
		return JsonData.buildSuccess("未缴费");
		
	}

	
}
