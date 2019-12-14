package com.hosptialsys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hosptialsys.domain.Payment;

public interface PaymentMapper {
	
	@Select("select * from payment where payment_id = #{paymentId}")
	Payment findById(@Param("paymentId")Integer paymentId);
	
	@Select("select * from payment where user_id = #{userId} and worker_id = #{workerId} and payment_date = #{paymentDate}")
	Payment findByJointKey(@Param("userId")String userId, @Param("workerId")String workerId, @Param("paymentDate")String paymentDate);
	
	@Insert("insert into `payment` (`worker_id`, `user_id`, `payment_date`, `payment_amount`) values"
			+ "(#{workerId}, #{userId}, #{paymentDate}, #{paymentAmount})")
	@Options(useGeneratedKeys = true, keyProperty = "paymentId",keyColumn = "payment_id")
	int save(Payment payment);
	
	@Update("update payment set payment_amount = payment_amount + #{addAmount} where user_id = #{userId} and payment_date = #{paymentDate}")
	int updatePayment(@Param("userId")String userId, @Param("paymentDate")String paymentDate,@Param("addAmount")Float addAmount);
}
