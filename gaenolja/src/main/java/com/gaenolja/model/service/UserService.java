package com.gaenolja.model.service;

import java.util.List;
import java.util.Map;

import com.gaenolja.model.dto.User;

public interface UserService {
	public List<User> searchall();
	public User search(String userid);
	public boolean insert(User user);
	public boolean update(User user);
	public boolean delete(String userid);
	public Map<String, String> googleLogin(String idToken);
	public Map<String, String> naverLogin(String token);
	public Map<String, String> kakaoLogin(String token);
}
