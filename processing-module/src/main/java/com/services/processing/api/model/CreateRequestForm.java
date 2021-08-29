package com.services.processing.api.model;

import javax.validation.constraints.NotNull;

public class CreateRequestForm {
	@NotNull
	private long userId;
	@NotNull
    private String equipmentCategory;
	@NotNull
	private double equipmentLength;
	@NotNull
	private double equipmentWidth;
	@NotNull
	private double equipmentHeight;
	@NotNull
    private long equipmentQuantity;
	@NotNull
    private String typeOfTransporting;
	@NotNull
    private double capacityOfTransport;
	@NotNull
    private boolean rentalByTrip;
	@NotNull
    private double rentalDuration;
	@NotNull
    private int startLocationPostalCode;
	@NotNull
    private int endLocationPostalCode;
	@NotNull
    private float routeDistance;
	@NotNull
	private String startDatetime;
	@NotNull
	private String endDatetime;
	@NotNull
    private String specialRequest;
    
	public CreateRequestForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateRequestForm(@NotNull long userId, @NotNull String equipmentCategory, @NotNull double equipmentLength,
			@NotNull double equipmentWidth, @NotNull double equipmentHeight, @NotNull long equipmentQuantity,
			@NotNull String typeOfTransporting, @NotNull double capacityOfTransport, @NotNull boolean rentalByTrip,
			@NotNull double rentalDuration, @NotNull int startLocationPostalCode, @NotNull int endLocationPostalCode,
			@NotNull float routeDistance, @NotNull String startDatetime, @NotNull String endDatetime,
			@NotNull String specialRequest) {
		super();
		this.userId = userId;
		this.equipmentCategory = equipmentCategory;
		this.equipmentLength = equipmentLength;
		this.equipmentWidth = equipmentWidth;
		this.equipmentHeight = equipmentHeight;
		this.equipmentQuantity = equipmentQuantity;
		this.typeOfTransporting = typeOfTransporting;
		this.capacityOfTransport = capacityOfTransport;
		this.rentalByTrip = rentalByTrip;
		this.rentalDuration = rentalDuration;
		this.startLocationPostalCode = startLocationPostalCode;
		this.endLocationPostalCode = endLocationPostalCode;
		this.routeDistance = routeDistance;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.specialRequest = specialRequest;
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

	public long getEquipmentQuantity() {
		return equipmentQuantity;
	}

	public void setEquipmentQuantity(long equipmentQuantity) {
		this.equipmentQuantity = equipmentQuantity;
	}

	public String getTypeOfTransporting() {
		return typeOfTransporting;
	}

	public void setTypeOfTransporting(String typeOfTransporting) {
		this.typeOfTransporting = typeOfTransporting;
	}

	public double getCapacityOfTransport() {
		return capacityOfTransport;
	}

	public void setCapacityOfTransport(double capacityOfTransport) {
		this.capacityOfTransport = capacityOfTransport;
	}

	public boolean isRentalByTrip() {
		return rentalByTrip;
	}

	public void setRentalByTrip(boolean rentalByTrip) {
		this.rentalByTrip = rentalByTrip;
	}

	public double getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(double rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public int getStartLocationPostalCode() {
		return startLocationPostalCode;
	}

	public void setStartLocationPostalCode(int startLocationPostalCode) {
		this.startLocationPostalCode = startLocationPostalCode;
	}

	public int getEndLocationPostalCode() {
		return endLocationPostalCode;
	}

	public void setEndLocationPostalCode(int endLocationPostalCode) {
		this.endLocationPostalCode = endLocationPostalCode;
	}

	public float getRouteDistance() {
		return routeDistance;
	}

	public void setRouteDistance(float routeDistance) {
		this.routeDistance = routeDistance;
	}

	public String getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(String startDatetime) {
		this.startDatetime = startDatetime;
	}

	public String getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

	
}
