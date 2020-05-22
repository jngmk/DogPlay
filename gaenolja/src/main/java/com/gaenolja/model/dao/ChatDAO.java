package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Chat;

@Mapper
public interface ChatDAO {
	public List<Chat> searchall();
	public Chat searchbyreceive(String receive);
	public Chat searchbysend(String send);
//	public Chat searchbytwo(Hashmap); 나중에
	public Chat search(int id);
	public void insert(Chat chat);
	public void delete(int id);
}
