package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Review;

public interface ReviewService {
	public List<Review> searchall();
	public List<Review> searchbyhotelnumber(int hotelnumber);
	public List<Review> searchbyuserid(String userid);
	public Review search(int id);
	public void insert(Review review);
	public void update(Review review);
	public void delete(int id);
}
