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
	public Response search(int id){
		try {
			Response response = dao.search(id);
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Response searchbyreview(int reviewid) {
		try {
			Response response = dao.searchbyreview(reviewid);
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Response response) {
		try {
			dao.insert(response);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean update(Response response) {
		try {
			dao.update(response);
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
