package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Chatroom;

public interface ChatroomService {
	public Chatroom search(int id);
	public int insert(Chatroom chatroom);
	public boolean delete(int id);
	public List<Integer> searchall();
}
