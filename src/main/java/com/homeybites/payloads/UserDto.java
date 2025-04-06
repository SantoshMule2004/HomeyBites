package com.homeybites.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homeybites.entities.MenuItem;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
	
	private Integer userId;
	
	@NotBlank(message = "first name cannot be empty..!")
	private String firstName;
	
	private String middleName;
	
	@NotBlank(message = "last name cannot be empty..!")
	private String lastName;
	
	@Email(regexp = ".*?@?[^@]*\\.+.*")
	private String emailId;
	
	private boolean isVerified;
	
	@NotBlank(message = "Phone number cannot be empty..!")
	@Size(min = 10, max = 10, message = "phone number should be of 10 numbers..!")
	private String phoneNo;
	
	@NotBlank
	private String dob;
	

	@Size(min = 8, max = 16, message = "password should have minimum of 8 characters and maximum of 16 characters..!")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "password must contain atleast one lowercase letter, atleast one uppercase letter, atleast one digit and atleast one special character..!")
	private String password;
	
	private String cPassword;
	
	@NotBlank
	private String gender;
	
	private String dietryPref;
	
	private String universityName;
	
	private String course;
	
	private String companyName;
	
	private String businessName;
	
	private String foodLicenseNo;
	
	private String GSTIN;
	
	private List<String> permissions;
	
	private String userRole;
	
	private List<AddressDto> address = new ArrayList<>();
	
	@JsonIgnore
	private List<OrderInfoDto> orders = new ArrayList<>();

	@JsonIgnore
	private List<SubscriptionDto> subscriptions = new ArrayList<>();
	
	@JsonIgnore
	private List<TiffinPlanDto> tiffinPlans = new ArrayList<>();
	
	@JsonIgnore
	private List<PaymentDto> payments = new ArrayList<>();

	@JsonIgnore
	private List<FeedbackDto> feedbacks = new ArrayList<>();
	
	@JsonIgnore
	private List<MenuItem> menuItems = new ArrayList<>();
	
	@JsonIgnore
	private List<UserCartDto> userCart;
	
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCpassword() {
		return cPassword;
	}
	public void setCpassword(String cPassword) {
		this.cPassword = cPassword;
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
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public List<AddressDto> getAddress() {
		return address;
	}
	public void setAddress(List<AddressDto> address) {
		this.address = address;
	}
	public List<OrderInfoDto> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderInfoDto> orders) {
		this.orders = orders;
	}
	public List<SubscriptionDto> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List<SubscriptionDto> subscriptions) {
		this.subscriptions = subscriptions;
	}
	public List<TiffinPlanDto> getTiffinPlans() {
		return tiffinPlans;
	}
	public void setTiffinPlans(List<TiffinPlanDto> tiffinPlans) {
		this.tiffinPlans = tiffinPlans;
	}
	public List<PaymentDto> getPayments() {
		return payments;
	}
	public void setPayments(List<PaymentDto> payments) {
		this.payments = payments;
	}
	public List<FeedbackDto> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(List<FeedbackDto> feedbacks) {
		this.feedbacks = feedbacks;
	}
	public String getcPassword() {
		return cPassword;
	}
	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}
	public List<UserCartDto> getUserCart() {
		return userCart;
	}
	public void setUserCart(List<UserCartDto> userCart) {
		this.userCart = userCart;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", emailId=" + emailId + ", isVerified=" + isVerified + ", phoneNo=" + phoneNo + ", dob="
				+ dob + ", password=" + password + ", cPassword=" + cPassword + ", gender=" + gender + ", dietryPref="
				+ dietryPref + ", universityName=" + universityName + ", course=" + course + ", companyName="
				+ companyName + ", businessName=" + businessName + ", foodLicenseNo=" + foodLicenseNo + ", GSTIN="
				+ GSTIN + ", permissions=" + permissions + ", userRole=" + userRole + ", address=" + address
				+ ", orders=" + orders + ", subscriptions=" + subscriptions + ", tiffinPlans=" + tiffinPlans
				+ ", payments=" + payments + ", feedbacks=" + feedbacks + ", menuItems=" + menuItems + ", userCart="
				+ userCart + "]";
	}
}
