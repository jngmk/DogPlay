package com.gaenolja.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ChatDAO;
import com.gaenolja.model.dto.Chat;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	ChatDAO dao;
	
	@Override
	public List<Chat> searchall(){
		try {
			List<Chat> chat = dao.searchall();
			return chat;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Chat> searchbyreceive(String receive) {
		try {
			List<Chat> chat = dao.searchbyreceive(receive);
			return chat;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Chat> searchbysend(String send){
		try {
			List<Chat> review = dao.searchbysend(send);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Chat> searchbytwo(String receive, String send){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("receive", receive);
			map.put("send", send);
			List<Chat> review = dao.searchbytwo(map);
			return review;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Chat search(int id){
		try {
			Chat chat = dao.search(id);
			return chat;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(Chat chat) {
		try {
			dao.insert(chat);
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
