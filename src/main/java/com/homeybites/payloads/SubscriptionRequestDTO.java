package com.homeybites.payloads;

import java.time.LocalDate;

public class SubscriptionRequestDTO {
	private boolean wantBreakfast;
    private boolean wantLunch;
    private boolean wantDinner;
    private LocalDate startDate;
    private Long deliveryAddressId; 
    
	public boolean isWantBreakfast() {
		return wantBreakfast;
	}
	public void setWantBreakfast(boolean wantBreakfast) {
		this.wantBreakfast = wantBreakfast;
	}
	public boolean isWantLunch() {
		return wantLunch;
	}
	public void setWantLunch(boolean wantLunch) {
		this.wantLunch = wantLunch;
	}
	public boolean isWantDinner() {
		return wantDinner;
	}
	public void setWantDinner(boolean wantDinner) {
		this.wantDinner = wantDinner;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public Long getDeliveryAddressId() {
		return deliveryAddressId;
	}
	public void setDeliveryAddressId(Long deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}
}
