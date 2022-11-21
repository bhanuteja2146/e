package com.clinistats.hepdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.clinistats.hepdesk.config.filter.JwtRequestFilter;
import com.clinistats.hepdesk.services.impl.UserDetailsServiceImpl;
import com.clinistats.hepdesk.services.interfaces.TokenService;
import com.clinistats.hepdesk.util.JwtTokenUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//	@Autowired
//	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.cors().disable();

		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests()

				.antMatchers("/oauth/login", "/swagger-ui.html", "/swagger-ui.html/**", "/actuator/info",
						"/helpdesk/actuator/health", "/helpdesk/actuator/**", "/webjars/**", "/v2/**", "/swagger-resources/**",
						"/helpdesk/api/health", "/api/userprofile/v1/user/add", "/api/userprofile/v1/user/resetpwd",
						"/api/userprofile/v1/user/softdelete", "/api/userprofile/v1/user/update",
						"/api/userprofile/v1/user/users-dropdown", "/api/v2/users",
						"/api/userprofile/v1/user/**/checkIfUserExists", "/api/userprofile/v1/user/createotp",
						"/api/userprofile/v1/user/resendotp", "/api/userprofile/v1/user/validateotp", "/helpdesk/sample",
						"/customer/**/add", "/v1/**")

				.permitAll().

				// all other requests need to be authenticated
				anyRequest().authenticated().and()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilter(new JwtRequestFilter(new CustomAuthenticationManager(), jwtTokenUtil, userDetailsService,
				tokenService));
//		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();

	}

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//			throws Exception {
//		return new CustomAuthenticationManager();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}
}
