package com.ccps.exchgate.api.pojo;

import java.io.Serializable;

/**
 * @author fandong17032 
 * 功能说明: 调用TA 商品合约状态变更请求类<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: fandong17032
 * 开发时间: <br>
 */
public class ExchTAAgreeReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8214488292969811201L;
	
	private String productInId;
	private String exchangeId;
	private String productCode;
	private String effectStatus;

	public String getProductInId() {
		return productInId;
	}

	public void setProductInId(String productInId) {
		this.productInId = productInId;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeID(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getEffectStatus() {
		return effectStatus;
	}

	public void setEffectStatus(String effectStatus) {
		this.effectStatus = effectStatus;
	}

}
