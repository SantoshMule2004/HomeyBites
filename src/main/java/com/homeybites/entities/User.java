package com.homeybites.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.homeybites.entities.Log.SubscriptionLog;
import com.homeybites.entities.Log.TiffinPlanLog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String firstName;
	private String middleName;
	private String lastName;
	
	@Column(nullable = false)
	private String emailId;
	
	private boolean isVerified = false;
	private String phoneNo;
	private String dob;
	private String password;
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
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> address = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<OrderInfo> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Subscription> subscriptions = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SubscriptionLog> subscriptionLog = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<TiffinPlan> tiffinPlans = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<TiffinPlanLog> tiffinPlanLog = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Payment> payments = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<MenuItem> menuItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserCart> userCart;
	
	
	public User() {
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
	
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public List<OrderInfo> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderInfo> orders) {
		this.orders = orders;
	}
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	public List<TiffinPlan> getTiffinPlans() {
		return tiffinPlans;
	}
	public void setTiffinPlans(List<TiffinPlan> tiffinPlans) {
		this.tiffinPlans = tiffinPlans;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
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
	public List<UserCart> getUserCart() {
		return userCart;
	}
	public void setUserCart(List<UserCart> userCart) {
		this.userCart = userCart;
	}
	public List<TiffinPlanLog> getTiffinPlanLog() {
		return tiffinPlanLog;
	}
	public void setTiffinPlanLog(List<TiffinPlanLog> tiffinPlanLog) {
		this.tiffinPlanLog = tiffinPlanLog;
	}
	public List<SubscriptionLog> getSubscriptionLog() {
		return subscriptionLog;
	}
	public void setSubscriptionLog(List<SubscriptionLog> subscriptionLog) {
		this.subscriptionLog = subscriptionLog;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
		return List.of(authority);
	}

	@Override
	public String getUsername() {
		return emailId;
	}
}
