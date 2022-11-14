package com.clinistats.helpdesk.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.BlackListedTokens;

@Repository("TokenRepository")
public interface TokenRepository extends JpaRepository<BlackListedTokens, Long> {

	Optional<BlackListedTokens> findByToken(String Token);
}
