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

import com.gaenolja.model.dto.Hotelroom;
import com.gaenolja.model.service.HotelroomService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class HotelroomController {
	
	@Autowired
	private HotelroomService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/hotelroom/searchall")
	@ApiOperation("모든 방 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/hotelroom/search")
	@ApiOperation("id로 방 나타내기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/hotelroom/searchbyhotel")
	@ApiOperation("hotel 방 나타내기")
	public ResponseEntity<Map<String, Object>> searchbyhotel(@RequestParam String hotelnumber){
		return handleSuccess(service.searchbyhotel(hotelnumber));
	}
	
	@GetMapping("/api/v1/hotelroom/searchbyroom/hotelnumber")
	@ApiOperation("hotel에서 방 이름으로 방 찾기")
	public ResponseEntity<Map<String, Object>> searchbyhotelandroom(@RequestParam String roomname, @RequestParam String hotelnumber){
		return handleSuccess(service.searchbyhotelandroom(roomname, hotelnumber));
	}
	
	@GetMapping("/api/v1/hotelroom/searchbyprice")
	@ApiOperation("가격별 방 찾기")
	public ResponseEntity<Map<String, Object>> searchbyprice(@RequestParam int price){
		return handleSuccess(service.searchbyprice(price));
	}
	
	@GetMapping("/api/v1/hotelroom/searchbysize")
	@ApiOperation("애견 size 별 방 찾기")
	public ResponseEntity<Map<String, Object>> searchbysize(@RequestParam int size){
		return handleSuccess(service.searchbysize(size));
	}
	
	@PostMapping("/api/v1/hotelroom/insert")
	@ApiOperation("insert hotelroom")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Hotelroom hotelroom){
		boolean res = service.insert(hotelroom);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/hotelroom/update")
	@ApiOperation("update hotelroom")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Hotelroom hotelroom){
		boolean res = service.update(hotelroom);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/hotelroom/delete")
	@ApiOperation("delete hotelroom")
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
