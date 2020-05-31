package com.gaenolja.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaenolja.model.dto.Chat;
import com.gaenolja.model.dto.Chatroom;
import com.gaenolja.model.service.ChatService;
import com.gaenolja.model.service.ChatroomService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class ChatController {
	@Autowired
	private ChatService service;
	
	@Autowired
	private ChatroomService roomservice;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/chat/searchall")
	@ApiOperation("chat 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/chat/search")
	@ApiOperation("id로 chat 찾기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/chat/search/receive")
	@ApiOperation("받은 사람으로 chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbyreceive(@RequestParam String receive){
		return handleSuccess(service.searchbyreceive(receive));
	}
	
	@GetMapping("/api/v1/chat/search/send")
	@ApiOperation("보낸 사람으로 chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbysend(@RequestParam String send){
		return handleSuccess(service.searchbysend(send));
	}
	
	@GetMapping("/api/v1/chat/search/userid")
	@ApiOperation("userid로 chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@RequestParam String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@GetMapping("/api/v1/chat/search/chatid")
	@ApiOperation("chatid로 chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbychatid(@RequestParam int chatid){
		return handleSuccess(service.searchbychatid(chatid));
	}
		
	@GetMapping("/api/v1/chat/search/receive/send")
	@ApiOperation("두 사람으로  chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbytwo(@RequestParam String receive, @RequestParam String send){
		return handleSuccess(service.searchbytwo(receive, send));
	}
	
	@GetMapping("/api/v1/chat/searchnew/")
	@ApiOperation("채팅창안에서 새로운  chat 찾기")
	public ResponseEntity<Map<String, Object>> searchnew(@RequestParam String receive, @RequestParam String send){
		return handleSuccess(service.searchnew(receive, send));
	}
	
	@GetMapping("/api/v1/chat/searchnewbyuserid/")
	@ApiOperation("전체 chat에서 새로운  chat 찾기")
	public ResponseEntity<Map<String, Object>> searchnewbyuserid(@RequestParam String userid){
		return handleSuccess(service.searchnewbyuserid(userid));
	}

	@PostMapping("/api/v1/chat/insert")
	@ApiOperation("insert chat")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Chat chat){
		boolean res = service.insert(chat);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@PutMapping("/api/v1/chat/update")
	@ApiOperation("update chat")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Chat chat){
		boolean res = service.update(chat);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	
		
	@DeleteMapping("/api/v1/chat/delete")
	@ApiOperation("delete chat")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam int id){
		boolean res = service.delete(id);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/chatroom/search")
	@ApiOperation("두 사람으로  chat 찾기")
	public ResponseEntity<Map<String, Object>> searchchatroombyid(@RequestParam int id){
		return handleSuccess(roomservice.search(id));
	}
		
	@PostMapping("/api/v1/chatroom/insert")
	@ApiOperation("insert chat")
	public ResponseEntity<Map<String, Object>> insertchatroom(@RequestBody Chatroom chatroom){
		boolean res = roomservice.insert(chatroom);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@DeleteMapping("/api/v1/chatroom/delete")
	@ApiOperation("delete chat")
	public ResponseEntity<Map<String, Object>> deletechatroom(@RequestParam int id){
		boolean res = roomservice.delete(id);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}

	public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
}
