package com.gaenolja.controller;

import java.time.LocalDateTime;
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

import com.gaenolja.model.dto.Reservation;
import com.gaenolja.model.service.ReservationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class ReservationController {
	@Autowired
	private ReservationService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/reservation/searchall")
	@ApiOperation("reservation 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@GetMapping("/api/v1/reservation/search/{id}")
	@ApiOperation("id로 reservation 나타내기")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/reservation/searchbyuserid/{userid}/userid")
	@ApiOperation("user로 reservation 찾기")
	public ResponseEntity<Map<String, Object>> searchbyuserid(@PathVariable String userid){
		return handleSuccess(service.searchbyuserid(userid));
	}
	
	@GetMapping("/api/v1/reservation/searchbypaidid/{paidid}/paidid")
	@ApiOperation("paidid로 reservation 찾기")
	public ResponseEntity<Map<String, Object>> searchbypaidid(@PathVariable int paidid){
		return handleSuccess(service.searchbypaidid(paidid));
	}
	
	@GetMapping("/api/v1/reservation/searchbyhotel/{hotelnumber}")
	@ApiOperation("호텔별 reservation 찾기")
	public ResponseEntity<Map<String, Object>> searchbyhotel(@PathVariable int hotelnumber){
		return handleSuccess(service.searchbyhotel(hotelnumber));
	}
	
	@GetMapping("/api/v1/reservation/searchbyhotel/{hotelnumber}/room/{roomname}")
	@ApiOperation("hotel + room 으로 reservation 찾기")
	public ResponseEntity<Map<String, Object>> searchbyhotelroom(@PathVariable int hotelnumber, @PathVariable String roomname){
		return handleSuccess(service.searchbyhotelandroom(hotelnumber, roomname));
	}
	
	@GetMapping("/api/v1/reservation/count/hotel/{hotelnumber}/room/{roomname}")
	@ApiOperation("id로 reservation 나타내기")
	public ResponseEntity<Map<String, Object>> counhotelroom(@PathVariable int hotelnumber, @PathVariable String roomname){
		return handleSuccess(service.countbyhotelandroom(hotelnumber, roomname));
	}
	
	@GetMapping("/api/v1/reservation/count/hotel/{hotelnumber}/room/{roomname}/start/{startdate}/finish/{finishdate}")
	@ApiOperation("date reservation 나타내기")
	public ResponseEntity<Map<String, Object>> countbydate(@PathVariable int hotelnumber, @PathVariable String roomname, @PathVariable LocalDateTime startdate, @PathVariable LocalDateTime finishdate){
		return handleSuccess(service.countbydate(hotelnumber, roomname, startdate, finishdate));
	}
	
	@PostMapping("/api/v1/reservation/insert")
	@ApiOperation("insert reservation")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Reservation reservation){
		boolean res = service.insert(reservation);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/reservation/update")
	@ApiOperation("update reservation")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Reservation reservation){
		boolean res = service.update(reservation);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/reservation/delete/{id}")
	@ApiOperation("delete reservation")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int id){
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
