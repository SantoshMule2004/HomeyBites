package com.homeybites.payloads;

public class SubscriptionFilterRequest {
	private SubscriptionStatus status;
	private String search;

	public SubscriptionStatus getStatus() {
		return status;
	}

	public void setStatus(SubscriptionStatus status) {
		this.status = status;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
