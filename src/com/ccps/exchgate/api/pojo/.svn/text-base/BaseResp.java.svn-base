package com.ccps.exchgate.api.pojo;

import java.io.Serializable;

/**
 * 功能说明: 响应基类<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: <br>
 * 开发时间: <br>
 */
@SuppressWarnings("serial")
public class BaseResp implements Serializable {
	//定义接口返回参数
	private int error_no = 0;
	private String error_info = "操作成功!";
	private String serial_no= "";
	private String trade_serial_no = "";
	private String cust_id = "";
	private long out_poundage=0;
	
	
	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public long getOut_poundage() {
		return out_poundage;
	}

	public void setOut_poundage(long out_poundage) {
		this.out_poundage = out_poundage;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getTrade_serial_no() {
		return trade_serial_no;
	}

	public void setTrade_serial_no(String trade_serial_no) {
		this.trade_serial_no = trade_serial_no;
	}

	

	public int getError_no() {
		return error_no;
	}

	public void setError_no(int error_no) {
		this.setError_no(error_no);
	}
	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

	/**
	 * 判断是否出错<br>
	 * true - 出错<br>
	 * false - 未出错
	 * 
	 * @return b
	 */
	public boolean isError() {
		return this.error_no != 0;
	}

	/**
	 * 判断是否成功<br>
	 * true - 成功<br>
	 * false - 未成功
	 * 
	 * @return b
	 */
	public boolean isSuccess() {
		return !this.isError();
	}

}