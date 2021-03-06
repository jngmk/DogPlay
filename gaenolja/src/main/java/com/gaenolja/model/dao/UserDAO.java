package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.User;

@Mapper
public interface UserDAO {
	public List<User> searchall();
	public User search(String userid);
	public void insert(User user);
	public void update(User user);
	public void delete(String userid);
}
