package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.DoginfoDAO;
import com.gaenolja.model.dto.Doginfo;

@Service
public class DoginfoServiceImpl implements DoginfoService {
	
	@Autowired
	DoginfoDAO dao;
	
	@Override
	public List<Doginfo> searchall(){
		try {
			List<Doginfo> doginfo = dao.searchall();
			return doginfo;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Doginfo> searchbyuserid(String userid) {
		try {
			List<Doginfo> doginfo = dao.searchbyuserid(userid);
			return doginfo;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Doginfo search(int id) {
		try {
			Doginfo doginfo = dao.search(id);
			return doginfo;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public void insert(Doginfo doginfo) {
		try {
			dao.insert(doginfo);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void update(Doginfo doginfo) {
		try {
			dao.insert(doginfo);
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
