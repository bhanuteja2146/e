package com.clinistats.hepdesk.dao.impl;

/**
 * 
 */

import java.io.File;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

/**
 * @author LENOVO
 *
 */

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.UnlockUserFilter;
import com.clinistats.hepdesk.config.filter.UserFilter;
import com.clinistats.hepdesk.dao.interfaces.UserProfileDao;
import com.clinistats.hepdesk.domain.Status;
import com.clinistats.hepdesk.domain.UserProfile;
import com.clinistats.hepdesk.mapper.UserProfileMapper;
import com.clinistats.hepdesk.model.UnLockAuditModel;
import com.clinistats.hepdesk.model.UserProfileModel;
import com.clinistats.hepdesk.repositry.UnLockAuditRepository;
import com.clinistats.hepdesk.repositry.UserProfileRepository;
import com.clinistats.hepdesk.request.DeleteUserRequest;
import com.clinistats.hepdesk.response.UserProfilePageable;
import com.clinistats.hepdesk.specification.LockedUserSpecification;
import com.clinistats.hepdesk.specification.UnlockedUserSpecification;
import com.clinistats.hepdesk.specification.UserProfileSpecification;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileDaoImpl.class);

	private final static String LOCK = "Lock";

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UnLockAuditRepository unLockAuditRepository;

	@Autowired
	private UserProfileMapper userProfileMapper;

//	@Autowired
//	private MobileOTPRepository mobileOTPRepository;
//
//	@Autowired
//	private MobileOTPMapper mobileOTPMapper;

	@Value("${base.path}")
	private String basePath;

	private Path fileStorageLocation = null;

	@Override
	@Transactional
	public UserProfile createUserprofile(UserProfile userProfile) throws Exception {

		UserProfileModel userProfileModel = userProfileMapper.toModel(userProfile);
		try {
			if (userProfile.getUserName() != null) {
				UserProfileModel findByUserName = userProfileRepository.findByUserName(userProfile.getUserName());
				if (findByUserName != null)
					throw new Exception("User already exists with same username");
			}
			if (userProfile.getMobile() != null) {
				UserProfileModel findByUserName = userProfileRepository.findByMobile(userProfile.getMobile());
				if (findByUserName != null)
					throw new Exception("Mobile Already Exists With Same Mobile Number");
			}

			if (userProfile.getEmailId().length() > 0) {
				UserProfileModel findByUserName = userProfileRepository.findByEmailId(userProfile.getEmailId());
				if (findByUserName != null)
					throw new Exception("EmailID already exists");
			}

			userProfileModel.setActivePatient(false);
			UserProfileModel model = userProfileRepository.save(userProfileModel);
			return userProfileMapper.toDomain(model);
			// return userProfileMapper.toDomain(model);
		} catch (Exception exce) {
			logger.error("Error in UserProfileDaoImpl # createuserprofile", exce.getMessage());
			throw exce;
		}
	}

	public static boolean isValidDate(Date coverage) throws ParseException {
		return new Date().before(coverage);
	}

	@Override
	@Transactional
	public UserProfile updateUserprofile(UserProfile userProfile) {
		UserProfileModel model = userProfileMapper.toModel(userProfile);
		model.setActivePatient(false);
		try {
			UserProfileModel save = userProfileRepository.save(model);
			return userProfileMapper.toDomain(save);

		} catch (Exception exce) {
			logger.error("Error in UserProfileDaoImpl # createuserprofile", exce.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public List<String> disableUserprofiles(List<Long> userProfiles) {

		List<String> strList = new ArrayList<>();

		userProfiles.forEach(userId -> {

//			boolean existsById = userProfileRepository.existsById(userId);
//
//			if (existsById) {

			Optional<UserProfileModel> findById = userProfileRepository.findById(userId);

			if (findById.isPresent()) {
//					UserProfileModel model = userProfileMapper.toModel(user);
				findById.get().setStatus(Status.INACTIVE);
				userProfileRepository.save(findById.get());

				strList.add(new StringBuilder() + "" + findById + " disabled");

			} else {
				strList.add(new StringBuilder() + "" + findById + " not found");
			}

//			} else {
//				strList.add(new StringBuilder() + "" + user.getId() + " not found");
//			}

		});

		return strList;
	}

	@Override
	public UserProfile getById(Long id) {

		Optional<UserProfileModel> findById = userProfileRepository.findById(id);

		if (findById.isPresent()) {

			return userProfileMapper.toDomain(findById.get());
		}

		return null;
	}

	@Override
	public UserProfile getByUserName(String userName) {

		UserProfileModel findById = userProfileRepository.findByUserName(userName);

		if (findById != null) {

			return userProfileMapper.toDomain(findById);
		}

		return null;
	}

	@Override
	public UserProfilePageable getUserProfile(UserFilter filter, Pagination pagination) {

		UserProfileSpecification specification = new UserProfileSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<UserProfileModel> results = userProfileRepository.findAll(specification, pageable);

		return new UserProfilePageable(results.getTotalElements(), userProfileMapper.toDomain(results.getContent()));

	}

	@Override
	public UserProfilePageable getLockUserProfile(UserFilter filter, Pagination pagination) {

		LockedUserSpecification specification = new LockedUserSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<UserProfileModel> results = userProfileRepository.findAll(specification, pageable);

		return new UserProfilePageable(results.getTotalElements(), userProfileMapper.toDomain(results.getContent()));

	}

	@Override
	public UserProfilePageable getUnlockUserProfile(UnlockUserFilter filter, Pagination pagination) {

		UnlockedUserSpecification specification = new UnlockedUserSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<UserProfileModel> results = userProfileRepository.findAll(specification, pageable);

		return new UserProfilePageable(results.getTotalElements(), userProfileMapper.toDomain(results.getContent()));

	}

	/**
	 *
	 */
	@Override
	@Transactional
	public void lockStatusSave(String userName, String reason) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		String dateString = dateFormat.format(new Date()).toString();
		userProfileRepository.lockStatusSave(LOCK, userName, dateString, reason);
	}

	@Override
	@Transactional
	public void limitLoginAttemptSave(Long count, String userName) {

		if (count == 0) {
			userProfileRepository.limitLoginAttemptAndLastAccessApplicationSave(count, userName);
		} else {
			userProfileRepository.limitLoginAttemptSave(count, userName);
		}

	}

	@Override
	public Long getlimitLoginAttemptCount(String userName) {
		Long count = (long) 0;
		UserProfileModel userProfileModel = userProfileRepository.findByUserName(userName);
		if (userProfileModel != null) {

			if (userProfileModel.getUsername().equalsIgnoreCase(userName)) {

				count = userProfileModel.getLimitLoginAttempt();
			}
		}

		return count;
	}

	@Override
	public Long findFacilityId(String userName) {
		Long count = (long) 0;
		UserProfileModel userProfileModel = userProfileRepository.findByUserName(userName);
		if (userProfileModel != null) {

			if (userProfileModel.getUsername().equalsIgnoreCase(userName)) {

				count = userProfileModel.getFacilityId();
			}
		}
		return count;
	}
//
//	@Override
//	public String storeUserData(MobileOTP dto) {
//		MobileOTPModel model = mobileOTPMapper.toModel(dto);
//		mobileOTPRepository.save(model);
//		return model.getId().toString();
//	}

	@Override
	public UserProfile getUserByMobile(String mobile) {
		UserProfileModel model = userProfileRepository.findByMobile(mobile);
		if (model != null) {

			return userProfileMapper.toDomain(model);
		}
		return null;
	}

	@Override
	public UserProfile getUserByEmailId(String emailId) {
		UserProfileModel model = userProfileRepository.findByEmailId(emailId);
		if (model != null) {

			return userProfileMapper.toDomain(model);
		}
		return null;
	}

//	@Override
//	public boolean checkIfNewUserExist(String mobile) {
//		boolean flag = false;
//		MobileOTPModel model = mobileOTPRepository.getByMobile(mobile);
//		if (model != null) {
//			flag = true;
//		}
//		return flag;
//	}

//	public String saveFiletoFS(MultipartFile file) {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		String basename = FilenameUtils.getBaseName(fileName);
//		String extension = FilenameUtils.getExtension(fileName);
//
//		String base_dir = new String(basePath);
//		// String patientId = id.toString();
//		String uploads_dir = base_dir.concat(File.separator);
//
//		if (!checkIfDirectoryExists(uploads_dir)) {
//			logger.info("StoreHelpSupportFile, storeFileOnSystem, uploads_dir does not exists");
//			logger.info("StoreHelpSupportFile, storeFileOnSystem, creating HelpSupportFile dir with uploads path="
//					+ uploads_dir);
//			File fdir = new File(uploads_dir);
//			fdir.mkdirs();
//		}
//
//		String newFileName = basename.concat(".").concat(extension);
//
//		logger.info(" newFileName = " + newFileName);
//
//		logger.info(" uploads_dir, filePath = " + uploads_dir);
//
//		try {
//			if (fileName.contains("..")) {
//				logger.info(" Invalid filename  path sequence !!!");
//			}
//			this.fileStorageLocation = Paths.get(uploads_dir).toAbsolutePath().normalize();
//			logger.info(" filestorelocation = " + this.fileStorageLocation);
//			Path targetLocation = this.fileStorageLocation.resolve(newFileName);
//			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException ex) {
//			logger.info(" Catch, Exception in saveFiletoFS method ");
//		}
//
//		return newFileName;
//	}

	public boolean checkIfDirectoryExists(String folderPath) {
		File dir = new File(folderPath);
		boolean exists = dir.exists();
		logger.info("Method:checkIfDirectoryExists, folder=" + dir.getPath() + " exists: " + exists);
		return exists;
	}

	@Override
	public UserProfile getByActiveId(Long id) {
		Optional<UserProfileModel> findById = userProfileRepository.findById(id);
		if (findById.isPresent()) {
			UserProfileModel model = findById.get();
			if (model.getStatus().toString().equalsIgnoreCase("ACTIVE")) {
				return userProfileMapper.toDomain(model);
			}
		}
		return null;
	}

	@Override
	public List<String> changeUserStatus(List<DeleteUserRequest> request) {
		List<String> ids = new ArrayList<String>();
		request.forEach(user -> {
			UserProfileModel findById = userProfileRepository.findByProviderId(user.getProviderId(),
					user.getUserName());
			if (findById != null) {
				findById.setStatus(Status.INACTIVE);
				userProfileRepository.save(findById);
				ids.add(String.valueOf(findById.getId()));
			}

		});
		return ids;
	}

	@Override
	public String unLockUserProfile(String unLockUserName, String logInUserName) {
		UserProfileModel findByUserName = userProfileRepository.findByUserName(unLockUserName);
		if (findByUserName != null) {
			UnLockAuditModel resp = new UnLockAuditModel();
			resp.setUserName(unLockUserName);
			resp.setUnLockedBy(logInUserName);
			resp.setUnLockDate(LocalDate.now());
			resp.setLockReason(findByUserName.getLockReason());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
			LocalDate lockedDtae = LocalDate.parse(findByUserName.getLockDate(), formatter);
			resp.setLockDate(lockedDtae);
			findByUserName.setUnLockDate(LocalDateTime.now().toString());
			findByUserName.setUnLockedBy(logInUserName);
			findByUserName.setLockStatus("unlocked");
			findByUserName.setLastAccessApplication(LocalDateTime.now());
			findByUserName.setLockReason(null);
			findByUserName.setLockDate(null);
			findByUserName.setLimitLoginAttempt(0L);
			userProfileRepository.save(findByUserName);
			unLockAuditRepository.save(resp);
			return "Sucessfully Unlcoked";
		}
		return null;
	}

}