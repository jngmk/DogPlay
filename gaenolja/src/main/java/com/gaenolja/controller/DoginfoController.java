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

import com.gaenolja.model.dto.Doginfo;
import com.gaenolja.model.service.DoginfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class DoginfoController {
	@Autowired
	private DoginfoService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/doginfo/searchall")
	@ApiOperation("doginfo 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/doginfo/search/{id}")
	@ApiOperation("id로 doginfo 나타내기")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/doginfo/searchbyuserid/{userid}/userid")
	@ApiOperation("user로 doginfo 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@PathVariable String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
		
	@PostMapping("/api/v1/doginfo/insert")
	@ApiOperation("insert doginfo")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Doginfo doginfo){
		service.insert(doginfo);
		return handleSuccess("success");
	}
		
	@PutMapping("/api/v1/doginfo/update")
	@ApiOperation("update doginfo")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Doginfo doginfo){
		service.update(doginfo);
		return handleSuccess("success");
	}	

	@DeleteMapping("/api/v1/doginfo/delete/{id}")
	@ApiOperation("delete doginfo")
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
