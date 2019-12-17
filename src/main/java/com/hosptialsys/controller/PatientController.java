package com.hosptialsys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.hosptialsys.service.WorkerService;
import com.hosptialsys.utils.CreateQrCodeUtil;
import com.hosptialsys.utils.WXPayUtil;

@RestController
@RequestMapping("/api/v1/patient/")
@PropertySource({"classpath:application.properties"})
public class PatientController {

	@Value("${web.images-path}")
	private String filePath;
	private static AtomicInteger count = new AtomicInteger(0);
	private static Map<String, Queue<String>> comQueueMap = new HashMap<>();
	private static Map<String, Queue<String>> priQueueMap = new HashMap<>();
	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm"); 
	@Autowired
	private UserService userService;
	@Autowired
	private DocResvInfoService docResvInfoService;
	@Autowired
	private ResInfoService resInfoService;
	@Autowired
	private WorkerService workerService;
	
	/*
	 *预约或线下挂号api
	 */
	@RequestMapping(value="register",method=RequestMethod.POST)
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
		String resvDocName = request.getParameter("resv_doctor_name");
		User user = new User(userId, userName, userGender, userAge);
		ResInfo resInfo = new ResInfo();
		Map<String, String> info = new HashMap<>();
		//限制只能预约一次，可以先取消在预约
		ResInfo resInfo2 = new ResInfo();
		if ("".equals(doctorId)) {
			resInfo2 = resInfoService.findByUserIdAndDate1(userId, resvDepartment, resvTimeSlot, resvDate);
			if (resInfo2 != null) {
				return JsonData.buildError("不能重复预约，可以先取消预约再重新预约！");
			}
		} else {
			resInfo2 = resInfoService.findByUserIdAndDate(userId, doctorId, resvDepartment, resvTimeSlot, resvDate);
			if (resInfo2 != null) {
				return JsonData.buildError("不能重复预约，可以先取消预约再重新预约！");
			}
		}
		
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
					resInfo.setResvDoctorName(resvDocName);
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
				/*if (code == -1) {
					return JsonData.buildError("复诊病人");
				}*/
				docResvInfoService.update(docResInfo);
				Integer resvId = resInfoService.save(resInfo);
				//System.out.println(resvId);   预约号：取消预约时需要
				//信息写进数据库
				//返回二维码，表示预约信息
				String result = WXPayUtil.mapToXml(info);
				try {
					System.out.println(filePath);
					CreateQrCodeUtil.createQrCode(new FileOutputStream(new File(filePath + "qrcode"+ resvId + ".jpg")),result,400, "JPEG");
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
			return JsonData.buildError("预约失败，无此条预约记录！");
		}
		return JsonData.buildError("预约失败！");
	}
    
	/*
     * 根据专家或者科室获取预约日期
     */
	@RequestMapping(value="getResInfoDate",method=RequestMethod.GET)
	public Object getResInfoDate(HttpServletRequest request) {
		String docName = request.getParameter("doctor_name");
		String drDepartment = request.getParameter("work_department");
		List<String> dateInfos = new ArrayList<String>();
		String doctorId;
		Object[] info = new Object[2];
		if (docName.equals("")) {
			doctorId = "";
			dateInfos = docResvInfoService.findDate1(drDepartment);
		} else {
			doctorId = workerService.findIdByName(docName);
			dateInfos = docResvInfoService.findDate(docName, drDepartment);
		}
		info[0]=doctorId;
		info[1]=dateInfos;
        return JsonData.buildSuccess(info);
	}
	
	/*
     * 根据专家或者科室和预约日期获取时间段
     */
	@RequestMapping(value="getResInfoTimeSlot",method=RequestMethod.GET)
	public Object getResInfoTimeSlot(HttpServletRequest request) {
		String docName = request.getParameter("doctor_name");
		String drDepartment = request.getParameter("work_department");
		String drDate = request.getParameter("dr_date");
		List<String> timeSlotInfos = new ArrayList<String>();
		if (docName.equals("")) {
			timeSlotInfos = docResvInfoService.findTimeSlot1(drDepartment, drDate);
		} else {
			timeSlotInfos =docResvInfoService.findTimeSlot(docName, drDepartment, drDate);
		} 
        return JsonData.buildSuccess(timeSlotInfos);
	}
	
	/*
	 * 根据科室获取医生
	 */
	@RequestMapping(value="getDocInfo",method=RequestMethod.POST)
	public Object getDocInfo(@RequestParam(value = "work_department",required=true)String workDepartment) {
		List<String> docInfo = workerService.findByDep(workDepartment);
		docInfo.add("只约科室");
		return JsonData.buildSuccess(docInfo);
	}
	
	/*
	 * 取消预约api
	 */
	@RequestMapping(value="delRegist",method=RequestMethod.POST)
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
				if (resvDocId == null) {
					resvDocId = "";
				}
				DocResInfo docResInfo = docResvInfoService.findDocInfo(resvDocId, resvDep, resvTimeSlot, resvDate);
				int resvNum = docResInfo.getDrResvNum();
				docResInfo.setDrResvNum(resvNum-1);
				resInfo.setResvIsValid("0");
				docResvInfoService.update(docResInfo);
				resInfoService.updateState("0",resvId);
				return JsonData.buildSuccess(1, "预约取消成功！");
			} else {
				return JsonData.buildError("已经取消预约，请勿多次取消！");
			}
		} 
		return JsonData.buildError("预约单号有误，或您还未预约！");
	}
	
	/*
	 * 分诊台排队取号api
	 */
	@RequestMapping(value="lineUp",method=RequestMethod.POST)
	public Object lineUp(@RequestParam(value = "resv_id",required=true)int resvId) {
		ResInfo resInfo = resInfoService.findById(resvId);
		if (resInfo == null) {
			return JsonData.buildError("没有该预约信息！");
		}
		if (resInfo.getResvIsValid().equals("0")) {
			return JsonData.buildError("该预约已经排号，请勿重复排号！");
		}
		String doctorId = resInfo.getResvDoctorId();
		String doctorName = resInfo.getResvDoctorName();
		String userId = resInfo.getUserId();
		String resDep = resInfo.getResvDepartment();
		//String resvNum = request.getParameter("resv_num");
		String resvType = resInfo.getResvType();
		String resvDate = resInfo.getResvDate();
		String resvTimeSlot = resInfo.getResvTimeSlot();
		String resvOnline = resInfo.getResvOnline();
		String resvTimeBegin = resvTimeSlot.split("-")[0];
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String dateNow = date.format(formatter1);
		String timeNow = time.format(formatter2);
		Map<String, Object> result = new HashMap<>();
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
						result.put("number", 1);
						result.put("department", resDep);
						result.put("doctor_name", doctorName);
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result,"线上预约专家号排队成功");
					}
					else {
						int num = priQueueMap.get(doctorId).size();
						if (comQueueMap.get(doctorId) != null) {
							num += comQueueMap.get(doctorId).size();
						}
						priQueueMap.get(doctorId).add(userId);
						result.put("number", num+1);
						result.put("department", resDep);
						result.put("doctor_name", doctorName);
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result, "线上预约专家号排队成功");
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
						result.put("number", priNum+1);
						result.put("department", resDep);
						result.put("doctor_name", doctorName);
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result,"线下预约专家号排队成功");
					}
					else {
						int priNum = 0;
						if (priQueueMap.get(doctorId) != null) {
							priNum = priQueueMap.get(doctorId).size();
						}
						int comNum = comQueueMap.get(doctorId).size();
						comQueueMap.get(doctorId).add(userId);
						result.put("number", priNum+comNum+1);
						result.put("department", resDep);
						result.put("doctor_name", doctorName);
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result, "线下预约专家号排队成功");
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
						result.put("number",1);
						result.put("department", resDep);
						result.put("doctor_name", "无");
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result,"线上科室号排队成功");
					}
					else {
						int num = priQueueMap.get(resDep).size();
						if (comQueueMap.get(resDep)!=null) {
							num += comQueueMap.get(resDep).size();
						}
						priQueueMap.get(resDep).add(userId);
						result.put("number",num+1);
						result.put("department", resDep);
						result.put("doctor_name", "无");
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result, "线上科室号排队成功");
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
						result.put("number",priNum+1);
						result.put("department", resDep);
						result.put("doctor_name", "无");
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result,"线下科室号排队成功");
					}
					else {
						int priNum = 0;
						if (priQueueMap.get(resDep) != null) {
							priNum = priQueueMap.get(resDep).size();
						}
						int comNum = comQueueMap.get(resDep).size();
						comQueueMap.get(resDep).add(userId);
						result.put("number",priNum+comNum+1);
						result.put("department", resDep);
						result.put("doctor_name", "无");
						resInfoService.updateState("0",resvId);
						return JsonData.buildSuccess(result, "线下科室号排队成功");
					}
				}
			}
		} else if (dateNow.compareTo(resvDate) < 0) {
			return JsonData.buildError("预约日期还未到");
		} else {
			return JsonData.buildError("预约日期已过");
		}
		
		return JsonData.buildError("排队失败");
	}

	/*
	 * 查看排队信息api
	 */
	@RequestMapping(value="queryLineupNumber",method=RequestMethod.POST)
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
						return JsonData.buildSuccess(num.get());
					}
				}
			}
			if (comQueue != null) {
				for (String s : comQueue) {
					if(!s.equals(userId)) {
						num.getAndIncrement();
					} else {
						num.getAndIncrement();
						return JsonData.buildSuccess(num.get());
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
						return JsonData.buildSuccess(num.get());
					}
				}
			}
			if (comQueue != null) {
				for (String s : comQueue) {
					if(!s.equals(userId)) {
						num.getAndIncrement();
					} else {
						num.getAndIncrement();
						return JsonData.buildSuccess(num.get());
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
	@RequestMapping(value="getPatientInfo",method=RequestMethod.POST)
	public JsonData getPatientInfo(String id) {
		if (!id.equals("")) {
			Queue<String> priQueue = priQueueMap.get(id);
			Queue<String> comQueue = comQueueMap.get(id);
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
				return JsonData.buildError("暂时没有病人了！");
			}
		} 
		return JsonData.buildError("已经没有病人！");
	}

}
