package com.gaenolja.model.service;

import java.util.HashMap;
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
	public int countbyhotelnumber(int hotelnumber) {
		try {
			int reviewnumber = dao.countbyhotelnumber(hotelnumber);
			return reviewnumber;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countreview(int hotelnumber) {
		try {
			int reviewnumber = dao.countreview(hotelnumber);
			return reviewnumber;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
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
	public List<Review> goodreview(int hotelnumber){
		try {
			List<Review> review = dao.goodreview(hotelnumber);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Review> badreview(int hotelnumber){
		try {
			List<Review> review = dao.badreview(hotelnumber);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Review> reviewwithcontent(int hotelnumber){
		try {
			List<Review> review = dao.reviewwithcontent(hotelnumber);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Review> searchbystar(int hotelnumber, float star){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("hotelnumber", hotelnumber);
			map.put("star", star);
			List<Review> review = dao.searchbystar(map);
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
	public boolean insert(Review review) {
		try {
			dao.insert(review);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean update(Review review) {
		try {
			dao.update(review);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
}
