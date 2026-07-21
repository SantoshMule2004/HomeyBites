package com.homeybites.payloads;

import java.math.BigDecimal;

public class CreateTiffinPlanDTO {
	private String planName;
    private int validityDays;
    
    private boolean offersBreakfast;
    private BigDecimal pricePerBreakfast; // Can be null/0 if offersBreakfast is false
    
    private boolean offersLunch;
    private BigDecimal pricePerLunch;
    
    private boolean offersDinner;
    private BigDecimal pricePerDinner;
    
    private int maxCapacity;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public int getValidityDays() {
		return validityDays;
	}

	public void setValidityDays(int validityDays) {
		this.validityDays = validityDays;
	}

	public boolean isOffersBreakfast() {
		return offersBreakfast;
	}

	public void setOffersBreakfast(boolean offersBreakfast) {
		this.offersBreakfast = offersBreakfast;
	}

	public BigDecimal getPricePerBreakfast() {
		return pricePerBreakfast;
	}

	public void setPricePerBreakfast(BigDecimal pricePerBreakfast) {
		this.pricePerBreakfast = pricePerBreakfast;
	}

	public boolean isOffersLunch() {
		return offersLunch;
	}

	public void setOffersLunch(boolean offersLunch) {
		this.offersLunch = offersLunch;
	}

	public BigDecimal getPricePerLunch() {
		return pricePerLunch;
	}

	public void setPricePerLunch(BigDecimal pricePerLunch) {
		this.pricePerLunch = pricePerLunch;
	}

	public boolean isOffersDinner() {
		return offersDinner;
	}

	public void setOffersDinner(boolean offersDinner) {
		this.offersDinner = offersDinner;
	}

	public BigDecimal getPricePerDinner() {
		return pricePerDinner;
	}

	public void setPricePerDinner(BigDecimal pricePerDinner) {
		this.pricePerDinner = pricePerDinner;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
}
