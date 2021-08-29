package com.services.bid.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="job")
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="jobNumber")
    private long jobNumber;
    @Column(name="jobStatus")
    private String jobStatus;
    @Column(name="jobCreationTime")
    private String jobCreationTime;
    @Column(name="jobCompleteTime")
    private String jobCompleteTime;
    @Column(name="requestNumber")
    private String requestNumber;
    @Column(name="requestorUserId")
    private long requestorUserId;
    @Column(name="bidId")
    private long bidId;
    @Column(name="partnerUserId")
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
