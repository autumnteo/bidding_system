package com.services.resource.api.model;

public class ResourceForm {
    
    private long userId;
    private String equipmentName;
    private ResourceType type;
    private String brand;
    private String model;
    private double unitRate;
    private int priceDaily;
    private int priceWeekly;
    private int priceMonthly;
    private double flatRate;
    private double priceperKm;
    private double peakHourRate;
    


	public ResourceForm() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ResourceForm(long userId, String equipmentName, ResourceType type, String brand, String model,
			double unitRate, int priceDaily, int priceWeekly, int priceMonthly, double flatRate, double priceperKm,
			double peakHourRate) {
		super();
		this.userId = userId;
		this.equipmentName = equipmentName;
		this.type = type;
		this.brand = brand;
		this.model = model;
		this.unitRate = unitRate;
		this.priceDaily = priceDaily;
		this.priceWeekly = priceWeekly;
		this.priceMonthly = priceMonthly;
		this.flatRate = flatRate;
		this.priceperKm = priceperKm;
		this.peakHourRate = peakHourRate;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public String getEquipmentName() {
		return equipmentName;
	}



	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}



	public ResourceType getType() {
		return type;
	}



	public void setType(ResourceType type) {
		this.type = type;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public String getModel() {
		return model;
	}



	public void setModel(String model) {
		this.model = model;
	}



	public double getUnitRate() {
		return unitRate;
	}



	public void setUnitRate(double unitRate) {
		this.unitRate = unitRate;
	}



	public int getPriceDaily() {
		return priceDaily;
	}



	public void setPriceDaily(int priceDaily) {
		this.priceDaily = priceDaily;
	}



	public int getPriceWeekly() {
		return priceWeekly;
	}



	public void setPriceWeekly(int priceWeekly) {
		this.priceWeekly = priceWeekly;
	}



	public int getPriceMonthly() {
		return priceMonthly;
	}



	public void setPriceMonthly(int priceMonthly) {
		this.priceMonthly = priceMonthly;
	}



	public double getFlatRate() {
		return flatRate;
	}



	public void setFlatRate(double flatRate) {
		this.flatRate = flatRate;
	}



	public double getPriceperKm() {
		return priceperKm;
	}



	public void setPriceperKm(double priceperKm) {
		this.priceperKm = priceperKm;
	}



	public double getPeakHourRate() {
		return peakHourRate;
	}



	public void setPeakHourRate(double peakHourRate) {
		this.peakHourRate = peakHourRate;
	}

	

}
