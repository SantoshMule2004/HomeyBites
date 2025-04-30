package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.Feedback;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.services.FeedbackService;

@RestController
@RequestMapping("/api/v1/")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	// add feedback
	@PostMapping("/user/{userId}/feedback")
	public ResponseEntity<ApiResponse> addFeedback(@RequestBody Feedback feedback, @PathVariable Integer userId) {
		Feedback savedFeedback = this.feedbackService.addFeedback(feedback, userId);
		ApiResponse response = new ApiResponse("Feedback added successfully..!", true, savedFeedback);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update feedback
	@PutMapping("/feedback/{feedbackId}")
	public ResponseEntity<ApiResponse> updateFeedback(@RequestBody Feedback feedback,
			@PathVariable Integer feedbackId) {
		Feedback updatedFeedback = this.feedbackService.updateFeedback(feedback, feedbackId);
		ApiResponse response = new ApiResponse("Feedback updated successfully..!", true, updatedFeedback);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// get feedback
	@GetMapping("feedback/{feedbackId}")
	public ResponseEntity<Feedback> getFeedback(@PathVariable Integer feedbackId) {
		Feedback feedbacksOfUser = this.feedbackService.getFeedback(feedbackId);
		return new ResponseEntity<Feedback>(feedbacksOfUser, HttpStatus.OK);
	}

	// get feedback of user
	@GetMapping("/user/{userId}/feedback/{feedbackId}")
	public ResponseEntity<Feedback> getFeedbackOfUser(@PathVariable Integer userId,
			@PathVariable Integer feedbackId) {
		Feedback feedbacksOfUser = this.feedbackService.getFeedbackOfUser(feedbackId, userId);
		return new ResponseEntity<Feedback>(feedbacksOfUser, HttpStatus.OK);
	}

	// get feedbacks of user
	@GetMapping("/user/{userId}/feedback")
	public ResponseEntity<List<Feedback>> getFeedbacksOfUser(@PathVariable Integer userId) {
		List<Feedback> allFeedbacksOfUser = this.feedbackService.getAllFeedbacksOfUser(userId);
		return new ResponseEntity<List<Feedback>>(allFeedbacksOfUser, HttpStatus.OK);
	}

	// get all feedbacks
	@GetMapping("/feedbacks")
	public ResponseEntity<List<Feedback>> getFeedbacks() {
		List<Feedback> allFeedbacksOfUser = this.feedbackService.getAllFeedback();
		return new ResponseEntity<List<Feedback>>(allFeedbacksOfUser, HttpStatus.OK);
	}

	// delete feedback of user
	@DeleteMapping("/feedback/{feedbackId}")
	public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable Integer feedbackId) {
		this.feedbackService.deleteFeedback(feedbackId);
		ApiResponse response = new ApiResponse("feedback deleted successfully..!");
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
