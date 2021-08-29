package com.services.user.api.model;

import java.util.ArrayList;


public class Bid {


    private long bidId;
    private long userId;
    private String bidSubmissionDate;
    private String bidStatus;
    private double bidPrice;
    private ArrayList<String> resourceId;
    private long requestId;

    public Bid() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Bid(long bidId, long userId, String bidSubmissionDate, String bidStatus, double bidPrice, ArrayList<String> resourceId, long requestId) {
        this.bidId = bidId;
        this.userId = userId;
        this.bidSubmissionDate = bidSubmissionDate;
        this.bidStatus = bidStatus;
        this.bidPrice = bidPrice;
        this.resourceId = resourceId;
        this.requestId = requestId;
    }



    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBidSubmissionDate() {
        return bidSubmissionDate;
    }

    public void setBidSubmissionDate(String bidSubmissionDate) {
        this.bidSubmissionDate = bidSubmissionDate;
    }

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public ArrayList<String> getResourceId() {
        return resourceId;
    }

    public void setResourceId(ArrayList<String> resourceId) {
        this.resourceId = resourceId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }


}
