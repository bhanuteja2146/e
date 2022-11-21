package com.clinistats.hepdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinistats.hepdesk.model.UserProfileModel;
import com.clinistats.hepdesk.repositry.UserProfileRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserProfileRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserProfileModel user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("UserName " + username + " not found");
		}

		return user;
	}

}