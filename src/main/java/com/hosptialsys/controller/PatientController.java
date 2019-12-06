package com.hosptialsys.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.hosptialsys.utils.CreateQrCodeUtil;
import com.hosptialsys.utils.WXPayUtil;

@RestController
@RequestMapping("/api/patient/")
public class PatientController {

	@RequestMapping("register")
	public void sayHello(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("name"));
		String name = request.getParameter("name");
		Map<String, String> patientinfo = new HashMap<String, String>();
		patientinfo.put("name", name);
		patientinfo.put("address", "北京");
		patientinfo.put("age", "20");
		//信息写进数据库
		//返回二维码，表示预约号
		String info = WXPayUtil.mapToXml(patientinfo);
		try {
			CreateQrCodeUtil.createQrCode(response.getOutputStream(),info,400, "JPEG");
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
