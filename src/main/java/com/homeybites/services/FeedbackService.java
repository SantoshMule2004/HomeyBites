package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.Feedback;

public interface FeedbackService {
	
	// add feedback
	Feedback addFeedback(Feedback feedback, Integer useId);
	
	// get feedback
	Feedback getFeedback(Integer feedbackId);
	
	// get feedback of user
	Feedback getFeedbackOfUser(Integer feedbackId, Integer userId);
	
	//get all feedbacks of user
	List<Feedback> getAllFeedbacksOfUser(Integer userId);
	
	// get all feedback
	List<Feedback> getAllFeedback();
	
	// update feedback
	Feedback updateFeedback(Feedback feedback, Integer feedbackId);
	
	// delete feedback
	void deleteFeedback(Integer feedbackId);
}
