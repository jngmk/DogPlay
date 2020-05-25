package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.NotificationDAO;
import com.gaenolja.model.dto.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	NotificationDAO dao;
	
	@Override
	public List<Notification> searchall(){
		try {
			List<Notification> notification = dao.searchall();
			return notification;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Notification> searchbyhotelnumber(int hotelnumber) {
		try {
			List<Notification> notification = dao.searchbyhotelnumber(hotelnumber);
			return notification;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Notification> searchbyuserid(String userid) {
		try {
			List<Notification> notification = dao.searchbyuserid(userid);
			return notification;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Notification> searchbytarget(String target) {
		try {
			List<Notification> notification = dao.searchbytarget(target);
			return notification;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Notification notification) {
		try {
			dao.insert(notification);
		}catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void update(Notification notification) {
		try {
			dao.update(notification);
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
