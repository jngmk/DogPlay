package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.PaidDAO;
import com.gaenolja.model.dto.Paid;

@Service
public class PaidServiceImpl implements PaidService{
	
	@Autowired
	private PaidDAO dao;
	
	@Override
	public List<Paid> searchall(){
		try {
			List<Paid> paid = dao.searchall();
			return paid;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Paid search(int id) {
		try {
			Paid paid = dao.search(id);
			return paid;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Paid paid) {
		try {
			dao.insert(paid);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Paid paid) {
		try {
			dao.update(paid);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
