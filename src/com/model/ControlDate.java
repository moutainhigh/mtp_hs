package com.model;

public class ControlDate {

    private String controlDate;

    private Integer isOpen;

	public String getControlDate() {
		return controlDate;
	}

	public void setControlDate(String controlDate) {
		this.controlDate = controlDate==null?null:controlDate.trim();
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}


    
}