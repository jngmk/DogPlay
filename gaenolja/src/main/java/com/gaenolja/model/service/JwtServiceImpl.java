package com.gaenolja.model.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.gaenolja.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {
	private static String SECRETKEY = "DogApplicationSecretKey";
	
	private static int HOUR = 1;
	
	@Override
	public String makeJwt(User user) {
		try {
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
			
			Date expireTime = new Date();
			expireTime.setTime(expireTime.getTime()+1000*60*60*HOUR);
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);
			
			Key signinKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
			
			Map<String, Object> headerMap = new HashMap<>();
			headerMap.put("typ", "JWT");
			headerMap.put("alg", "HS256");
			
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getUserid());
			map.put("nickname", user.getNickname());
			
			JwtBuilder builder = Jwts.builder().setHeader(headerMap)
							.setClaims(map)
							.setExpiration(expireTime)
							.signWith(signatureAlgorithm, signinKey);
			
			return builder.compact();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean checkJwt(String jwt) {
		try {
			@SuppressWarnings("unused")
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRETKEY))
							.parseClaimsJws(jwt).getBody();
			return true;
		}catch (ExpiredJwtException e) {
			// 토큰 만료
			return false;
		} catch (JwtException e) {
			// 토큰 변조 
			return false;
		}
	}
}
