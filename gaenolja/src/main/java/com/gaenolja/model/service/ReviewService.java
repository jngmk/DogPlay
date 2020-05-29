package com.gaenolja.model.service;

import java.util.HashMap;
import java.util.List;

import com.gaenolja.model.dto.Review;

public interface ReviewService {
	public List<Review> searchall();
	public List<Review> searchbyhotelnumber(int hotelnumber);
	public List<Review> searchbyuserid(String userid);
	public Review search(int id);
	public boolean insert(Review review);
	public boolean update(Review review);
	public boolean delete(int id);
	public int countbyhotelnumber(int hotelnumber);
	public int countreview(int hotelnumber);
	public List<Review> reviewwithcontent(int hotelnumber);
	public List<Review> goodreviewbadreview(int hotelnumber);
	public List<Review> searchbystar(int hotelnumber, float star);
	public List<HashMap<Object, Object>> reviewwithresponse(int hotelnumber);
}
