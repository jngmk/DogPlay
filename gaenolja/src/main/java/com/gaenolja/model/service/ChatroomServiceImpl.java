package com.gaenolja.model.service;

import java.util.List;

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
	public List<Integer> searchall(){
		try {
			List<Integer> chatroom = dao.searchall();
			return chatroom;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int insert(Chatroom chatroom) {
		try {
			dao.insert(chatroom);
			int id = dao.searchlast();
			return id;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
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
