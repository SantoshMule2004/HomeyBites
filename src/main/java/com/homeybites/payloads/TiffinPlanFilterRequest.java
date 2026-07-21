package com.homeybites.payloads;

public class TiffinPlanFilterRequest {
	private Boolean offersBreakfast;
    private Boolean offersLunch;
    private Boolean offersDinner;
    private Boolean isActive;
    private String search;
    
	public Boolean getOffersBreakfast() {
		return offersBreakfast;
	}
	public void setOffersBreakfast(Boolean offersBreakfast) {
		this.offersBreakfast = offersBreakfast;
	}
	public Boolean getOffersLunch() {
		return offersLunch;
	}
	public void setOffersLunch(Boolean offersLunch) {
		this.offersLunch = offersLunch;
	}
	public Boolean getOffersDinner() {
		return offersDinner;
	}
	public void setOffersDinner(Boolean offersDinner) {
		this.offersDinner = offersDinner;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
}
