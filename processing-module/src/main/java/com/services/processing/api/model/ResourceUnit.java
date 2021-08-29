package com.services.processing.api.model;

public class ResourceUnit {

	private long resourceId;
	private long userId;
    private String equipmentName;
    private ResourceType type;
    private String brand;
    private String model;
    private String manufactureYear;
    private ResourceCategory category;
    private double weight;
    private double height;
    private double width;
    private double length;
    private double payload;
    private double centrifugalForce;
    private int workingCapacity;
    private int priceDaily;
    private int priceWeekly;
    private int priceMonthly;
    private double pricePerTrip;
    private double priceperKm;
    private double extraPricePerHour;
    private double fullDayPrice;
    private double weekendRatePerHour;
    private double extraCost;

	public ResourceUnit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourceUnit(long resourceId, long userId, String equipmentName, ResourceType type, String brand,
			String model, String manufactureYear, ResourceCategory category, double weight, double height, double width,
			double length, double payload, double centrifugalForce, int workingCapacity, int priceDaily,
			int priceWeekly, int priceMonthly, double pricePerTrip, double priceperKm, double extraPricePerHour,
			double fullDayPrice, double weekendRatePerHour, double extraCost) {
		super();
		this.resourceId = resourceId;
		this.userId = userId;
		this.equipmentName = equipmentName;
		this.type = type;
		this.brand = brand;
		this.model = model;
		this.manufactureYear = manufactureYear;
		this.category = category;
		this.weight = weight;
		this.height = height;
		this.width = width;
		this.length = length;
		this.payload = payload;
		this.centrifugalForce = centrifugalForce;
		this.workingCapacity = workingCapacity;
		this.priceDaily = priceDaily;
		this.priceWeekly = priceWeekly;
		this.priceMonthly = priceMonthly;
		this.pricePerTrip = pricePerTrip;
		this.priceperKm = priceperKm;
		this.extraPricePerHour = extraPricePerHour;
		this.fullDayPrice = fullDayPrice;
		this.weekendRatePerHour = weekendRatePerHour;
		this.extraCost = extraCost;
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

	public String getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public ResourceCategory getCategory() {
		return category;
	}

	public void setCategory(ResourceCategory category) {
		this.category = category;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
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

	public double getPricePerTrip() {
		return pricePerTrip;
	}

	public void setPricePerTrip(double pricePerTrip) {
		this.pricePerTrip = pricePerTrip;
	}

	public double getPriceperKm() {
		return priceperKm;
	}

	public void setPriceperKm(double priceperKm) {
		this.priceperKm = priceperKm;
	}

	public double getExtraPricePerHour() {
		return extraPricePerHour;
	}

	public void setExtraPricePerHour(double extraPricePerHour) {
		this.extraPricePerHour = extraPricePerHour;
	}

	public double getFullDayPrice() {
		return fullDayPrice;
	}

	public void setFullDayPrice(double fullDayPrice) {
		this.fullDayPrice = fullDayPrice;
	}

	public double getWeekendRatePerHour() {
		return weekendRatePerHour;
	}

	public void setWeekendRatePerHour(double weekendRatePerHour) {
		this.weekendRatePerHour = weekendRatePerHour;
	}

	public double getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(double extraCost) {
		this.extraCost = extraCost;
	}

	

}