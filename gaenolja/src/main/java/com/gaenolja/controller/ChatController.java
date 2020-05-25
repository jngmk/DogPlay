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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaenolja.model.dto.Chat;
import com.gaenolja.model.service.ChatService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class ChatController {
	@Autowired
	private ChatService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/chat/searchall")
	@ApiOperation("chat 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/chat/search/{id}")
	@ApiOperation("id로 chat 나타내기")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/chat/searchbyreceive/{receive}/receive")
	@ApiOperation("받은 사람으로 chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@PathVariable String receive){
		return handleSuccess(service.searchbyreceive(receive));
	}
	
	@GetMapping("/api/v1/chat/searchbysend/{send}/send")
	@ApiOperation("보낸 사람으로 chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbysend(@PathVariable String send){
		return handleSuccess(service.searchbysend(send));
	}
		
	@GetMapping("/api/v1/chat/searchbytwo/{receive}/receive/{send}/send")
	@ApiOperation("두 사람으로  chat 찾기")
	public ResponseEntity<Map<String, Object>> searchbytwo(@PathVariable String receive, @PathVariable String send){
		return handleSuccess(service.searchbytwo(receive, send));
	}
		
	@PostMapping("/api/v1/chat/insert")
	@ApiOperation("insert chat")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Chat chat){
		service.insert(chat);
		return handleSuccess("success");
	}
		
	@DeleteMapping("/api/v1/chat/delete/{id}")
	@ApiOperation("delete chat")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int id){
		service.delete(id);
		return handleSuccess("success");
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
