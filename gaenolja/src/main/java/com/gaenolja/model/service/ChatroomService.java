package com.gaenolja.model.service;

import com.gaenolja.model.dto.Chatroom;

public interface ChatroomService {
	public Chatroom search(int id);
	public boolean insert(Chatroom chatroom);
	public boolean delete(int id);
}
