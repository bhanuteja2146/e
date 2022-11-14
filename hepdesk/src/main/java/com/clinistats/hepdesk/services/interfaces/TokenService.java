package com.clinistats.hepdesk.services.interfaces;

import java.util.Optional;

import com.clinistats.hepdesk.model.BlackListedTokens;

public interface TokenService {

	BlackListedTokens saveToken(BlackListedTokens token);
	
	Optional<BlackListedTokens> getToken(String token);
}
