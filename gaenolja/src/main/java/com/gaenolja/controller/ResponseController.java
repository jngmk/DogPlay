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

import com.gaenolja.model.dto.Response;
import com.gaenolja.model.service.ResponseService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class ResponseController {
	@Autowired
	private ResponseService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/response/searchall")
	@ApiOperation("response 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/response/search/{reviewid}")
	@ApiOperation("reviewid로 response 나타내기")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int reviewid){
		return handleSuccess(service.search(reviewid));
	}
	
	@GetMapping("/api/v1/response/searchbyuserid/{userid}/userid")
	@ApiOperation("user로 response 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@PathVariable String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@PostMapping("/api/v1/response/insert")
	@ApiOperation("insert response")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Response response){
		service.insert(response);
		return handleSuccess("success");
	}
		
	@PutMapping("/api/v1/response/update")
	@ApiOperation("update response")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Response response){
		service.update(response);
		return handleSuccess("success");
	}	

	@DeleteMapping("/api/v1/response/delete/{id}")
	@ApiOperation("delete response")
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
