package com.gaenolja.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ChatroomDAO;
import com.gaenolja.model.dto.Chatroom;

@Service
public class ChatroomServiceImpl implements ChatroomService {
	
	@Autowired
	private ChatroomDAO dao;
	
	@Override
	public Chatroom search(int id) {
		try {
			Chatroom chat = dao.search(id);
			return chat;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Chatroom chatroom) {
		try {
			dao.insert(chatroom);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
