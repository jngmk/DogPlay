package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Review;

@Mapper
public interface ReviewDAO {
	public List<Review> searchall();
	public List<Review> searchbyhotelnumber(String hotelnumber);
	public List<Review> searchbyuserid(String userid);
	public Review search(int id);
	public void insert(Review review);
	public void update(Review review);
	public void delete(int id);
	public int countbyhotelnumber(String hotelnumber);
	public List<Review> goodreview(String hotelnumber);
	public List<Review> badreview(String hotelnumber);
	public List<Review> reviewbytime(String hotelnumber);
	public int countreview(String hotelnumber);
	public List<Review> reviewwithcontent(String hotelnumber);
	public List<Review> searchbystar(HashMap<Object, Object> map);
}
