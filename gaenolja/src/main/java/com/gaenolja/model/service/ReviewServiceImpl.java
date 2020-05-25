package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ReviewDAO;
import com.gaenolja.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewDAO dao;
	
	@Override
	public List<Review> searchall(){
		try {
			List<Review> review = dao.searchall();
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Review> searchbyhotelnumber(int hotelnumber) {
		try {
			List<Review> review = dao.searchbyhotelnumber(hotelnumber);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Review> searchbyuserid(String userid){
		try {
			List<Review> review = dao.searchbyuserid(userid);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Review search(int id){
		try {
			Review review = dao.search(id);
			return review;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Review review) {
		try {
			dao.insert(review);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void update(Review review) {
		try {
			dao.update(review);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void delete(int id) {
		try {
			dao.delete(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
}
