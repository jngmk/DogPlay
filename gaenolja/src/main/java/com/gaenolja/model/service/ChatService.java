package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Chat;
import com.gaenolja.model.dto.NewChat;

public interface ChatService {
	public List<Chat> searchall();
	public List<Chat> searchbyreceive(String receive);
	public List<Chat> searchbysend(String send);
	public List<NewChat> searchbyuserid(String userid);
	public List<Chat> searchbytwo(String receive, String send);
	public Chat search(int id);
	public List<Chat> searchbychatid(int chatid);
	public boolean searchnew(String receive, String send);
	public boolean searchnewbyuserid(String userid);
	public boolean insert(Chat chat);
	public boolean update(Chat chat);
	public boolean delete(int id);
}
