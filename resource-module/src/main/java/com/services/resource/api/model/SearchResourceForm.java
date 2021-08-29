package com.services.resource.api.model;

public class SearchResourceForm {
	private long userId;
	private String equipmentCategory;
    private double equipmentLength;
    private double equipmentWidth;
    private double equipmentHeight;
    private String reqStartTime;
    private String reqEndTime;
    
	public SearchResourceForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchResourceForm(long userId, String equipmentCategory, double equipmentLength, double equipmentWidth,
			double equipmentHeight, String reqStartTime, String reqEndTime) {
		super();
		this.userId = userId;
		this.equipmentCategory = equipmentCategory;
		this.equipmentLength = equipmentLength;
		this.equipmentWidth = equipmentWidth;
		this.equipmentHeight = equipmentHeight;
		this.reqStartTime = reqStartTime;
		this.reqEndTime = reqEndTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEquipmentCategory() {
		return equipmentCategory;
	}

	public void setEquipmentCategory(String equipmentCategory) {
		this.equipmentCategory = equipmentCategory;
	}

	public double getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(double equipmentLength) {
		this.equipmentLength = equipmentLength;
	}

	public double getEquipmentWidth() {
		return equipmentWidth;
	}

	public void setEquipmentWidth(double equipmentWidth) {
		this.equipmentWidth = equipmentWidth;
	}

	public double getEquipmentHeight() {
		return equipmentHeight;
	}

	public void setEquipmentHeight(double equipmentHeight) {
		this.equipmentHeight = equipmentHeight;
	}

	public String getReqStartTime() {
		return reqStartTime;
	}

	public void setReqStartTime(String reqStartTime) {
		this.reqStartTime = reqStartTime;
	}

	public String getReqEndTime() {
		return reqEndTime;
	}

	public void setReqEndTime(String reqEndTime) {
		this.reqEndTime = reqEndTime;
	}   

}
