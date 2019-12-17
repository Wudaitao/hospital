package com.hosptialsys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.Worker;
import com.hosptialsys.service.WorkerService;

@Controller
@RequestMapping("/api/v1/user/")
public class LoginController {

	private static final String EXPERT = "专家";
	private static final String GENERAL_DOCTOR = "普通医生";
	private static final String REGISTER_STAFF = "挂号人员";
	private static final String TOLL_MAN = "财务人员";
	private static final String PHARMACIAT = "药剂师";
	private static final String FENZHEN = "分诊人员";
	private static final String TESTMAN = "检验医师";
	@Autowired
	private WorkerService workerSevice;
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Object login(ModelMap modelMap,HttpServletRequest request,RedirectAttributes attributes) {
		String userId = request.getParameter("user_id");
		String userPassword = request.getParameter("user_password");
		Worker worker = workerSevice.findUser(userId, userPassword);
		if (worker != null) {
			String workType = worker.getWorkerType();
			if (workType.equals(EXPERT)) {
				return "redirect:/html/doctor.html";         //专家页面
			}
			else if (workType.equals(GENERAL_DOCTOR)) {
				return "redirect:/html/doctor.html";         //普通医生页面
			}
			else if (workType.equals(REGISTER_STAFF)) {
				return "redirect:/html/rigist.html";         //挂号人员页面
			}
			else if (workType.equals(TOLL_MAN)) { 
				return "redirect:/html/finance.html";         //收费人员页面
			}
			else if (workType.equals(PHARMACIAT)) {
				return "redirect:/html/pharmacy.html";         //药剂师页面
			}
			else if (workType.equals(FENZHEN)) {
				return "redirect:/html/triage.html";         //分诊台页面
			}
			else if (workType.equals(TESTMAN)) {
				return "redirect:/html/test.html";         //分诊台页面
			}
			else {
				return JsonData.buildError("不识别职位类别！");
			}
		}
		else {
			attributes.addAttribute("msg","密码错误");
			return "redirect:/api/v1/user/toLogin";
		}
	}
	
	@ResponseBody 
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object regist(HttpServletRequest request) {
		String userName = request.getParameter("user_name");
		String userId = request.getParameter("user_id");
		String userAge = request.getParameter("user_age");
		String userPassword = request.getParameter("user_password");
		String userGender = request.getParameter("gender");
		String workDepartment = request.getParameter("work_department");
		String workType = request.getParameter("work_type");
		if (workerSevice.findById(userId) == null) {
			Worker worker = new Worker(userId, userName, userGender, userAge, userPassword, workType, workDepartment);
			int id = workerSevice.saveWorker(worker);
			return JsonData.buildSuccess(id, "注册成功，请前往登录页面");
		} else {
			return JsonData.buildError("注册失败,该账号已被注册");
		}
	}
	
	@RequestMapping(value="/toLogin",method=RequestMethod.GET)
	public String toLogin(ModelMap map,HttpServletRequest request) {
		map.addAttribute("msg", request.getParameter("msg"));
		//System.out.println(request.getParameter("msg"));
		return "admin/login";
	}
	
	
}
