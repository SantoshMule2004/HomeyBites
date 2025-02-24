package com.homeybites.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.homeybites.payloads.ImageInfo;

public interface ImageService {
	
	// upload image
	ImageInfo uploadImage(MultipartFile file) throws IOException;
	
	// generating image URL
	String generateUrl(String publicId);
	
	// generating transformed image URL
	String generateTransformedUrl(String publicId);
}
