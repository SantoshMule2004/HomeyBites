package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.FeedbackDto;

public interface FeedbackService {
	
	// add feedback
	FeedbackDto addFeedback(FeedbackDto feedbackDto, Integer useId);
	
	// get feedback
	FeedbackDto getFeedback(Integer feedbackId);
	
	// get feedback of user
	FeedbackDto getFeedbackOfUser(Integer feedbackId, Integer userId);
	
	//get all feedbacks of user
	List<FeedbackDto> getAllFeedbacksOfUser(Integer userId);
	
	// get all feedback
	List<FeedbackDto> getAllFeedback();
	
	// update feedback
	FeedbackDto updateFeedback(FeedbackDto feedbackDto, Integer feedbackId);
	
	// delete feedback
	void deleteFeedback(Integer feedbackId);
}
