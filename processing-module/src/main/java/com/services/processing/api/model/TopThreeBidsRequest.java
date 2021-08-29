package com.services.processing.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TopThreeBidsRequest {
	private Long requestNumberLong;
	private List<Long> topThreeBidsList;
	
	public TopThreeBidsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TopThreeBidsRequest(Long requestNumberLong, List<Long> topThreeBidsList) {
		super();
		this.requestNumberLong = requestNumberLong;
		this.topThreeBidsList = topThreeBidsList;
	}
	public Long getRequestNumberLong() {
		return requestNumberLong;
	}
	public void setRequestNumberLong(Long requestNumberLong) {
		this.requestNumberLong = requestNumberLong;
	}
	public List<Long> getTopThreeBidsList() {
		return topThreeBidsList;
	}
	public void setTopThreeBidsList(List<Long> topThreeBidsList) {
		this.topThreeBidsList = topThreeBidsList;
	}
	
	
}
