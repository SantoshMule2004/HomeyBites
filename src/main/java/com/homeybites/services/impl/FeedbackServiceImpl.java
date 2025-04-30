package com.homeybites.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Feedback;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.repositories.FeedBackRepository;
import com.homeybites.services.FeedbackService;
import com.homeybites.services.UserService;


@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedBackRepository feedBackRepository;

	@Autowired
	private UserService userService;

	@Override
	public Feedback addFeedback(Feedback feedback, Integer userId) {
		User user = this.userService.getUser(userId);
		feedback.setUser(user);
		return this.feedBackRepository.save(feedback);
	}

	@Override
	public Feedback getFeedback(Integer feedbackId) {
		return this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
	}

	@Override
	public Feedback getFeedbackOfUser(Integer feedbackId, Integer userId) {
		User user = this.userService.getUser(userId);

		this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));

		return this.feedBackRepository.findByUserAndFId(user, feedbackId);
	}

	@Override
	public List<Feedback> getAllFeedbacksOfUser(Integer userId) {
		User user = this.userService.getUser(userId);
		return this.feedBackRepository.findByUser(user);
	}

	@Override
	public List<Feedback> getAllFeedback() {
		return this.feedBackRepository.findAll();
	}

	@Override
	public Feedback updateFeedback(Feedback feedback, Integer feedbackId) {
		Feedback eFeedback = this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
		eFeedback.setEmailId(feedback.getEmailId());
		eFeedback.setDescription(feedback.getDescription());

		return this.feedBackRepository.save(eFeedback);
	}

	@Override
	public void deleteFeedback(Integer feedbackId) {
		Feedback feedback = this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
		this.feedBackRepository.delete(feedback);
	}
}
