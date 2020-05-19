package com.gaenolja.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaenolja.model.dto.User;
import com.gaenolja.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	@GetMapping("/api/user/searchbyuserid/{userid}/userid")
	@ApiOperation("userid로 user 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@PathVariable String userid){
		return handleSuccess(service.search(userid));
	}
	
	@PostMapping("/api/user/signup")
	@ApiOperation("회원가입")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody User user){
		service.insert(user);
		return handleSuccess("success");
	}
	
	@PostMapping("/user/googlelogin")
	@ApiOperation("google 로그인")
	public ResponseEntity<Map<String, Object>> googleLogin(@RequestBody String idToken) {
		return handleSuccess(service.googleLogin(idToken));
	}
	
	@PostMapping("/user/naverlogin")
	@ApiOperation("naver 로그인")
	public ResponseEntity<Map<String, Object>> naverLogin(@RequestBody String token) {
		token = token.substring(0, token.length()-1);
		return handleSuccess(service.naverLogin(token));
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
