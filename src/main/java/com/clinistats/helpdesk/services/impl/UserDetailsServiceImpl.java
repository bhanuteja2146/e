package com.clinistats.helpdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.model.UserProfileModel;
import com.clinistats.helpdesk.repositry.UserProfileRepository;

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