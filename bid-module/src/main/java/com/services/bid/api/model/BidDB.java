package com.services.bid.api.model;

//import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "bid")
public class BidDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bidId")
    private long bidId;
    @Column(name = "userId")
    private long userId;
    @Column(name = "bidSubmissionDate")
    private String bidSubmissionDate;
    @Column(name = "bidStatus")
    private String bidStatus;
    @Column(name = "bidPrice")
    private double bidPrice;
    @Column(name = "resourceId")
    private String resourceId;
    @Column(name = "requestId")
    private long requestId;

    public BidDB() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BidDB(long userId, String bidSubmissionDate, String bidStatus, double bidPrice, String resourceId, long requestId) {
        this.userId = userId;
        this.bidSubmissionDate = bidSubmissionDate;
        this.bidStatus = bidStatus;
        this.bidPrice = bidPrice;
        this.resourceId = resourceId;
        this.requestId = requestId;
    }



	public BidDB(long bidId, long userId, String bidSubmissionDate, String bidStatus, double bidPrice, String resourceId, long requestId) {
		super();
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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

}
