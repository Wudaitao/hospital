package com.hosptialsys.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.hosptialsys.domain.JsonData;
import com.hosptialsys.utils.JwtUtil;

import io.jsonwebtoken.Claims;

public class LoginInterceptor implements HandlerInterceptor {
	
	private final static Gson gson = new Gson();
	/**
	 * 进入controller之前进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		System.out.println("interceptor");
		String token = request.getHeader("token");
		if (token==null) {
			token = request.getParameter("token");
		}
		if (token!=null) {
			Claims claims = JwtUtil.checkJWT(token);
			if (claims!=null) {
				String userId = (String)claims.get("user_id");
				String name = (String)claims.get("user_name");
				request.setAttribute("user_id", userId);
				request.setAttribute("user_name", name);
				return true;
			}			
		}
		sendJsonData(response, JsonData.buildError("您还未登录，请先登录！"));
		return false;
	}
	
	/**
	 * 响应数据给前端
	 */
	public static void sendJsonData(HttpServletResponse response,Object object) {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(gson.toJson(object));
			writer.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
