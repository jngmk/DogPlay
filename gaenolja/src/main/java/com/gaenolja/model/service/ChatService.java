package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Chat;

public interface ChatService {
	public List<Chat> searchall();
	public List<Chat> searchbyreceive(String receive);
	public List<Chat> searchbysend(String send);
	public List<Chat> searchbytwo(String receive, String send);
	public Chat search(int id);
	public boolean insert(Chat chat);
	public boolean delete(int id);
}
