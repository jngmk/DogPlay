package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Notification;

@Mapper
public interface NotificationDAO {
	public List<Notification> searchall();
	public Notification searchbyhotelnumber(int hotelnumber);
	public Notification searchbyuserid(String userid);
	public Notification searchbytarget(String target);
	public void insert(Notification notification);
	public void update(Notification notification);
	public void delete(int id);
}
