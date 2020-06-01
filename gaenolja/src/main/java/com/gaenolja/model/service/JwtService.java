package com.gaenolja.model.service;

import com.gaenolja.model.dto.User;

public interface JwtService {
	String makeJwt(User user) throws Exception;
	boolean checkJwt(String jwt) throws Exception;
}
