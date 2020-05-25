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

import com.gaenolja.model.dto.Likes;
import com.gaenolja.model.service.LikesService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class LikesController {
	@Autowired
	private LikesService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/likes/searchall")
	@ApiOperation("likes 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/likes/searchbyuserid/{userid}/userid")
	@ApiOperation("user로 likes 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@PathVariable String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@GetMapping("/api/v1/likes/searchbyhotel/{hotelnumber}")
	@ApiOperation("호텔별 likes 찾기")
	public ResponseEntity<Map<String, Object>> searchbyhotel(@PathVariable int hotelnumber){
		return handleSuccess(service.searchbyhotelnumber(hotelnumber));
	}
	
	@PostMapping("/api/v1/likes/insert")
	@ApiOperation("insert likes")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Likes likes){
		service.insert(likes);
		return handleSuccess("success");
	}
		
	@DeleteMapping("/api/v1/likes/delete/")
	@ApiOperation("delete likes")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Likes likes){
		service.delete(likes);
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
