package com.charity.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.TokenRepository;
import com.charity.util.Constants;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserTokenService {

	@Autowired
	private TokenRepository userTokenRepository;

	public boolean isValidToken(String token) {
		if (StringUtils.isBlank(token))
			return false;
		return userTokenRepository.isValidToken(token, new Date()) > 0;
	}

	@Async
	public void updateTokenExpirationDate(String token) {
		userTokenRepository.updateTokenExpirationDate(token,
				DateUtils.addMinutes(new Date(), Constants.TOKEN_EXPIRATION_MINUTES));
	}

}
