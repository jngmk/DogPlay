package com.gaenolja.model.service;

import java.util.Map;

import com.gaenolja.model.dto.User;

public interface UserService {
	public User search(String userid);
	public void insert(User user);
	public void update(User user);
	public void delete(String userid);
	public Map<String, String> googleLogin(String idToken);
	public Map<String, String> naverLogin(String token);
}
