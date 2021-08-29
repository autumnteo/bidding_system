package com.services.bid.api.model;

public class ResourceUnit {


	private long resourceId;
	private long userId;
    private String equipmentName;
    private String type;
    private String brand;
    private String model;
    private String manufactureYear;
    private String category;
    private String subCategory;
    private double weight;
    private double height;
    private double breadth;
    private int length;
    private double payload;
    private double centrifugalForce;
    private int workingCapacity;
    private int priceDaily;
    private int priceWeekly;
    private int priceMonthly;
    private double flatRate;
    private double priceperKm;
    private double peakHourRate;

	public ResourceUnit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourceUnit(long resourceId, long userId, String equipmentName, String type, String brand, String model,
			String manufactureYear, String category, String subCategory, double weight, double height, double breadth,
			int length, double payload, double centrifugalForce, int workingCapacity, int priceDaily, int priceWeekly,
			int priceMonthly, double flatRate, double priceperKm, double peakHourRate) {
		super();
		this.resourceId = resourceId;
		this.userId = userId;
		this.equipmentName = equipmentName;
		this.type = type;
		this.brand = brand;
		this.model = model;
		this.manufactureYear = manufactureYear;
		this.category = category;
		this.subCategory = subCategory;
		this.weight = weight;
		this.height = height;
		this.breadth = breadth;
		this.length = length;
		this.payload = payload;
		this.centrifugalForce = centrifugalForce;
		this.workingCapacity = workingCapacity;
		this.priceDaily = priceDaily;
		this.priceWeekly = priceWeekly;
		this.priceMonthly = priceMonthly;
		this.flatRate = flatRate;
		this.priceperKm = priceperKm;
		this.peakHourRate = peakHourRate;
	}



	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
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

//	public ResourceTimetable getResourceTimetable() {
//		return ResourceTimetable;
//	}
//
//	public void setResourceTimetable(ResourceTimetable resourceTimetable) {
//		ResourceTimetable = resourceTimetable;
//	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public String getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getBreadth() {
		return breadth;
	}

	public void setBreadth(double breadth) {
		this.breadth = breadth;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getPayload() {
		return payload;
	}

	public void setPayload(double payload) {
		this.payload = payload;
	}

	public double getCentrifugalForce() {
		return centrifugalForce;
	}

	public void setCentrifugalForce(double centrifugalForce) {
		this.centrifugalForce = centrifugalForce;
	}

	public int getWorkingCapacity() {
		return workingCapacity;
	}

	public void setWorkingCapacity(int workingCapacity) {
		this.workingCapacity = workingCapacity;
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
