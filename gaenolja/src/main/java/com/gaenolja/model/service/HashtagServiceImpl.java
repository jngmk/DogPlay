package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.HashtagDAO;
import com.gaenolja.model.dto.Hashtag;

@Service
public class HashtagServiceImpl implements HashtagService{
	
	@Autowired
	private HashtagDAO dao;
	
	@Override
	public List<Hashtag> searchall(){
		try {
			List<Hashtag> hashtag = dao.searchall();
			return hashtag;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Hashtag search(String id) {
		try {
			Hashtag hashtag = dao.search(id);
			return hashtag;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Hashtag hashtag) {
		try {
			dao.insert(hashtag);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Hashtag hashtag) {
		try {
			dao.update(hashtag);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(String id) {
		try {
			dao.delete(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
