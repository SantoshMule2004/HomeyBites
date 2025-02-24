package com.homeybites.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Feedback;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.FeedbackDto;
import com.homeybites.payloads.UserDto;
import com.homeybites.repositories.FeedBackRepository;
import com.homeybites.services.FeedbackService;
import com.homeybites.services.UserService;
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedBackRepository feedBackRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FeedbackDto addFeedback(FeedbackDto feedbackDto, Integer userId) {
		UserDto userDto = this.userService.getUser(userId);
		User user = this.modelMapper.map(userDto, User.class);

		Feedback feedback = this.modelMapper.map(feedbackDto, Feedback.class);
		feedback.setUser(user);
		Feedback savedFeedback = this.feedBackRepository.save(feedback);

		return this.modelMapper.map(savedFeedback, FeedbackDto.class);
	}

	@Override
	public FeedbackDto getFeedback(Integer feedbackId) {
		Feedback feedback = this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
		return this.modelMapper.map(feedback, FeedbackDto.class);
	}

	@Override
	public FeedbackDto getFeedbackOfUser(Integer feedbackId, Integer userId) {
		UserDto userDto = this.userService.getUser(userId);
		User user = this.modelMapper.map(userDto, User.class);
		
		this.feedBackRepository.findById(feedbackId)
		.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
		
		Feedback feedback = this.feedBackRepository.findByUserAndFId(user, feedbackId);
		return this.modelMapper.map(feedback, FeedbackDto.class);
	}
	
	@Override
	public List<FeedbackDto> getAllFeedbacksOfUser(Integer userId) {
		UserDto userDto = this.userService.getUser(userId);
		User user = this.modelMapper.map(userDto, User.class);
		List<Feedback> list = this.feedBackRepository.findByUser(user);
		List<FeedbackDto> allFeedbacks = list.stream().map(feedback -> this.modelMapper.map(feedback, FeedbackDto.class))
				.collect(Collectors.toList());
		return allFeedbacks;
	}

	@Override
	public List<FeedbackDto> getAllFeedback() {
		List<Feedback> all = this.feedBackRepository.findAll();
		List<FeedbackDto> allFeedbacks = all.stream().map(feedback -> this.modelMapper.map(feedback, FeedbackDto.class))
				.collect(Collectors.toList());
		return allFeedbacks;
	}

	@Override
	public FeedbackDto updateFeedback(FeedbackDto feedbackDto, Integer feedbackId) {
		Feedback feedback = this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
		feedback.setEmailId(feedbackDto.getEmailId());
		feedback.setDescription(feedbackDto.getDescription());

		Feedback save = this.feedBackRepository.save(feedback);
		return this.modelMapper.map(save, FeedbackDto.class);
	}

	@Override
	public void deleteFeedback(Integer feedbackId) {
		Feedback feedback = this.feedBackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback", "Id", feedbackId));
		this.feedBackRepository.delete(feedback);
	}
}
