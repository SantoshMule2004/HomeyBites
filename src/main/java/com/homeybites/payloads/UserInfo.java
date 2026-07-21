package com.homeybites.payloads;

import com.homeybites.entities.User;

public class UserInfo {
	private Long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private boolean isVerified = false;
	private String phoneNo;
	private String dob;
	private String gender;
	private String dietryPref;
	private String userRole;
	private String businessName;
	private String foodLicenseNo;
	private String gstin;
	
	private double latitude;
	private double longitude;
	private double serviceRadius;

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserInfo(User user) {
		super();
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.middleName = user.getMiddleName();
		this.lastName = user.getLastName();
		this.emailId = user.getEmailId();
		this.isVerified = user.isVerified();
		this.phoneNo = user.getPhoneNo();
		this.dob = user.getDob();
		this.gender = user.getGender();
		this.dietryPref = user.getDietryPref();
		this.userRole = user.getUserRole();
		this.businessName = user.getBusinessName();
		this.foodLicenseNo = user.getFoodLicenseNo();
		this.gstin = user.getGSTIN();
		this.latitude = user.getLatitude();
		this.longitude = user.getLongitude();
		this.serviceRadius = user.getServiceRadius();
	}

	public UserInfo(Long userId, String firstName, String middleName, String lastName, String emailId,
			boolean isVerified, String phoneNo, String dob, String gender, String dietryPref, String userRole,
			String businessName, String foodLicenseNo, String gSTIN, double latitude, double longitude,
			double serviceRadius) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.isVerified = isVerified;
		this.phoneNo = phoneNo;
		this.dob = dob;
		this.gender = gender;
		this.dietryPref = dietryPref;
		this.userRole = userRole;
		this.businessName = businessName;
		this.foodLicenseNo = foodLicenseNo;
		this.gstin = gSTIN;
		this.latitude = latitude;
		this.longitude = longitude;
		this.serviceRadius = serviceRadius;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDietryPref() {
		return dietryPref;
	}

	public void setDietryPref(String dietryPref) {
		this.dietryPref = dietryPref;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

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

	public void setGSTIN(String gSTIN) {
		this.gstin = gSTIN;
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
}
