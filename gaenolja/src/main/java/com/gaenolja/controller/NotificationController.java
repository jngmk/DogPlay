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

import com.gaenolja.model.dto.Notification;
import com.gaenolja.model.service.NotificationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class NotificationController {
	@Autowired
	private NotificationService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/notification/searchall")
	@ApiOperation("notification 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}

	@GetMapping("/api/v1/notification/search/userid")
	@ApiOperation("user로 notification 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@RequestParam String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@GetMapping("/api/v1/notification/searchbyhotel")
	@ApiOperation("호텔별 notification 찾기")
	public ResponseEntity<Map<String, Object>> searchbyhotel(@RequestParam String hotelnumber){
		return handleSuccess(service.searchbyhotelnumber(hotelnumber));
	}
	
	@GetMapping("/api/v1/notification/searchbytarget")
	@ApiOperation("target으로 notification 찾기")
	public ResponseEntity<Map<String, Object>> searchbytarget(@RequestParam String target){
		return handleSuccess(service.searchbytarget(target));
	}

	@PostMapping("/api/v1/notification/insert")
	@ApiOperation("insert notification")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Notification notification){
		boolean res = service.insert(notification);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/notification/update")
	@ApiOperation("update notification")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Notification notification){
		boolean res = service.update(notification);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/notification/delete")
	@ApiOperation("delete notification")
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
