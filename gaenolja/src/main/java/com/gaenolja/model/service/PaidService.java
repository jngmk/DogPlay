package com.gaenolja.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gaenolja.model.dto.Kakaopay;
import com.gaenolja.model.dto.Paid;

public interface PaidService {
	public List<Paid> searchall();
	public Paid search(int id);
	public int insert(Paid paid);
	public boolean update(Paid paid);
	public boolean delete(int id);
	public Map kakaoready(Kakaopay kakao);
	public String kakaopay(String pg_token);
	public String notapproved(HttpServletRequest request);
	public String kakaofail(HttpServletRequest request);
	public boolean cancelpay(int id);
}
