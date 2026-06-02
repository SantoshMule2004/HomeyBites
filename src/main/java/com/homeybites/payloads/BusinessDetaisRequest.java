package com.homeybites.payloads;

public class BusinessDetaisRequest {
	private String businessName;
	private String foodLicenseNo;
	private String gstin;

	private String addressLine;
	private String area;
	private double latitude;
	private double longitude;
	private double serviceRadius;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getFoodLicenseNo() {
		return foodLicenseNo;
	}

	public void setFoodLicenseNo(String foodLicenseNo) {
		this.foodLicenseNo = foodLicenseNo;
	}

	public String getGSTIN() {
		return gstin;
	}

	public void setGSTIN(String gstin) {
		this.gstin = gstin;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getServiceRadius() {
		return serviceRadius;
	}

	public void setServiceRadius(double serviceRadius) {
		this.serviceRadius = serviceRadius;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "BusinessDetaisRequest [businessName=" + businessName + ", foodLicenseNo=" + foodLicenseNo + ", GSTIN="
				+ gstin + ", addressLine=" + addressLine + ", area=" + area + ", latitude=" + latitude + ", longitude="
				+ longitude + ", serviceRadius=" + serviceRadius + "]";
	}
}
