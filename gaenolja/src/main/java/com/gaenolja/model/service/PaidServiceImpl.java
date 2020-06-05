package com.gaenolja.model.service;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gaenolja.model.dao.PaidDAO;
import com.gaenolja.model.dto.Kakaopay;
import com.gaenolja.model.dto.Paid;

@Service
public class PaidServiceImpl implements PaidService{
	
	@Autowired
	private PaidDAO dao;
	
	String appId = "";
	
	@Override
	public List<Paid> searchall(){
		try {
			List<Paid> paid = dao.searchall();
			return paid;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Paid search(int id) {
		try {
			Paid paid = dao.search(id);
			return paid;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Map kakaoready(Kakaopay kakao, HttpServletRequest request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			RestTemplate restTemplate = new RestTemplate();
			headers.add("Authorization", "KakaoAK " + "426e500513b390adca9cdb419a1d9a8c");
			headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			
			MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
			parameters.add("cid", kakao.getCid());
			parameters.add("partner_order_id", kakao.getPartner_order_id());
			parameters.add("partner_user_id", kakao.getPartner_user_id());
			parameters.add("item_name", kakao.getItem_name());
			parameters.add("quantity", kakao.getQuantity());
			parameters.add("total_amount", kakao.getTotal_amount());
			parameters.add("tax_free_amount", kakao.getTax_free_amount());
			parameters.add("approval_url", kakao.getApproval_url());
			parameters.add("cancel_url", kakao.getCancel_url());
			parameters.add("fail_url", kakao.getFail_url());
			
			
			HttpEntity<MultiValueMap<String, Object>> rest_request = new HttpEntity<>(parameters, headers);
			URI uri = URI.create("https://kapi.kakao.com/v1/payment/ready");
			
			ResponseEntity<Map> rest_response;
			rest_response = restTemplate.postForEntity(uri, rest_request, Map.class);
			Map body = rest_response.getBody();
			Paid paid = new Paid();
			
			String tid = (String) body.get("tid");

			paid.setTid(tid);
			paid.setCid(kakao.getCid());
			paid.setUserid(kakao.getPartner_user_id());
			paid.setPartner_order_id(kakao.getPartner_order_id());
			paid.setCancel_amount(kakao.getTotal_amount());
			paid.setCancel_tax_free_amount(kakao.getTax_free_amount());
			dao.insert(paid);

			HttpSession session = request.getSession();
			session.setAttribute("tid", tid);
			System.out.println(session.getAttribute("tid"));
			return body;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String notapproved(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String tid = (String) session.getAttribute("tid");
			Paid paid = dao.searchbyaid(tid);
			
			dao.delete(paid.getId());
			
			session.removeAttribute(tid);
			
			return "결제 취소";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String kakaofail(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String tid = (String) session.getAttribute("tid");
			
			Paid paid = dao.searchbyaid(tid);
			
			dao.delete(paid.getId());
			
			session.removeAttribute(tid);
			
			return "결제 실패";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int kakaopay(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String tid = (String) session.getAttribute("tid");
			
			Paid paid = dao.searchbyaid(tid);
			System.out.println(tid);
			session.removeAttribute(tid);
			System.out.println(request.getRequestURI());
			String pg_token = request.getParameter("pg_token");
			System.out.println(pg_token);
//			// 결제 승인
			HttpHeaders payheaders = new HttpHeaders();
			RestTemplate payrestTemplate = new RestTemplate();
			payheaders.add("Authorization", "KakaoAK " + "426e500513b390adca9cdb419a1d9a8c");
			payheaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			MultiValueMap<String, Object> payparameters = new LinkedMultiValueMap<>();
			payparameters.add("cid", paid.getCid());
			payparameters.add("tid", tid);
			payparameters.add("partner_order_id", paid.getPartner_order_id());
			payparameters.add("partner_user_id", paid.getUserid());
			payparameters.add("pg_token", pg_token);
			
			HttpEntity<MultiValueMap<String, Object>> pay_request = new HttpEntity<>(payparameters, payheaders);
			URI payuri = URI.create("https://kapi.kakao.com/v1/payment/approve");
			System.out.println(payuri);
			ResponseEntity<Map> pay_response;
			pay_response = payrestTemplate.postForEntity(payuri, pay_request, Map.class);
			Map paybody = pay_response.getBody();
			
			paid.setPg_token(pg_token);
			
			String aid = (String) paybody.get("aid");
			paid.setAid(aid);

			dao.update(paid);
			
			return paid.getId();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public boolean cancelpay(int id) {
		try {
			Paid paid = dao.search(id);
			String tid = paid.getTid();
			String cid = paid.getCid();
			int cancel_amount = paid.getCancel_amount();
			int cancel_tax_free_amount = paid.getCancel_tax_free_amount();

			HttpHeaders payheaders = new HttpHeaders();
			RestTemplate payrestTemplate = new RestTemplate();
			payheaders.add("Authorization", "KakaoAK " + "426e500513b390adca9cdb419a1d9a8c");
			payheaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			
			MultiValueMap<String, Object> payparameters = new LinkedMultiValueMap<>();
			payparameters.add("cid", cid);
			payparameters.add("tid", tid);
			payparameters.add("cancel_amount", cancel_amount);
			payparameters.add("cancel_tax_free_amount", cancel_tax_free_amount);
			
			HttpEntity<MultiValueMap<String, Object>> pay_request = new HttpEntity<>(payparameters, payheaders);
			URI payuri = URI.create("https://kapi.kakao.com/v1/payment/cancel");
			
			ResponseEntity<Map> pay_response;
			pay_response = payrestTemplate.postForEntity(payuri, pay_request, Map.class);
			Map paybody = pay_response.getBody();
			String status = (String) paybody.get("status");
			if (status.equals("CANCEL_PAYMENT")) {
				dao.delete(id);
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean insert(Paid paid) {
		try {
			dao.insert(paid);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Paid paid) {
		try {
			dao.update(paid);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
