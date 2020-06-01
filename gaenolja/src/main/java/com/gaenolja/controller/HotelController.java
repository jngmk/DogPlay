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

import com.gaenolja.model.dto.Hotel;
import com.gaenolja.model.service.HotelService;
import com.gaenolja.model.service.HotelStarService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class HotelController {
	@Autowired
	private HotelService service;
	
	@Autowired
	private HotelStarService starservice;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/hotel/searchall")
	@ApiOperation("hotel")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/hotel/search/hotelnumber")
	@ApiOperation("id로 hotel 나타내기")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String hotelnumber){
		return handleSuccess(service.search(hotelnumber));
	}
	
	@GetMapping("/api/v1/hotel/search/hotelname")
	@ApiOperation("name으로 hotel 찾기")
	public ResponseEntity<Map<String, Object>> searchbyname(@RequestParam String hotelname){
		return handleSuccess(service.searchbyname(hotelname));
	}
		
	@GetMapping("/api/v1/hotel/search/latitude/longitude/distance")
	@ApiOperation("위도 + 경도로 hotel 찾기")
	public ResponseEntity<Map<String, Object>> searchbydistance(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int distance){
		return handleSuccess(service.searchbydistance(latitude, longitude, distance));
	}
	
	@PostMapping("/api/v1/hotel/insert")
	@ApiOperation("insert hotel")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Hotel hotel){
		boolean res = service.insert(hotel);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/hotel/update")
	@ApiOperation("update hotel")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Hotel hotel){
		boolean res = service.update(hotel);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/hotel/delete")
	@ApiOperation("delete hotel")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam String hotelnumber){
		boolean res = service.delete(hotelnumber);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/hotelstar/searchall")
	@ApiOperation("hotel + star")
	public ResponseEntity<Map<String, Object>> searchhotelstarall(){
		return handleSuccess(starservice.searchall());
	}
	
	@GetMapping("/api/v1/hotelstar/search/userid")
	@ApiOperation("hotel + star")
	public ResponseEntity<Map<String, Object>> searchhotelstarbyuserid(String userid){
		return handleSuccess(starservice.searchbyuserid(userid));
	}
	
	@GetMapping("/api/v1/hotelstar/search/latitude/longitude/distance")
	@ApiOperation("distance별로 hotel 찾기")
	public ResponseEntity<Map<String, Object>> searchbydistancehotelstar(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int distance){
		return handleSuccess(starservice.searchbydistance(latitude, longitude, distance));
	}
	
	@GetMapping("/api/v1/hotelstar/search/hotelnumber")
	@ApiOperation("id로 hotel+star 나타내기")
	public ResponseEntity<Map<String, Object>> searchhotelstar(@RequestParam String hotelnumber){
		return handleSuccess(starservice.search(hotelnumber));
	}
	
	@GetMapping("/api/v1/hotelstar/search/hotelname/latitude/longitude/distance")
	@ApiOperation("name으로 hotel+star 찾기")
	public ResponseEntity<Map<String, Object>> searchhotelstarbyname(@RequestParam String hotelname, @RequestParam double latitude, @RequestParam double longitude, @RequestParam int distance){
		return handleSuccess(starservice.searchbyname(hotelname, latitude, longitude, distance));
	}
	
	@GetMapping("/api/v1/hoteldetail/search")
	@ApiOperation("hoteldetail")
	public ResponseEntity<Map<String, Object>> hoteldeatail(@RequestParam String hotelnumber){
		return handleSuccess(starservice.hoteldetail(hotelnumber));
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
