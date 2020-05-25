package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ResponseDAO;
import com.gaenolja.model.dto.Response;

@Service
public class ResponseServiceImpl implements ResponseService {
	
	@Autowired
	ResponseDAO dao;
	
	@Override
	public List<Response> searchall(){
		try {
			List<Response> response = dao.searchall();
			return response;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Response> searchbyuserid(String userid) {
		try {
			List<Response> response = dao.searchbyuserid(userid);
			return response;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Response search(int reviewid){
		try {
			Response response = dao.search(reviewid);
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Response response) {
		try {
			dao.insert(response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void update(Response response) {
		try {
			dao.update(response);
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
