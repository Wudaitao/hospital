package com.hosptialsys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hosptialsys.domain.ExamItem;
import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.UserCase;
import com.hosptialsys.service.ExamItemService;
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
	@Autowired
	private ExamItemService examItemService;
	
	/*
	 * 拉取下一个病人信息的api，叫号功能
	 */
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
	
	/*
	 * 获取病人历史信息的api
	 */
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
	
	/*
	 * 提交一条新的病历api
	 */
	@RequestMapping(value="savePatientCase",method=RequestMethod.GET)
	public Object savePatientCase(HttpServletRequest request) {
		UserCase userCase = new UserCase();
		userCase.setUserId(request.getParameter("user_id"));
		userCase.setCaseDate(request.getParameter("case_date"));
		userCase.setCaseIsFinish(0);
		userCase.setCaseResult(request.getParameter("case_result"));
		Integer caseId = userCaseService.save(userCase);
		return JsonData.buildSuccess(caseId, "保存成功！");
	}

    /*
     * 提交一条检查申请api
     */
	@RequestMapping(value="saveCheckItem",method=RequestMethod.GET)
	public Object saveCheckItem(HttpServletRequest request) {
		String userId = request.getParameter("user_id");
		String checkUserName = request.getParameter("check_user_name");
		String checkItemName = request.getParameter("check_item_name");
		String checkDate = request.getParameter("check_date");
		String checkItemContent = request.getParameter("check_item_content");
		ExamItem examItem = new ExamItem();
		examItem.setUserId(userId);
		examItem.setCheckDate(checkDate);
		examItem.setCheckIsPaid("0");
		examItem.setCheckItemName(checkItemName);
		examItem.setCheckUserName(checkUserName);
		examItem.setCheckItemContent(checkItemContent);
		int checkItemId = examItemService.save(examItem);
		return JsonData.buildSuccess(checkItemId, "提交成功！");
	}

	/*
	 * 更新检验结果api
	 */
	@RequestMapping(value="updateCheckResult",method=RequestMethod.GET)
	public Object updateCheckResult(Integer check_item_id, String check_result) {
		examItemService.updateCheckResult(check_item_id, check_result);
		return JsonData.buildSuccess("更新成功！");
	}

	/*
	 * 计算检验费用，并更新缴费情况api
	 */
	@RequestMapping(value="checkSum",method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object updateCheckIsPaid(HttpServletRequest request) {
		String userId = request.getParameter("user_id");
		String checkDate = request.getParameter("check_date");
		Float total = examItemService.checkSum(userId, checkDate);
		examItemService.updateCheckIsPaid(userId, checkDate);
		return JsonData.buildSuccess(total, "更新成功！");
	}

}
