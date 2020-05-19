package com.gaenolja.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaenolja.model.dao.UserDAO;
import com.gaenolja.model.service.JwtService;
import com.gaenolja.model.dto.User;

public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO dao;
	
	@Autowired
	JwtService service;
	
	@Autowired
	public User search(String userid) {
		try {
			User user = dao.search(userid);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Autowired
	public void insert(User user) {
		try {
			dao.insert(user);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	public void update(User user) {
		try {
			dao.update(user);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	public void delete(String userid) {
		try {
			dao.delete(userid);
		}catch(Exception e) {
			e.printStackTrace();
		}
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
			
			if (findedUser != null) {
				result.put("token", service.makeJwt(findedUser));
			} else {
				result.put("email", email);
			};
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
			if (findedUser != null) {
				result.put("token", service.makeJwt(findedUser));
			} else {
				result.put("email", email);
			};

		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
