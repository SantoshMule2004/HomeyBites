//package com.homeybites.repositories;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.homeybites.entities.Feedback;
//
//public interface FeedBackRepository extends JpaRepository<Feedback, Long> {
//
//	// get feedback of user
//	Feedback findByUserIdAndFId(Long userId, Long feedbackId);
//	
//	//get all feedbacks of user
//	List<Feedback> findByUserId(Long userId);
//}
