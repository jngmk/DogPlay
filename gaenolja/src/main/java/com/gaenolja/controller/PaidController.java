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

import com.gaenolja.model.dto.Kakaopay;
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
		System.out.println(handleFail(e.getMessage(), HttpStatus.OK));
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
		return handleSuccess(service.insert(paid));
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
	
	@PostMapping("/api/v1/paid/kakaopay/ready")
	@ApiOperation("카카오페이 준비")
	public ResponseEntity<Map<String, Object>> kakaopayready(@RequestBody Kakaopay kakao){
		return handleSuccess(service.kakaoready(kakao));
	}
	
	@GetMapping("/api/v1/paid/kakaopay")
	@ApiOperation("카카오페이 결제")
	public ResponseEntity<Map<String, Object>> kakaopay(@RequestParam String pg_token){
		return handleSuccess(service.kakaopay(pg_token));
	}
	
	@GetMapping("/api/v1/paid/usercancel")
	@ApiOperation("카카오페이 사용자 취소")
	public ResponseEntity<Map<String, Object>> kakaopayusercancel(){
		return handleSuccess(service.notapproved());
	}
	
	@GetMapping("/api/v1/paid/failkakaopay")
	@ApiOperation("카카오페이 실패")
	public ResponseEntity<Map<String, Object>> kakaopayfail(){
		return handleSuccess(service.kakaofail());
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
