package com.hosptialsys.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.hosptialsys.domain.DocResInfo;
import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.ResInfo;
import com.hosptialsys.domain.User;
import com.hosptialsys.service.DocResvInfoService;
import com.hosptialsys.service.ResInfoService;
import com.hosptialsys.service.UserService;
import com.hosptialsys.utils.CreateQrCodeUtil;
import com.hosptialsys.utils.WXPayUtil;

@RestController
@RequestMapping("/api/v1/patient/")
public class PatientController {

	private static AtomicInteger count = new AtomicInteger(0);
	private static Map<String, Queue<String>> comQueueMap = new HashMap<>();
	private static Map<String, Queue<String>> priQueueMap = new HashMap<>();
	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM.dd");
	private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm"); 
	@Autowired
	private UserService userService;
	@Autowired
	private DocResvInfoService docResvInfoService;
	@Autowired
	private ResInfoService resInfoService;
	
	/*
	 *预约或线下挂号api
	 */
	@RequestMapping(value="register",method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("user_id");
		String userName = request.getParameter("user_name");
		String userAge = request.getParameter("user_age");
		String userGender = request.getParameter("user_gender");
		String resvDepartment = request.getParameter("resv_department");
		String doctorId = request.getParameter("doctor_id");
		String resvTimeSlot = request.getParameter("resv_time_slot");
		String resvDate = request.getParameter("resv_date");
		String resvOnline = request.getParameter("resv_online");
		User user = new User(userId, userName, null, userGender, userAge);
		ResInfo resInfo = new ResInfo();
		Map<String, String> info = new HashMap<>();
		DocResInfo docResInfo = docResvInfoService.findDocInfo(doctorId, resvDepartment, resvTimeSlot, resvDate);
		if (docResInfo != null) {
			Integer num = docResInfo.getDrResvNum();
			
			if ( num < docResInfo.getDrMaxResvNum()) {
				System.out.println(num + " " + docResInfo.getDrMaxResvNum());
				count.getAndIncrement();
				docResInfo.setDrResvNum(num+1);
				resInfo.setUserId(userId);
				resInfo.setResvIsValid("1");
				resInfo.setResvDate(resvDate);
				resInfo.setResvTimeSlot(resvTimeSlot);
				resInfo.setResvNum(count.get());
				resInfo.setResvDepartment(resvDepartment);
				resInfo.setResvOnline(resvOnline);
				if ("".equals(doctorId)) {
					resInfo.setResvType("科室预约");
					info.put("resv_type", "科室预约");
				} else {
					resInfo.setResvDoctorId(doctorId);
					resInfo.setResvType("专家预约");
					info.put("resv_type", "专家预约");
					info.put("doctor_id", doctorId);
				}
				info.put("user_id", userId);
				info.put("resv_date", resvDate);
				info.put("resv_time_slot", resvTimeSlot);
				info.put("resv_num", String.valueOf(count.get()));
				info.put("resv_department", resvDepartment);
				info.put("resv_online", resvOnline);
				userService.saveUser(user);
				docResvInfoService.update(docResInfo);
				Integer resvId = resInfoService.save(resInfo);
				//System.out.println(resvId);   预约号：取消预约时需要
				//信息写进数据库
				//返回二维码，表示预约信息
				String result = WXPayUtil.mapToXml(info);
				try {
					CreateQrCodeUtil.createQrCode(response.getOutputStream(),result,250, "JPEG");
					return JsonData.buildSuccess(resvId, "预约成功");
				} catch (WriterException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return JsonData.buildError("预约失败，名额已满！");
			}
		} else {
			return JsonData.buildError("预约失败，无此条记录！");
		}
		return JsonData.buildError("预约失败！");
	}
    
	/*
     * 获取专家或者科室预约信息api
     */
	@RequestMapping(value="getResInfo",method=RequestMethod.GET)
	public Object getResInfo(HttpServletRequest request) {
		String docId = request.getParameter("doctor_id");
		String drDepartment = request.getParameter("dr_department");
		List<DocResInfo> docResInfos =docResvInfoService.findAll(docId, drDepartment);
        return JsonData.buildSuccess(docResInfos);
	}
	
	/*
	 * 取消预约api
	 */
	@RequestMapping(value="delRegist",method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object delRegist(@RequestParam(value = "resv_id",required=true)int resvId) {
		ResInfo resInfo = resInfoService.findById(resvId);
		if (resInfo != null) {
			String resvIsValid = resInfo.getResvIsValid();
			if (resvIsValid.equals("1")) {
				String resvDep = resInfo.getResvDepartment();
				String resvDocId = resInfo.getResvDoctorId();
				String resvDate = resInfo.getResvDate();
				String resvTimeSlot = resInfo.getResvTimeSlot();
				DocResInfo docResInfo = docResvInfoService.findDocInfo(resvDocId, resvDep, resvTimeSlot, resvDate);
				int resvNum = docResInfo.getDrResvNum();
				docResInfo.setDrResvNum(resvNum-1);
				resInfo.setResvIsValid("0");
				docResvInfoService.update(docResInfo);
				resInfoService.updateState(resInfo);
				return JsonData.buildSuccess("取消预约成功！");
			} else {
				return JsonData.buildError("已经取消预约，请勿多次取消！");
			}
		} 
		return JsonData.buildError("预约单号有误，或您还未预约！");
	}
	
	/*
	 * 分诊台排队取号api
	 */
	@RequestMapping(value="lineUp",method=RequestMethod.GET)
	public Object lineUp(HttpServletRequest request) {
		String doctorId = request.getParameter("doctor_id");
		String userId = request.getParameter("user_id");
		String resDep = request.getParameter("resv_department");
		//String resvNum = request.getParameter("resv_num");
		String resvType = request.getParameter("resv_type");
		String resvDate = request.getParameter("resv_date");
		String resvTimeSlot = request.getParameter("resv_time_slot");
		String resvOnline = request.getParameter("resv_online");
		String resvTimeBegin = resvTimeSlot.split("-")[0];
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String dateNow = date.format(formatter1);
		String timeNow = time.format(formatter2);
		System.out.println(resvDate + " " + resvTimeBegin);
		System.out.println(dateNow + " " + timeNow);
		
		if (dateNow.compareTo(resvDate) == 0) {
			//专家号
			if (resvType.equals("专家预约")) {
				if (resvOnline.equals("1") && timeNow.compareTo(resvTimeBegin) <= 0) {
					if (priQueueMap.get(doctorId)==null) {
						Queue<String> priQueue = new LinkedList<>();
						priQueue.add(userId);
						priQueueMap.put(doctorId, priQueue);
						return JsonData.buildSuccess(1,"线上预约专家号排队成功，专家ID:"+doctorId);
					}
					else {
						int num = priQueueMap.get(doctorId).size();
						if (comQueueMap.get(doctorId) != null) {
							num += comQueueMap.get(doctorId).size();
						}
						priQueueMap.get(doctorId).add(userId);
						return JsonData.buildSuccess(num+1, "线上预约专家号排队成功，专家ID:"+doctorId);
					}
				} else {
					if (comQueueMap.get(doctorId)==null) {
						Queue<String> comQueue = new LinkedList<>();
						comQueue.add(userId);
						comQueueMap.put(doctorId, comQueue);
						int priNum = 0;
						if (priQueueMap.get(doctorId) != null) {
							priNum = priQueueMap.get(doctorId).size();
						}
						return JsonData.buildSuccess(priNum+1,"线下预约专家号排队成功，专家ID:"+doctorId);
					}
					else {
						int priNum = 0;
						if (priQueueMap.get(doctorId) != null) {
							priNum = priQueueMap.get(doctorId).size();
						}
						int comNum = comQueueMap.get(doctorId).size();
						comQueueMap.get(doctorId).add(userId);
						return JsonData.buildSuccess(priNum+comNum+1, "线下预约专家号排队成功，专家ID:"+doctorId);
					}
				}
			}
			//科室号
			else if (resvType.equals("科室预约")) {
				if (resvOnline.equals("1")  && timeNow.compareTo(resvTimeBegin) <= 0) {
					if (priQueueMap.get(resDep)==null) {
						Queue<String> priQueue = new LinkedList<>();
						priQueue.add(userId);
						priQueueMap.put(resDep, priQueue);
						return JsonData.buildSuccess(1,"线上科室号排队成功，科室："+resDep);
					}
					else {
						int num = priQueueMap.get(resDep).size();
						if (comQueueMap.get(resDep)!=null) {
							num += comQueueMap.get(resDep).size();
						}
						priQueueMap.get(resDep).add(userId);
						return JsonData.buildSuccess(num+1, "线上科室号排队成功，科室："+resDep);
					}
				} else {
					if (comQueueMap.get(resDep)==null) {
						Queue<String> comQueue = new LinkedList<>();
						comQueue.add(userId);
						comQueueMap.put(resDep, comQueue);
						int priNum = 0;
						if (priQueueMap.get(resDep) != null) {
							priNum = priQueueMap.get(resDep).size();
						}
						return JsonData.buildSuccess(priNum+1,"线下科室号排队成功，科室："+resDep);
					}
					else {
						int priNum = 0;
						if (priQueueMap.get(resDep) != null) {
							priNum = priQueueMap.get(resDep).size();
						}
						int comNum = comQueueMap.get(resDep).size();
						comQueueMap.get(resDep).add(userId);
						return JsonData.buildSuccess(priNum+comNum+1, "线下科室号排队成功，科室："+resDep);
					}
				}
			}
		} else if (dateNow.compareTo(resvDate) < 0) {
			return JsonData.buildError("预约日期还未到");
		} else {
			return JsonData.buildError("预约已失效");
		}
		
		return JsonData.buildError("排队失败");
	}

	/*
	 * 查看排队信息api
	 */
	@RequestMapping(value="queryLineupNumber",method=RequestMethod.GET)
	public Object query(HttpServletRequest request) {
		String doctorId = request.getParameter("doctor_id");
		String userId = request.getParameter("user_id");
		String resvDep = request.getParameter("resv_department");
		//System.out.println(doctorId + " " + userId + " " + resvDep);
		AtomicInteger num = new AtomicInteger(0);
		if (!doctorId.equals("")) {
			Queue<String> priQueue = priQueueMap.get(doctorId);
			Queue<String> comQueue = comQueueMap.get(doctorId);
			if (priQueue != null) {
				for(String s : priQueue) {
					if(!s.equals(userId)) {
						num.getAndIncrement();
					} else {
						num.getAndIncrement();
						return JsonData.buildSuccess("查询成功！前面还有人数：" + num.get());
					}
				}
			}
			if (comQueue != null) {
				for (String s : comQueue) {
					if(!s.equals(userId)) {
						num.getAndIncrement();
					} else {
						num.getAndIncrement();
						return JsonData.buildSuccess("查询成功！前面还有人数：" + num.get());
					}
				}
			}
			System.out.println(userId + " " + doctorId );
		} else {
			Queue<String> priQueue = priQueueMap.get(resvDep);
			Queue<String> comQueue = comQueueMap.get(resvDep);
			if (priQueue != null) {
				for(String s : priQueue) {
					if(!s.equals(userId)) {
						num.getAndIncrement();
					} else {
						num.getAndIncrement();
						return JsonData.buildSuccess("查询成功！前面还有人数：" + num.get());
					}
				}
			}
			if (comQueue != null) {
				for (String s : comQueue) {
					if(!s.equals(userId)) {
						num.getAndIncrement();
					} else {
						num.getAndIncrement();
						return JsonData.buildSuccess("查询成功！前面还有人数：" + num.get());
					}
				}
			}
			System.out.println(priQueue);
			System.out.println(comQueue);
			System.out.println(userId + " " + resvDep);
		}
		return JsonData.buildError("查询失败！您已经不在队列里");
	}

	/*
	 * 医生拉取病人信息api
	 */
	@RequestMapping(value="getPatientInfo",method=RequestMethod.GET)
	public JsonData getPatientInfo(String Id) {
		if (!Id.equals("")) {
			Queue<String> priQueue = priQueueMap.get(Id);
			Queue<String> comQueue = comQueueMap.get(Id);
			//先取优先队列里的病人
			if (priQueue != null && !priQueue.isEmpty()) {
				String patientInfo = priQueue.poll();
				return JsonData.buildSuccess(patientInfo,"拉取成功！");
			} 
			//然后取普通队列里的病人
			else if (comQueue != null && !comQueue.isEmpty()) {
				String patientInfo = comQueue.poll();
				return JsonData.buildSuccess(patientInfo,"拉取成功！");
			}
			//两个队列里都没有病人
			else {
				//System.out.println(comQueue);
				return JsonData.buildError("已经没有病人！");
			}
		} 
		return JsonData.buildError("已经没有病人！");
	}

}
