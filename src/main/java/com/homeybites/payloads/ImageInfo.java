package com.homeybites.payloads;

public class ImageInfo {
	
	private String publicId;
	private String securedUrl;
	private String format;
	
	public ImageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ImageInfo(String publicId, String securedUrl, String format) {
		super();
		this.publicId = publicId;
		this.securedUrl = securedUrl;
		this.format = format;
	}
	
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getSecuredUrl() {
		return securedUrl;
	}
	public void setSecuredUrl(String securedUrl) {
		this.securedUrl = securedUrl;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
