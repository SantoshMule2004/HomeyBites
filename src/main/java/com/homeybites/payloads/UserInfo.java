package com.homeybites.payloads;

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
	private String GSTIN;
	
	private double latitude;
	private double longitude;
	private String serviceRadius;

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(Long userId, String firstName, String middleName, String lastName, String emailId,
			boolean isVerified, String phoneNo, String dob, String gender, String dietryPref, String userRole) {
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
	}
	
	public UserInfo(Long userId, String firstName, String middleName, String lastName, String emailId,
			boolean isVerified, String phoneNo, String dob, String gender, String userRole) {
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
		this.userRole = userRole;
	}

	public UserInfo(Long userId, String firstName, String middleName, String lastName, String emailId,
			boolean isVerified, String phoneNo, String dob, String gender, String userRole, String businessName,
			String foodLicenseNo, String gSTIN, double latitude, double longitude, String serviceRadius) {
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
		this.userRole = userRole;
		this.businessName = businessName;
		this.foodLicenseNo = foodLicenseNo;
		GSTIN = gSTIN;
		this.latitude = latitude;
		this.longitude = longitude;
		this.serviceRadius = serviceRadius;
	}

	public UserInfo(Long userId, String firstName, String middleName, String lastName, String emailId,
			boolean isVerified, String phoneNo, String dob, String gender, String dietryPref, String userRole,
			String businessName, String foodLicenseNo, String gSTIN, double latitude, double longitude,
			String serviceRadius) {
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
		GSTIN = gSTIN;
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
		return GSTIN;
	}

	public void setGSTIN(String gSTIN) {
		GSTIN = gSTIN;
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

	public String getServiceRadius() {
		return serviceRadius;
	}

	public void setServiceRadius(String serviceRadius) {
		this.serviceRadius = serviceRadius;
	}
}
