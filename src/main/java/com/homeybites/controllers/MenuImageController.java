package com.homeybites.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homeybites.payloads.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class MenuImageController {
	
	@PostMapping("/User")
	public ResponseEntity<ApiResponse> addImage(@RequestParam MultipartFile file, @RequestParam String userData) {
		System.out.println("Image: " + file.getOriginalFilename());
		System.out.println("User data : "+ userData);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Done", true), HttpStatus.OK);
	}
}
