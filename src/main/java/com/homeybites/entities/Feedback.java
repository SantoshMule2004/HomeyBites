//package com.homeybites.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Feedback {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long fId;
//	private String emailId;
//	private String description;
//
//	@Column(name = "user_id")
//	private Long userId;
//
//	public Feedback() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Long getfId() {
//		return fId;
//	}
//
//	public void setfId(Long fId) {
//		this.fId = fId;
//	}
//
//	public String getEmailId() {
//		return emailId;
//	}
//
//	public void setEmailId(String emailId) {
//		this.emailId = emailId;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//}
