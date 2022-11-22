package com.clinistats.helpdesk.api;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.model.UserProfileModel;
import com.clinistats.helpdesk.request.JwtRequest;
import com.clinistats.helpdesk.response.UserDetailsResponse;
import com.clinistats.helpdesk.services.interfaces.AddLogInSettingsLogUseCase;
import com.clinistats.helpdesk.services.interfaces.GetLogInSettingsUseCase;
import com.clinistats.helpdesk.services.interfaces.GetUserProfileUseCase;
import com.clinistats.helpdesk.services.interfaces.ProvFacPermissionMappingUseCase;
import com.clinistats.helpdesk.services.interfaces.TokenService;
import com.clinistats.helpdesk.services.interfaces.UpdateUserProfileUseCase;
import com.clinistats.helpdesk.util.AES;
import com.clinistats.helpdesk.util.CommonUtil;
import com.clinistats.helpdesk.util.JwtTokenUtil;

@RestController
@CrossOrigin
@RequestMapping("/oauth")
//@PropertySource("classpath:app-config.properties")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UpdateUserProfileUseCase updateUserProfileUseCase;

	@Autowired
	private GetUserProfileUseCase getUserProfileUseCase;

	@Autowired
	private GetLogInSettingsUseCase getLogInSettingsUseCase;

//	@Autowired
//	private GenerateUserLog generateUserLog;

	@Autowired
	private AddLogInSettingsLogUseCase addLogInSettingsLogUseCase;

	@Autowired
	private ProvFacPermissionMappingUseCase provFacPermissionMappingUseCaseImpl;

	@Autowired
	private CommonUtil commonUtils;

	@Autowired
	private AES encryptDecryptUtil;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

//	private @Autowired SecurityAndPermissionsV2Repository securityAndPermissionV2Service;

	private String LAST_APPLICATION_USED = "User is Lock Becuase User not used application for <limitLastApplicationUsed> days";

	private String USER_LOCK = "Login Account is Locked, Please Contact to System Adminstrator.";

	private String WRONG_ATTEMPT_COUNT = "User is Locked For <wrongAttemptCount> Consecutive Wrong Password Attempts.";

	private final static String LOCK = "Lock";

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			HttpServletRequest httpRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		UserProfileModel userProfile = (UserProfileModel) userDetails;

		UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder().firstName(userProfile.getFirstName())
				.lastName(userProfile.getLastName()).username(userProfile.getUsername())
				.providerId(userProfile.getProviderId()).providerType(userProfile.getProviderType())
				.userType(userProfile.getUserType()).email(userProfile.getLastName()).userId(userProfile.getId())
				.facilityName(userProfile.getFacilityName()).build();
		final String token = jwtTokenUtil.generateToken(userDetailsResponse);
		Map<String, Object> userToken = new HashMap<String, Object>();
		userToken.put("userDetails", userDetailsResponse);
		userToken.put("token", token);

		if ("provider".equalsIgnoreCase(userProfile.getUserType())
				|| "resource".equalsIgnoreCase(userProfile.getUserType())
				|| "nonProvider".equalsIgnoreCase(userProfile.getUserType())) {
			// GET FACILITY ID LIST
			Map<Long, List<Long>> mappedFacilities = provFacPermissionMappingUseCaseImpl.getMappedFacilityListByStatus(
					String.valueOf(userProfile.getProviderId()), Status.ACTIVE,
					"provider".equalsIgnoreCase(userProfile.getUserType()) ? true : false);
			System.out.println("mappedFacilities " + mappedFacilities);
			if (!CollectionUtils.isEmpty(mappedFacilities)) {
				String convertedString = commonUtils
						.convertListToString(mappedFacilities.get(userProfile.getProviderId()));
				if (convertedString != null && !convertedString.isEmpty())
					userToken.put("assignedFacilities", encryptDecryptUtil.encrypt(convertedString));
			}
		}
		// generateUserLog.generateLoginUserLogs( UserLogStatus.SUCCESS, httpRequest,
		// true, userToken);
		return ResponseEntity.ok(userToken);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
//	public GenericResponse<?> logoutUser(HttpServletRequest request) throws Exception {
//		GenericResponse<?> genericResponse = new GenericResponse<>();
//		try {
//			BlackListedTokens tokens = new BlackListedTokens();
//			String requestTokenHeader = request.getHeader("Authorization").split("\\s")[1].trim();
//			tokens.setToken(requestTokenHeader);
//			tokens = tokenService.saveToken(tokens);
//			if (tokens != null) {
//				genericResponse.setMessage("logout succesful!");
//				genericResponse.setStatus(ResponseStatus.success.toString());
//				genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//
//				// generateUserLog.generateLogOutUserLogs( UserLogStatus.SUCCESS, request,
//				// false, requestTokenHeader);
//				return genericResponse;
//			} else {
//				genericResponse.setMessage("logout failed!");
//
//				genericResponse.setStatus(ResponseStatus.success.toString());
//				genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//				// generateUserLog.generateLogOutUserLogs( UserLogStatus.SUCCESS, request,
//				// false, requestTokenHeader);
//				return genericResponse;
//			}
//		} catch (Exception e) {
//			logger.error("*****exception in JwtAuthenticationController.logoutUser*****", e);
//			genericResponse.setMessage("error!");
//			genericResponse.setStatus(ResponseStatus.error.toString());
//			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
//			return genericResponse;
//		}
//	}
//
//	// Change password notification, calculate pending day for password change
//	@RequestMapping(value = "/change-password", method = RequestMethod.GET)
//	public GenericResponseList<Object> changepPsswordAfterNDays(@Context HttpServletRequest request) throws Exception {
//
//		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
//
//		Long passwordExpireDays = 0L;
//
//		String userName = getUserName(request);
//
//		ChangePasswordParam changePasswordParam = new ChangePasswordParam();
//
//		UserProfile userProfile = getUserProfileUseCase.getByName(userName);
//		long numberOfDayFromToCurrentDate = getNumberOfDayFromToCurrentDate(userProfile.getPasswordChangeDate());
//
//		LogInSettings logInSettingsList = getLogInSettingsUseCase.getLogInSettingsList(userProfile.getFacilityId());
//		if (logInSettingsList != null) {
//			passwordExpireDays = logInSettingsList.getPassExpireDays();
//			Long passExpireAlertDays = logInSettingsList.getPassExpireAlertDays();
//
//			Long pendingDays = passwordExpireDays - numberOfDayFromToCurrentDate;
//
//			changePasswordParam.setChangePwdPendingDays(pendingDays > 0 ? pendingDays : 0);
//
//			if (pendingDays > 0 && passExpireAlertDays > 0 && passExpireAlertDays >= pendingDays) {
//				changePasswordParam.setPwdExpireAlertDays(pendingDays);
//			} else {
//				changePasswordParam.setPwdExpireAlertDays(0);
//			}
//
//			genericResponse.setMessage("Change Password and Alert Message Parameter.");
//			genericResponse.setStatus(ResponseStatus.success.toString());
//			genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//			genericResponse.setContent(changePasswordParam);
//		}
//		genericResponse.setMessage("Configure Change Password days");
//		genericResponse.setStatus(ResponseStatus.success.toString());
//		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//		return genericResponse;
//	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			UserProfile userProfile = getUserProfileUseCase.getByName(username);

			if (userProfile == null)
				throw new ValidationException("Invalid username or password !");
			logger.info("login ->  authenticate  username validation done");

			if ("INACTIVE".equalsIgnoreCase(userProfile.getStatus().name())) {
				throw new ValidationException("Login Account is disabled");
			}

			if (LOCK.equals(userProfile.getLockStatus())) {
				throw new ValidationException(USER_LOCK);
			}

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			LockUserNotUsedAppForNDay(username);
			setLoginAttempt2Zero(username);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			logger.info("login ->  authenticate  passwords validation done");

			// check last 3 attempts
			LockUserForWrongPassword(username);
			// throw new Exception("INVALID_CREDENTIALS", e);
			throw new ValidationException("Invalid username or password !");
		}
	}

	// lock uer for one who have not used application for N days
	private void LockUserNotUsedAppForNDay(String username) throws Exception {

		UserProfile userProfile = getUserProfileUseCase.getByName(username);
		if (userProfile == null)
			return;
		if (LOCK.equals(userProfile.getLockStatus()))
			throw new ValidationException(USER_LOCK);

		LocalDateTime lastAccessApplication = userProfile.getLastAccessApplication();
		Long lastAccessApplicationDays = getNumberOfDayFromToCurrentDate(lastAccessApplication);
		lastAccessApplicationDays = lastAccessApplicationDays != null ? lastAccessApplicationDays : 0L;

		LogInSettings logInSettingsList = getLogInSettingsUseCase.getLogInSettingsList(userProfile.getFacilityId());
		if (logInSettingsList == null)
			return;
		Long limitLastApplicationUsed = logInSettingsList.getLimitLastApplicationUsed();
		limitLastApplicationUsed = limitLastApplicationUsed != null ? limitLastApplicationUsed : 0L;

		if (lastAccessApplicationDays > limitLastApplicationUsed) {

			String reason = LAST_APPLICATION_USED.replace("<limitLastApplicationUsed>",
					lastAccessApplicationDays.toString());

			updateUserProfileUseCase.lockStatusSave(username, reason);
			// update login setting
			addLogInSettingsLog(userProfile, reason);

			throw new Exception(USER_LOCK);
		}

	}

	// lock user on wrong password.
	private void LockUserForWrongPassword(String username) throws Exception {
		UserProfile userProfile = getUserProfileUseCase.getByName(username);

		if (userProfile == null)
			return;
		Long wrongAttemptCount = userProfile.getLimitLoginAttempt();

		wrongAttemptCount = wrongAttemptCount != null ? wrongAttemptCount : 0L;
		logger.info("login ->  authenticate	wrongAttemptCount" + wrongAttemptCount);

		// LogInSettings logInSettingsList =
		// getLogInSettingsUseCase.getLogInSettingsList(userProfile.getFacilityId());

		// Long limitLoginAttempt = logInSettingsList.getLimitLoginAttempt();
		// limitLoginAttempt = limitLoginAttempt!= null? limitLoginAttempt:0L;

		if (wrongAttemptCount > 2) {
			throw new Exception(USER_LOCK);
		}
		// else if(wrongAttemptCount == limitLoginAttempt) {
		else if (wrongAttemptCount == 2) {

			String reson = WRONG_ATTEMPT_COUNT.replace("<wrongAttemptCount>", wrongAttemptCount.toString());

			updateUserProfileUseCase.lockStatusSave(username, reson);

			addLogInSettingsLog(userProfile, reson);

			throw new ValidationException("Too many wrong attempts. You are account locked!");

		} else {
			logger.info("login ->  authenticate	incresiong count" + wrongAttemptCount);

			updateUserProfileUseCase.limitLoginAttemptSave(++wrongAttemptCount, username);
			logger.info("login ->  authenticate	incresiong count" + ++wrongAttemptCount);

			Long times = 3 - wrongAttemptCount++;
			throw new ValidationException("Invalid username or password ! You have " + times + " chances left");

		}
	}

	private void setLoginAttempt2Zero(String username) {
		updateUserProfileUseCase.limitLoginAttemptSave(0L, username);
	}

	private void addLogInSettingsLog(UserProfile userProfile, String reason) {

		LogInSettingsLog logInSettingsLog = new LogInSettingsLog();
		logInSettingsLog.setFacilityId(userProfile.getFacilityId());
		logInSettingsLog.setLockUserId(userProfile.getId());
		logInSettingsLog.setLockReason(reason);

		LocalDateTime now = LocalDateTime.now();
		/*
		 * DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"); String nowFormatDateTime =
		 * now.format(formatter);
		 */
		logInSettingsLog.setLockDate(now);
		logInSettingsLog.setRecordState(Status.ACTIVE);

		addLogInSettingsLogUseCase.addLogInSettingsLog(logInSettingsLog);
	}

	private String getUserName(HttpServletRequest req) {
		String requestTokenHeader = req.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
		// Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {

				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.error("#########  ClinicalAttributeController.Unable to get JWT Token");
			}
		} else {
			logger.warn("######### ClinicalAttributeController.JWT Token does not begin with Bearer String");
		}
		return username;
	}

	private Long getNumberOfDayFromToCurrentDate(LocalDateTime before) {
		before = (before == null) ? LocalDateTime.now() : before;
		LocalDateTime after = LocalDateTime.now();

		Long noOfDaysBetween = ChronoUnit.DAYS.between(before, after);
		return noOfDaysBetween;
	}
}

//class ScreenComparator implements Comparator<NavigationDTO> {
//	@Override
//	public int compare(NavigationDTO o1, NavigationDTO o2) {
//		if (o1.getOrder() > o2.getOrder()) {
//			return 1;
//		} else if (o1.getOrder() < o2.getOrder()) {
//			return -1;
//		}
//		return 0;
//	}
//}
