package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.LikesDAO;
import com.gaenolja.model.dto.Likes;

@Service
public class LikesServiceImpl implements LikesService {
	
	@Autowired
	LikesDAO dao;
	
	@Override
	public List<Likes> searchall(){
		try {
			List<Likes> likes = dao.searchall();
			return likes;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Likes> searchbyhotelnumber(int hotelnumber) {
		try {
			List<Likes> likes = dao.searchbyhotelnumber(hotelnumber);
			return likes;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Likes> searchbyuserid(String userid) {
		try {
			List<Likes> likes = dao.searchbyuserid(userid);
			return likes;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public boolean insert(Likes likes) {
		try {
			dao.insert(likes);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean delete(Likes likes) {
		try {
			dao.delete(likes);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
}
