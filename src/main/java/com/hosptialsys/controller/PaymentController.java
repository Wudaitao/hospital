package com.hosptialsys.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hosptialsys.domain.JsonData;
import com.hosptialsys.domain.Payment;
import com.hosptialsys.service.ExamItemService;
import com.hosptialsys.service.MedicineListService;
import com.hosptialsys.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payment/")
public class PaymentController {
	
	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private ExamItemService examItemService;
	@Autowired
	private MedicineListService medicineListService;
	
	@RequestMapping(value="payOrReturn",method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRED)
	public Object getDoctorInfo(HttpServletRequest request) {
		String op = request.getParameter("op");
		String userId = request.getParameter("user_id");
		String workerId = request.getParameter("worker_id");
		LocalDate date = LocalDate.now();
		String dateNow = date.format(formatter1);
		Payment payment = new Payment();
		payment.setPaymentDate(dateNow);
		payment.setUserId(userId);
		payment.setWorkerId(workerId);
		//退款
		if (op.equals("0")) {
			Float sum = new Float(0);
			Float total = examItemService.checkSum(userId, dateNow);
			examItemService.updateCheckIsPaid(userId, dateNow, "0");
			if (total != null) {
				sum += total;
			}
			total = medicineListService.returnTatal(userId, dateNow);
			medicineListService.updateIsPaid(userId, dateNow, "0");
			if (total != null) {
				sum += total;
			}
			paymentService.updatePayment(userId, dateNow, -sum);
			return JsonData.buildSuccess(sum, "退款成功，退款：");
		}
		//收费
		else if (op.equals("1")) {
			Float sum = new Float(0);
			Float total = examItemService.getTotal(userId, dateNow);
			examItemService.updateCheckIsPaid(userId, dateNow, "1");
			if (total != null) {
				sum += total;
			}
			total = medicineListService.getTatal(userId, dateNow);
			medicineListService.updateIsPaid(userId, dateNow, "1");
			if (total != null) {
				sum += total;
			}
			payment.setPaymentAmount(sum);
			paymentService.save(payment);
			MedicineController.prescribeQueue.add(userId);
			return JsonData.buildSuccess(sum, "收费成功，收费：");
		}
		return JsonData.buildError("操作失败！");
	}
}
