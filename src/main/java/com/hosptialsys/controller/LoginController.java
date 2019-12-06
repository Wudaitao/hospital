package com.hosptialsys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
	private static final String TOLL_MAN = "收费员";
	private static final String PHARMACIAT = "药剂师";
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
				modelMap.addAttribute("title","登录成功");
				modelMap.addAttribute("status","成功");
				return "admin/info";                      //专家页面
			}
			else if (workType.equals(GENERAL_DOCTOR)) {
				return "admin/general_doctor";            //普通医生页面
			}
			else if (workType.equals(REGISTER_STAFF)) {
				return "admin/register_staff";            //挂号人员页面
			}
			else if (workType.equals(TOLL_MAN)) {      
				return "admin/toll_man";                  //收费人员页面
			}
			else if (workType.equals(PHARMACIAT)) {
				return "admin/pharmaciat";                //药剂师页面
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
	
	@RequestMapping(value="/toLogin",method=RequestMethod.GET)
	public String toLogin(ModelMap map,HttpServletRequest request) {
		map.addAttribute("msg", request.getParameter("msg"));
		//System.out.println(request.getParameter("msg"));
		return "admin/login";
	}
}
