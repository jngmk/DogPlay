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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/api/v1/likes/checklikes")
	@ApiOperation("check likes")
	public ResponseEntity<Map<String, Object>> check(@RequestParam String userid, @RequestParam String hotelnumber){
		return handleSuccess(service.check(userid, hotelnumber));
	}
	
	@GetMapping("/api/v1/likes/search/userid")
	@ApiOperation("user로 likes 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@RequestParam String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@GetMapping("/api/v1/likes/searchbyhotel")
	@ApiOperation("호텔별 likes 찾기")
	public ResponseEntity<Map<String, Object>> searchbyhotel(@RequestParam String hotelnumber){
		return handleSuccess(service.searchbyhotelnumber(hotelnumber));
	}
	
	@GetMapping("/api/v1/likes/search/visitor")
	@ApiOperation("방문객별 likes + hotel 찾기")
	public ResponseEntity<Map<String, Object>> searchhotelbyuser(@RequestParam String visitor){
		return handleSuccess(service.searchhotelbyuserid(visitor));
	}
	
	@PostMapping("/api/v1/likes/insert")
	@ApiOperation("insert likes")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Likes likes){
		boolean res = service.insert(likes);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@DeleteMapping("/api/v1/likes/delete/hotel")
	@ApiOperation("delete likes")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam String userid, @RequestParam String hotelnumber){
		boolean res = service.delete(userid, hotelnumber);
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
