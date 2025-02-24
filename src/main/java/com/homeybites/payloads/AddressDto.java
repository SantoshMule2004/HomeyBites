package com.homeybites.payloads;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressDto {
	
	private Integer addId;
	private String addressLine;
	private String landmark;
	private String city;
	private String state;
	private String country;
	private double latitude;
	private double longitude;
	private String serviceRadius;
	
	@JsonIgnore
	private UserDto user;
	
	private UserRoles userRoles;
	
	public AddressDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getAddId() {
		return addId;
	}
	public void setAddId(Integer addId) {
		this.addId = addId;
	}
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public UserRoles getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(UserRoles userRoles) {
		this.userRoles = userRoles;
	}
}
