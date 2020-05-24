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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaenolja.model.dto.Hashtag;
import com.gaenolja.model.service.HashtagService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class HashtagController {
	
	@Autowired
	private HashtagService service;

	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/hashtag/searchall")
	@ApiOperation("모든 hashtag 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/hashtag/search/{id}")
	@ApiOperation("id로 hashtag 나타내기")
	public ResponseEntity<Map<String, Object>> search(@PathVariable String id){
		return handleSuccess(service.search(id));
	}
	
	@PostMapping("/api/v1/hashtag/insert")
	@ApiOperation("insert hashtag")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Hashtag hashtag){
		service.insert(hashtag);
		return handleSuccess("success");
	}
		
	@PutMapping("/api/v1/hashtag/update")
	@ApiOperation("update hashtag")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Hashtag hashtag){
		service.update(hashtag);
		return handleSuccess("success");
	}	

	@DeleteMapping("/api/v1/hashtag/delete/{id}")
	@ApiOperation("delete hashtag")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable String id){
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
