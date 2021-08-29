package com.services.bid.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class JobCreateForm {

    private String requestNumber;
    private long requestorUserId;
    private long bidId;
    private long partnerUserId;

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

    public long getRequestorUserId() {
        return requestorUserId;
    }

    public void setRequestorUserId(long requestorUserId) {
        this.requestorUserId = requestorUserId;
    }

    public long getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(long partnerUserId) {
        this.partnerUserId = partnerUserId;
    }
}
