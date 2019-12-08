package com.hosptialsys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.UserCase;
import com.hosptialsys.service.UserCaseService;
import com.hosptialsys.service.UserService;

@RestController
@RequestMapping("/api/v1/doctor/")
public class DoctorController {

	@Autowired
	private PatientController patientController;
	@Autowired
	private UserCaseService userCaseService;
	@Autowired
	private UserService userService;
	@RequestMapping(value="pull",method=RequestMethod.GET)
	public Object getNextPatientInfo(String Id) {
		JsonData data = patientController.getPatientInfo(Id);
		if (data.getData() != null) {
			String patientId = (String)data.getData();
			return JsonData.buildSuccess(userService.findById(patientId));
		}
		else {
			return data;
		}
	}
	
	@RequestMapping(value="getPatientCases",method=RequestMethod.GET)
	public Object getPatientCases(String user_id) {
		if (!user_id.equals("")) {
			List<UserCase> userCases = userCaseService.findByUserId(user_id);
			if (userCases.size() != 0) {
				return JsonData.buildSuccess(userCases, "查询该病人病历成功");
			}
			else {
				return JsonData.buildError("没有该病人病历数据");
			}
		}
		else {
			return JsonData.buildError("userId不能为空");
		}
	}
	
}
