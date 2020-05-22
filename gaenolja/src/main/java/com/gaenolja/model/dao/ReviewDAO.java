package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Review;

@Mapper
public interface ReviewDAO {
	public List<Review> searchall();
	public Review searchbyhotelnumber(int hotelnumber);
	public Review searchbyuserid(String userid);
	public Review searchbystar(float star);
	public Review search(int id);
	public void insert(Review review);
	public void update(Review review);
	public void delete(int id);
}
