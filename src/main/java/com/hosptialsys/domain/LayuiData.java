package com.hosptialsys.domain;

import java.io.Serializable;

public class LayuiData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer code; // 状态码 0 表示成功，1表示处理中，-1表示失败
	private Object data;  // 数据
	private String msg;   // 描述
	private Integer count;// 数据数量
	
	public LayuiData() {
		
	}
	public LayuiData(Integer code, Object data, String msg, Integer count) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.count = count;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static LayuiData buildSuccess(Object data, Integer count) {
		return new LayuiData(0, data, null, count);
	}
	
	public static LayuiData buildSuccess(Object data, Integer count, String msg) {
		return new LayuiData(0, data, msg, count);
	}
	
	public static LayuiData buildError(String msg) {
		return new LayuiData(-1,null, msg, null);
	}
}
