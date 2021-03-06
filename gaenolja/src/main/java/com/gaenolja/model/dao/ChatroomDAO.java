package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Chatroom;

@Mapper
public interface ChatroomDAO {
	public Chatroom search(int id);
	public void insert(Chatroom chatroom);
	public void delete(int id);
	public int searchlast();
	public List<Integer> searchall();
}
