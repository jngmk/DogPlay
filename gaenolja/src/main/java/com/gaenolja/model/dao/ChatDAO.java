package com.gaenolja.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Chat;

@Mapper
public interface ChatDAO {
	public List<Chat> searchall();
	public List<Chat> searchbyreceive(String receive);
	public List<Chat> searchbysend(String send);
	public List<Chat> searchbyuserid(String userid);
	public List<Chat> searchbytwo(HashMap<Object, Object> map);
	public int countbytwo(HashMap<Object, Object> map);
	public int checkunread(String receive);
	public List<Chat> searchbychatid(int chatid);
	public Chat search(int id);
	public void insert(Chat chat);
	public void update(Chat chat);
	public void delete(int id);
}
