package com.igate.iconnect.BO;

import java.io.Serializable;

public class Reports implements Serializable{

	private static final long serialVersionUID = 1L;
	private String function;
	private String category;
	private String status;
	private String startDate;
	private String endDate;
	private String onBasisOf;
	
	public String getOnBasisOf() {
		return onBasisOf;
	}
	public void setOnBasisOf(String onBasisOf) {
		this.onBasisOf = onBasisOf;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
