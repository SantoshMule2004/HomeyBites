package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Feedback;
import com.homeybites.entities.User;

public interface FeedBackRepository extends JpaRepository<Feedback, Integer> {

	// get feedback of user
	Feedback findByUserAndFId(User user, Integer feedbackId);
	
	//get all feedbacks of user
	List<Feedback> findByUser(User user);
}
