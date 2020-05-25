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

import com.gaenolja.model.dto.Species;
import com.gaenolja.model.service.SpeciesService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class SpeciesController {
	
	@Autowired
	private SpeciesService service;
	
	@ExceptionHandler 
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/species/search/{id}")
	@ApiOperation("id로 종 찾기")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int id){
		return handleSuccess(service.search(id));
	}
	
	@GetMapping("/api/v1/species/searchall")
	@ApiOperation("모든 종 나타내기")
	public ResponseEntity<Map<String, Object>> searchall(){
		return handleSuccess(service.searchall());
	}
	
	@PostMapping("/api/v1/species/insert")
	@ApiOperation("insert species")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Species species){
		boolean res = service.insert(species);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}
		
	@PutMapping("/api/v1/species/update")
	@ApiOperation("update species")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Species species){
		boolean res = service.update(species);
		if (res) return handleSuccess("success");
		else return handleFail("fail", HttpStatus.OK);
	}	

	@DeleteMapping("/api/v1/species/delete/{id}")
	@ApiOperation("delete species")
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
