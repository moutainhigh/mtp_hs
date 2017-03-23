package com.service.impl;

import java.util.List;

import com.service.OpenAndCloseTradeService;

/**
 *  
 * ClassName: OpenAndCloseTradeSchedulerJob.java
 * date: 2017年1月5日下午4:30:25
 * @author yu.jian
 * @version
 */
public class OpenAndCloseTradeSchedulerJob {
	private List<OpenAndCloseTradeService> taskList;
	//开市
	public void executeOpen() {
		if (null != taskList && !taskList.isEmpty()) {
			for (OpenAndCloseTradeService task : taskList) {
				try {
					task.dealTrade(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//闭市
	public void executeClose() {
		if (null != taskList && !taskList.isEmpty()) {
			for (OpenAndCloseTradeService task : taskList) {
				try {
					task.dealTrade(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public List<OpenAndCloseTradeService> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<OpenAndCloseTradeService> taskList) {
		this.taskList = taskList;
	}
}
