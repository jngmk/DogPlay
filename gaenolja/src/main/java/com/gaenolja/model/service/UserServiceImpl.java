package com.gaenolja.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gaenolja.model.dao.UserDAO;
import com.gaenolja.model.dto.User;
import com.gaenolja.model.service.JwtService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO dao;
	
	@Autowired
	JwtService service;
	
	@Override
	public List<User> searchall() {
		try {
			List<User> users = dao.searchall();
			return users;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User search(String userid) {
		try {
			User user = dao.search(userid);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(User user) {
		try {
			dao.insert(user);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(User user) {
		try {
			dao.update(user);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(String userid) {
		try {
			dao.delete(userid);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Map<String, String> googleLogin(String idToken) {
		Map<String, String> result = new HashMap<>();
		try {
			String[] tokens = idToken.split("\\.");
			String tmp = (new String(Base64.decodeBase64(tokens[1]), "utf-8"));
			JSONParser parser = new JSONParser();
			Object obj;
			obj = parser.parse(tmp);
			JSONObject jsonObj = (JSONObject) obj;
			String email = (String) jsonObj.get("email");
			
			User findedUser = dao.search(email);
			
			if (findedUser == null) {
				User user = new User();
				user.setUserid(email);
				user.setSocial(1);
				int num = (int) (Math.random() * 1000);
				user.setNickname(email + Integer.toString(num));
				dao.insert(user);
				findedUser = dao.search(email);
			}
			result.put("token", service.makeJwt(findedUser));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Map<String, String> naverLogin(String token){
		Map<String, String> result = new HashMap<>();
		try {
			String header = "Bearer " + token; // Bearer 다음에 공백 추가
			String profileURL = "https://openapi.naver.com/v1/nid/me";
			URL profileurl = new URL(profileURL);
			HttpURLConnection profilecon = (HttpURLConnection)profileurl.openConnection();
			profilecon.setRequestMethod("GET");
			profilecon.setRequestProperty("Authorization", header);
			int profileCode = profilecon.getResponseCode();
			BufferedReader profilebr;
			if(profileCode==200) { // 정상 호출
				profilebr = new BufferedReader(new InputStreamReader(profilecon.getInputStream()));
			} else {  // 에러 발생
				profilebr = new BufferedReader(new InputStreamReader(profilecon.getErrorStream()));
			}
			String profileinputLine;
			StringBuffer response = new StringBuffer();
			while ((profileinputLine = profilebr.readLine()) != null) {
				response.append(profileinputLine);
			}
			profilebr.close();
			
			JSONParser profileparser = new JSONParser();
			Object profileobj;
			profileobj = profileparser.parse(response.toString());
			JSONObject profilejsonObj = (JSONObject) profileobj;
			String email = (String) ((JSONObject) profilejsonObj.get("response")).get("email");
			User findedUser = dao.search(email);
			if (findedUser == null) {
				User user = new User();
				user.setUserid(email);
				user.setSocial(0);
				int num = (int) (Math.random() * 1000);
				user.setNickname(email + Integer.toString(num));
				dao.insert(user);
				findedUser = dao.search(email);
			}
			result.put("token", service.makeJwt(findedUser));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Map<String, String> kakaoLogin(String token){
		Map<String, String> result = new HashMap<>();
		try {
			HttpHeaders profileheaders = new HttpHeaders();
			RestTemplate profileTemplate = new RestTemplate();
			profileheaders.add("Authorization", "Bearer " + token);
			HttpEntity<String> profile_request = new HttpEntity<>(profileheaders);
			URI profileuri = URI.create("https://kapi.kakao.com/v2/user/me");
			
			ResponseEntity<Map> profile;
			profile = profileTemplate.postForEntity(profileuri, profile_request, Map.class);
			Map profile_body = profile.getBody();
			Map account = (Map) profile_body.get("kakao_account");
			String email = (String) account.get("email");
			User findedUser = dao.search(email);
			if (findedUser == null) {
				User user = new User();
				user.setUserid(email);
				user.setSocial(2);
				Map pro = (Map) account.get("profile");
				String nickname = (String) pro.get("nickname");
				String picture = (String) pro.get("thumbnail_image_url");
				user.setNickname(nickname);
				user.setPicture(picture);
				dao.insert(user);
				findedUser = dao.search(email);
			}
			result.put("token", service.makeJwt(findedUser));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
