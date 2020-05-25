package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Notification;

public interface NotificationService {
	public List<Notification> searchall();
	public List<Notification> searchbyhotelnumber(int hotelnumber);
	public List<Notification> searchbyuserid(String userid);
	public List<Notification> searchbytarget(String target);
	public void insert(Notification notification);
	public void update(Notification notification);
	public void delete(int id);
}
