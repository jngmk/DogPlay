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

import com.gaenolja.model.dto.Hashtag;
import com.gaenolja.model.dto.HotelHash;
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
	
	@GetMapping("/api/v1/hashtag/search")
	@ApiOperation("id로 hashtag 나타내기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam int id){
		return handleSuccess(service.search(id));
	}
	
	@PostMapping("/api/v1/hashtag/insert")
	@ApiOperation("insert hashtag")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Hashtag hashtag){
		boolean res = service.insert(hashtag);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
			
	@PutMapping("/api/v1/hashtag/update")
	@ApiOperation("update hashtag")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Hashtag hashtag){
		boolean res = service.update(hashtag);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/hashtag/delete")
	@ApiOperation("delete hashtag")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam int id){
		boolean res = service.delete(id);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@PostMapping("/api/v1/hotelhash/insert")
	@ApiOperation("connect hotelhash")
	public ResponseEntity<Map<String, Object>> inserthotelhash(@RequestBody HotelHash hotelhash){
		boolean res = service.inserthotelhash(hotelhash);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/hotelhash/searchall")
	@ApiOperation("모든 hotelhash 나타내기")
	public ResponseEntity<Map<String, Object>> searchallhotelhash(){
		return handleSuccess(service.searchallhotelhash());
	}
	
	@GetMapping("/api/v1/hotelhash/search")
	@ApiOperation("id로 hotelhash 나타내기")
	public ResponseEntity<Map<String, Object>> searchhotelhash(@RequestParam int id){
		return handleSuccess(service.searchhotelhash(id));
	}
	
	@GetMapping("/api/v1/hotelhash/searchbyhotel")
	@ApiOperation("id로 hotelhash 나타내기")
	public ResponseEntity<Map<String, Object>> searchhotelhashbyhotel(@RequestParam String hotelnumber){
		return handleSuccess(service.searchhotelhashbyhotel(hotelnumber));
	}
	
	@GetMapping("/api/v1/hotelhash/searchbyhash")
	@ApiOperation("id로 hashtag 나타내기")
	public ResponseEntity<Map<String, Object>> searchhotelhashbyhash(@RequestParam int hashtag){
		return handleSuccess(service.searchhotelhashbyhash(hashtag));
	}
	
	@PutMapping("/api/v1/hotelhash/update")
	@ApiOperation("update hotelhash")
	public ResponseEntity<Map<String, Object>> updatehotelhash(@RequestBody HotelHash hotelhash){
		boolean res = service.updatehotelhash(hotelhash);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/hotelhash/delete")
	@ApiOperation("delete hotelhash")
	public ResponseEntity<Map<String, Object>> deletehotelhash(@RequestParam int id){
		boolean res = service.deletehotelhash(id);
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
