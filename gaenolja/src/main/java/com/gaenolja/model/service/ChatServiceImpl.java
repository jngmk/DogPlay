package com.gaenolja.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.ChatDAO;
import com.gaenolja.model.dto.Chat;
import com.gaenolja.model.dto.NewChat;

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
			List<Chat> chat = dao.searchbysend(send);
			return chat;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<NewChat> searchbyuserid(String userid){
		List<NewChat> newchat = new ArrayList<NewChat>();
		try {
			List<Chat> chat = dao.searchbyuserid(userid);
			for (Chat c:chat) {
				String receive = c.getReceive();
				String send = c.getSend();
				NewChat chatnew = new NewChat();
				chatnew.setChat(c);
				if (receive.equals(userid)) {
					HashMap<Object, Object> map2 = new HashMap<Object, Object>();
					map2.put("receive", receive);
					map2.put("send", send);
					int count = dao.countbytwo(map2);
					chatnew.setCount(count);
				}
				newchat.add(chatnew);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return newchat;
	}
	
	@Override
	public boolean searchnewbyuserid(String userid){
		try {
			List<Chat> chat = dao.searchbyuserid(userid);
			for (Chat c:chat) {
				String receive = c.getReceive();
				String send = c.getSend();
				NewChat chatnew = new NewChat();
				chatnew.setChat(c);
				if (receive.equals(userid)) {
					HashMap<Object, Object> map2 = new HashMap<Object, Object>();
					map2.put("receive", receive);
					map2.put("send", send);
					int count = dao.countbytwo(map2);
					chatnew.setCount(count);
					if (count > 0) return true;
				}
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public boolean searchnew(String receive, String send){
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("receive", receive);
			map.put("send", send);
			int cnt = dao.countbytwo(map);
			if (cnt > 0) return true;
			else return false;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Chat> searchbychatid(int chatid){
		try {
			List<Chat> chat = dao.searchbychatid(chatid);
			return chat;
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
	public boolean insert(Chat chat) {
		try {
			dao.insert(chat);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean update(Chat chat) {
		try {
			dao.update(chat);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
}
