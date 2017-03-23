package com.ccps.exchgate.api.t2.param;

import java.util.ArrayList;
import java.util.List;

import com.ccps.exchgate.api.pojo.BaseReq;

/**
 * 
 * @author jiangzz
 *
 */
@SuppressWarnings("serial")
public class FeesOrderParamReq extends BaseReq {
	
private List<FeesOrderParam> items = new ArrayList<FeesOrderParam>();
	
	public List<FeesOrderParam> getItems() {
		return items;
	}

	public void setItems(List<FeesOrderParam> items) {
		this.items = items;
	}
    
}