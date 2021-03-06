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
	
	@GetMapping("/api/v1/response/search")
	@ApiOperation("id로 response 나타내기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/response/search/review")
	@ApiOperation("reviewid로 response 나타내기")
	public ResponseEntity<Map<String, Object>> searchbyreview(@RequestParam int reviewid){
		return handleSuccess(service.searchbyreview(reviewid));
	}
	
	@GetMapping("/api/v1/response/searchbyuserid/")
	@ApiOperation("user로 response 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@RequestParam String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@PostMapping("/api/v1/response/insert")
	@ApiOperation("insert response")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Response response){
		boolean res = service.insert(response);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/response/update")
	@ApiOperation("update response")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Response response){
		boolean res = service.update(response);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/response/delete")
	@ApiOperation("delete response")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam int id){
		boolean res = service.delete(id);
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
