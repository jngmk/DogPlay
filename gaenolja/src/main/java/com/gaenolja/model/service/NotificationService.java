package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Notification;

public interface NotificationService {
	public List<Notification> searchall();
	public List<Notification> searchbyhotelnumber(String hotelnumber);
	public List<Notification> searchbyuserid(String userid);
	public List<Notification> searchbytarget(String target);
	public boolean insert(Notification notification);
	public boolean update(Notification notification);
	public boolean delete(int id);
}
