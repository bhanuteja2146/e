package com.clinistats.helpdesk.services.interfaces;

import java.util.Optional;

import com.clinistats.helpdesk.model.BlackListedTokens;

public interface TokenService {

	BlackListedTokens saveToken(BlackListedTokens token);
	
	Optional<BlackListedTokens> getToken(String token);
}
