package com.services.processing.api.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private long jobNumber;

    private String jobStatus;

    private String jobCreationTime;

    private String jobCompleteTime;

    private String requestNumber;

    private long requestorUserId;

    private long bidId;

    private long partnerUserId;

    public long getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(long jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobCreationTime() {
        return jobCreationTime;
    }

    public void setJobCreationTime(String jobCreationTime) {
        this.jobCreationTime = jobCreationTime;
    }

    public String getJobCompleteTime() {
        return jobCompleteTime;
    }

    public void setJobCompleteTime(String jobCompleteTime) {
        this.jobCompleteTime = jobCompleteTime;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public long getRequestorUserId() {
        return requestorUserId;
    }

    public void setRequestorUserId(long requestorUserId) {
        this.requestorUserId = requestorUserId;
    }

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

    public long getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(long partnerUserId) {
        this.partnerUserId = partnerUserId;
    }
}
