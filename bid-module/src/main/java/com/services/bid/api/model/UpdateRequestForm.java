package com.services.bid.api.model;


public class UpdateRequestForm {
    private long requestNumber;
    private String equipmentCategory;
    private double equipmentLength;
    private double equipmentWidth;
    private double equipmentHeight;
    private long equipmentQuantity;
    private String typeOfTransporting;
    private double capacityOfTransport;
    private boolean rentalByTrip;
    private double rentalDuration;
    private int startLocationPostalCode;
    private int endLocationPostalCode;
    private float routeDistance;
    private String startDatetime;
    private String endDatetime;
    private String specialRequest;

    public UpdateRequestForm(){
        super();
    }

    public UpdateRequestForm(long requestNumber, String equipmentCategory, double equipmentLength,
                             double equipmentWidth, double equipmentHeight, long equipmentQuantity,
                             String typeOfTransporting, double capacityOfTransport, boolean rentalByTrip,
                             double rentalDuration, int startLocationPostalCode, int endLocationPostalCode,
                             float routeDistance, String startDatetime, String endDatetime, String specialRequest) {

        this.requestNumber = requestNumber;
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

    public long getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(long requestNumber) {
        this.requestNumber = requestNumber;
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
