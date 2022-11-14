package com.clinistats.helpdesk.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.model.BlackListedTokens;
import com.clinistats.helpdesk.repositry.TokenRepository;
import com.clinistats.helpdesk.services.interfaces.TokenService;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	public BlackListedTokens saveToken(BlackListedTokens token) {
		return tokenRepository.save(token);
	}

	@Override
	public Optional<BlackListedTokens> getToken(String token) {
		return tokenRepository.findByToken(token);
	}

}
