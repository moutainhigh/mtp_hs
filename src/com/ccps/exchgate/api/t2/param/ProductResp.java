package com.ccps.exchgate.api.t2.param;

import java.util.List;

import com.ccps.exchgate.api.pojo.BaseResp;
import com.ccps.exchgate.api.pojo.ProductInfo;

public class ProductResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7170842940862493451L;
	private ProductInfo productInfo;
	private List<ProductInfo> list;
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public List<ProductInfo> getList() {
		return list;
	}
	public void setList(List<ProductInfo> list) {
		this.list = list;
	}
	
	
}
