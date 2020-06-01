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

import com.gaenolja.model.dto.Paid;
import com.gaenolja.model.service.PaidService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class PaidController {
	@Autowired
	private PaidService service;

	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	@GetMapping("/api/v1/paid")
	@ApiOperation("모든 지불 정보 찾기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/paid/search")
	@ApiOperation("id로 지불 정보 찾기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam int id){
		return handleSuccess(service.search(id));
	}

	@PostMapping("/api/v1/paid/insert")
	@ApiOperation("insert paid")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Paid paid){
		boolean res = service.insert(paid);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/paid/update")
	@ApiOperation("update paid")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Paid paid){
		boolean res = service.update(paid);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/paid/delete")
	@ApiOperation("delete paid")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam int id){
		boolean res = service.delete(id);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@PostMapping("/api/v1/paid/kakaopay")
	@ApiOperation("카카오페이 결제")
	public ResponseEntity<Map<String, Object>> kakaopay(@RequestBody Paid paid){
		boolean res = service.kakaopay(paid);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/paid/cancelpay")
	@ApiOperation("카카오페이 결제 취소")
	public ResponseEntity<Map<String, Object>> cancelkakaopay(@RequestParam int id){
		boolean res = service.cancelpay(id);
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
