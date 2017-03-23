/*
 * Hundsun Inc.
 * Copyright (c) 2010-2020 All Rights Reserved.
 *
 * Author     :zhengsb
 * Version    :1.0
 * Create Date:Feb 16, 2011
 */
package com.ccps.exchgate.api.t2.param;

import java.util.Map;

/**
 * 前置响应资金系统，资金系统响应前置对象
 * 
 * @author wujy10703
 */
public class BankResponse {

	/**
	 * 是否成功
	 */
	private boolean success;
	
	/**
	 * 是否超时
	 */
	private boolean timeout = false;
	
	/**
	 * 返回代码
	 */
	private String code = "0000";
	/**
	 * 返回的消息
	 */
	private String message;
	/**
	 * 返回的消息
	 */
	private byte[] messageByte;

	/**
	 * 此属性非特殊情况不允许使用，随时可能删除
	 */
	private Map<String, String> responseMap;
	
	/**
	 * 此属性非特殊情况不允许使用，随时可能删除
	 */
	private Map<String,byte[]> responseMapByte;
	/**
	 * 银行返回结果集
	 */
	private String resultObj;

	public boolean isSuccess() {
		if (success) {
			return success;
		}
		try {
			success = Integer.valueOf(this.code).intValue() == 0;
		} catch (Exception e) {
			return false;
		}
		return success;
	}

	public String getResultObj() {
		return resultObj;
	}

	public void setResultObj(String resultObj) {
		this.resultObj = resultObj;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessageByte(byte[] messageByte) {
		this.messageByte = messageByte;
	}

	public byte[] getMessageByte() {
		return messageByte;
	}

	public Map<String, String> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, String> responseMap) {
		this.responseMap = responseMap;
	}

	public Map<String, byte[]> getResponseMapByte() {
		return responseMapByte;
	}

	public void setResponseMapByte(Map<String, byte[]> responseMapByte) {
		this.responseMapByte = responseMapByte;
	}

	public boolean getTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.valueOf("bs"));
	}

}
