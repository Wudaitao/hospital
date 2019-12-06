package com.hosptialsys.utils;

import java.util.Date;

import com.hosptialsys.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @fuction ：jwt工具类
 * @author  ：NONWORD
 * @version ：2019年8月15日 上午9:30:11
 */
public class JwtUtil {

	public static final String SUBJECT = "NONWORD";
	public static final long EXPIRE = 1000*60*60*24*7; //将过期时间设置为一周
	public static final String APPSECRET = "NONWORD12345";//密钥
	
	/**
	 * 生成JWT token的方法
	 * @param user
	 * @return String(token)
	 */
	public static String geneJsonWebToken(User user) {
		if(user==null||user.getUserId()==null||user.getUserName()==null||user.getUserPassword()==null) {
			return null;
		}
		String Token = Jwts.builder().setSubject(SUBJECT)
					  .claim("user_id", user.getUserId())
					  .claim("user_name", user.getUserName())
					  .claim("user_password", user.getUserPassword())
					  .setIssuedAt(new Date())
					  .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
					  .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
		return Token;
	}
	
	/**
	 * 校验token方法
	 * @param token
	 * @return Claims
	 */
	public static Claims checkJWT(String token) {
		try {
			final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
											   .parseClaimsJws(token).getBody();
			return claims;
		} catch (Exception e) {
		}
		return null;
	}
}
