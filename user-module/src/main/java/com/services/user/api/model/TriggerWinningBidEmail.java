package com.services.user.api.model;

public class TriggerWinningBidEmail {
	private Bid winningBid;
	private Request winingRequest;
	public TriggerWinningBidEmail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TriggerWinningBidEmail(Bid winningBid, Request winingRequest) {
		super();
		this.winningBid = winningBid;
		this.winingRequest = winingRequest;
	}
	public Bid getWinningBid() {
		return winningBid;
	}
	public void setWinningBid(Bid winningBid) {
		this.winningBid = winningBid;
	}
	public Request getWiningRequest() {
		return winingRequest;
	}
	public void setWiningRequest(Request winingRequest) {
		this.winingRequest = winingRequest;
	}
}
