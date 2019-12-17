package com.hosptialsys.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hosptialsys.domain.DocResInfo;
import com.hosptialsys.domain.ExamItem;
import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.LayuiData;
import com.hosptialsys.domain.User;
import com.hosptialsys.domain.UserCase;
import com.hosptialsys.domain.Worker;
import com.hosptialsys.service.DocResvInfoService;
import com.hosptialsys.service.ExamItemService;
import com.hosptialsys.service.ItemService;
import com.hosptialsys.service.MedicineService;
import com.hosptialsys.service.UserCaseService;
import com.hosptialsys.service.UserService;
import com.hosptialsys.service.WorkerService;

@RestController
@RequestMapping("/api/v1/doctor/")
public class DoctorController {

	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private PatientController patientController;
	@Autowired
	private UserCaseService userCaseService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExamItemService examItemService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private WorkerService workService;
	@Autowired
	private DocResvInfoService docResvInfoService;
	/*
	 * 获得医生信息api
	 */
	@RequestMapping(value="getDoctorInfo",method=RequestMethod.POST)
	public Object getDoctorInfo(@RequestParam(value = "doctor_id",required=true)String doctorId) {
		Worker doctor = workService.findById(doctorId);
		if (doctor == null) {
			return JsonData.buildError("没有该医生！");
		} else {
			return JsonData.buildSuccess(doctor);
		}
	}
	
	/*
	 * 拉取下一个病人信息的api，叫号功能
	 */
	@RequestMapping(value="pull")
	public Object getNextPatientInfo(String id) {
		JsonData data = patientController.getPatientInfo(id);
		if (data.getData() != null) {
			String patientId = (String)data.getData();
			User user = userService.findById(patientId);
			List<UserCase> userCases = userCaseService.findByUserId(patientId);
			Object [] info= {user, userCases};
			return JsonData.buildSuccess(info);
		}
		else {
			return data;
		}
	}
	
	/*
	 * 获取病人历史信息的api
	 */
	@RequestMapping(value="getPatientCases",method=RequestMethod.POST)
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
			return JsonData.buildError("userId为空");
		}
	}
	
	/*
	 * 提交一条新的病历api
	 */
	@RequestMapping(value="savePatientCase",method=RequestMethod.POST)
	public Object savePatientCase(HttpServletRequest request) {
		UserCase userCase = new UserCase();
		String userId = request.getParameter("user_id");
		userCase.setUserId(userId);
		userCase.setCaseResult(request.getParameter("case_result"));
		userCase.setCaseFirst(request.getParameter("case_first"));
		userCase.setCasePerfance(request.getParameter("case_perfance"));
		LocalDate date = LocalDate.now();
		String dateNow = date.format(formatter1);
		userCase.setCaseDate(dateNow);
		userCase.setCaseIsFinish(0);
		if (userCaseService.findByUserDate(userId, dateNow) != null) {
			return JsonData.buildError("已经提交，请勿重复保存！");
		}
		Integer caseId = userCaseService.save(userCase);
		return JsonData.buildSuccess(caseId, "保存成功！");
	}

    /*
     * 提交检查申请api
     */
	@RequestMapping(value="saveCheckItem",method=RequestMethod.POST)
	public Object saveCheckItem(HttpServletRequest request,
					@RequestParam(value = "row_size",required=true)int rowSize) {
		
		LocalDate date = LocalDate.now();
		String checkDate = date.format(formatter1);
		String userId = request.getParameter("user_id");
		String checkUserName = request.getParameter("user_name");
		String doctorId = request.getParameter("doctor_id");
		ExamItem examItem = new ExamItem();
		examItem.setUserId(userId);
		examItem.setCheckDate(checkDate);
		examItem.setCheckIsPaid("0");
		examItem.setCheckUserName(checkUserName);
		examItem.setDoctorId(doctorId);
		examItem.setCheckResult("未做检查");
		String[] checkItemNames = request.getParameterValues("inspection[]");
		String[] checkItemContents = request.getParameterValues("inspection1[]");
		for (int i = 0; i < rowSize; i++) {
			String checkItemName = checkItemNames[i];
			Float price = itemService.getPrice(checkItemName);
			examItem.setCheckItemName(checkItemName);
			String checkItemContent = checkItemContents[i];
			examItem.setCheckItemContent(checkItemContent);
			examItem.setCheckPayment(price);
			examItemService.save(examItem);
			//System.out.println(checkItemName + " " + checkItemContent);
		}
		return JsonData.buildSuccess(1,"提交成功！");
	}

	/*
	 * 更新检验结果api
	 */
	@RequestMapping(value="updateCheckResult",method=RequestMethod.GET)
	public Object updateCheckResult(Integer check_item_id, String check_result,String check_doctor_id) {
		examItemService.updateCheckResult(check_item_id, check_result,check_doctor_id);
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
		examItemService.updateCheckIsPaid(userId, checkDate, "1");
		return JsonData.buildSuccess(total, "更新成功！");
	}

	/*
	 * 查找所有检验项目名api
	 */
	@RequestMapping(value="getItems",method=RequestMethod.POST)
	public Object getItems() {
		List<String> items = itemService.findAll();
		if (items.size() == 0) {
			return JsonData.buildError("没有检查项目！");
		} else {
			return JsonData.buildSuccess(items);
		}
	}
	
	/*
	 * 查找所有药品名api
	 */
	@RequestMapping(value="getMedicines",method=RequestMethod.POST)
	public Object getMedicines() {
		List<String> medicines = medicineService.findAll();
		if (medicines.size() == 0) {
			return JsonData.buildError("没有药品种类！");
		} else {
			return JsonData.buildSuccess(medicines);
		}
	}
	/*
	 * 查找所有药品库存api
	 */
	@RequestMapping(value="getMedicineStorage",method=RequestMethod.POST)
	public Object getMedicineStorage(@RequestParam(value = "medicine_name",required=true)String medicineName) {
		Integer storage = medicineService.findStorgeByName(medicineName);
		if (storage == null) {
			return JsonData.buildError("没有药品种类！");
		} else {
			return JsonData.buildSuccess(storage);
		}
	}
	
	/*
	 * 根据病人Id和日期查找检验项目
	 */
	@RequestMapping(value="getExamItems",method=RequestMethod.POST)
	public Object getExamItems(@RequestParam(value = "user_id",required=true)String userId) {
		LocalDate date = LocalDate.now();
		String checkDate = date.format(formatter1);
		List<ExamItem> examItems = examItemService.findByUserDate(userId, checkDate);
		if (examItems.size()==0) {
			return JsonData.buildError("该病人没有检验项目！");
		}
		return JsonData.buildSuccess(examItems);
	}
	/*
	 * 根据病人Id和日期查找检验项目
	 */
	@RequestMapping(value="getDefaultExamItem",method=RequestMethod.GET)
	public Object getDefaultExamItem() {
		String userId = "0";
		LocalDate date = LocalDate.now();
		String checkDate = date.format(formatter1);
		List<ExamItem> examItems = examItemService.findByUserDate(userId, checkDate);
		if (examItems.size()==0) {
			return JsonData.buildError("没有检验项目！");
		}
		return JsonData.buildSuccess(examItems);
	}
	
	/*
	 * 根据病人Id和日期查找检验项目
	 */
	@RequestMapping(value="getExamItem",method=RequestMethod.GET)
	public Object getExamItem(@RequestParam(value = "user_id",required=true)String userId) {
		LocalDate date = LocalDate.now();
		String checkDate = date.format(formatter1);
		List<ExamItem> examItems = examItemService.findByUserDate(userId, checkDate);
		if (examItems.size()==0) {
			return JsonData.buildError("该病人没有检验项目！");
		}
		return LayuiData.buildSuccess(examItems, examItems.size());
	}
	
	// 添加医生预约信息
	@RequestMapping(value="addDocResvInfo", method=RequestMethod.POST)
	public Object addDocResvInfo(HttpServletRequest request) {
		
		// user_id work_time[0][]
		String docId = request.getParameter("user_id");
		// 在worker表中查询到医生的信息
		Worker doctor = workService.findById(docId);
		
		Integer rows = Integer.valueOf(request.getParameter("cnt"));
		// work_time[0][]
		System.out.println(rows);
		for(int i = 0;i < rows; i++) {
			// values --> [就诊日期,开始时间,结束时间,最大接诊数]
			String[] values = request.getParameterValues("work_time["+i+"][]");
			
			DocResInfo docInfo = new DocResInfo();
			// 基本信息
			docInfo.setUserId(docId);
			docInfo.setUserName(doctor.getUserName());
			
			// 设置日期 
			docInfo.setDrDate(values[0]);
			String[] arr1 = values[1].split(":");
			String[] arr2 = values[2].split(":");
			docInfo.setDrTimeSlot(arr1[0]+":"+arr1[1]+"-"+arr2[0]+":"+arr2[1]);
			
			// 最大预约人数
			docInfo.setDrResvNum(0);           // 初始化0个人预约
			docInfo.setDrMaxResvNum(Integer.valueOf(values[3]));
		
			// 科室设置
			docInfo.setDrDepartment(doctor.getWorkerDepartment());
			
			// 存入数据库
			docResvInfoService.save(docInfo);
		}
		return JsonData.buildSuccess("新增成功");
	}
	
}
