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

import com.gaenolja.model.dto.Hotelpicture;
import com.gaenolja.model.service.HotelpictureService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class HotelpictureController {
	@Autowired
	private HotelpictureService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/hotelpicture/searchall")
	@ApiOperation("모든 호텔 사진")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/hotelpicture/search")
	@ApiOperation("id로 hotelpicture 나타내기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/hotelpicture/searchbyhotel")
	@ApiOperation("호텔별 사진 나타내기")
	public ResponseEntity<Map<String, Object>> searchbythotel(@RequestParam String hotelnumber){
		return handleSuccess(service.searchbyhotel(hotelnumber));
	}
	
	@GetMapping("/api/v1/hotelpicture/searchbyhotel/name")
	@ApiOperation("호텔 및 이름으로 사진 찾기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String hotelnumber,@RequestParam String name){
		return handleSuccess(service.searchbyhotelandname(hotelnumber, name));
	}
		
	@PostMapping("/api/v1/hotelpicture/insert")
	@ApiOperation("insert hashtag")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Hotelpicture hotelpicture){
		boolean res = service.insert(hotelpicture);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/hotelpicture/update")
	@ApiOperation("update hashtag")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Hotelpicture hotelpicture){
		boolean res = service.update(hotelpicture);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/hotelpicture/delete")
	@ApiOperation("delete hashtag")
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
