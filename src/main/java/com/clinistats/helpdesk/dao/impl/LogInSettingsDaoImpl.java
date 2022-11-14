package com.clinistats.helpdesk.dao.impl;

/**
 * 
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clinistats.helpdesk.config.filter.Filter;
import com.clinistats.helpdesk.config.filter.LogUserFilter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UserLogFilter;
import com.clinistats.helpdesk.dao.interfaces.LogInSettingsDao;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.mapper.LogInSettingsLogMapper;
import com.clinistats.helpdesk.mapper.LogInSettingsMapper;
import com.clinistats.helpdesk.mapper.UserProfileMapper;
import com.clinistats.helpdesk.model.LogInSettingsLogModel;
import com.clinistats.helpdesk.model.LogInSettingsModel;
import com.clinistats.helpdesk.model.UnLockAuditModel;
import com.clinistats.helpdesk.model.UserProfileModel;
import com.clinistats.helpdesk.repositry.LogInSettingsLogRepository;
import com.clinistats.helpdesk.repositry.LogInSettingsRepository;
import com.clinistats.helpdesk.repositry.UnLockAuditRepository;
import com.clinistats.helpdesk.repositry.UserProfileRepository;
import com.clinistats.helpdesk.response.GetLogInSettingsLogResponse;
import com.clinistats.helpdesk.response.GetLogInSettingsResponse;
import com.clinistats.helpdesk.response.GetUnLockLogResponse;
import com.clinistats.helpdesk.specification.LogInSettingsLogSpecification;
import com.clinistats.helpdesk.specification.LogInSettingsSpecification;
import com.clinistats.helpdesk.specification.UnlockedLogSpecification;

/**
 * @author giri
 *
 */
@Repository("logInSettingsDaoImpl")
public class LogInSettingsDaoImpl implements LogInSettingsDao {

	@Autowired
	private LogInSettingsRepository logInSettingsRepository;

	@Autowired
	private LogInSettingsMapper logInSettingsMapper;

	@Autowired
	private LogInSettingsLogRepository logInSettingsLogRepository;

	@Autowired
	private LogInSettingsLogMapper logInSettingsLogMapper;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private UnLockAuditRepository unLockAuditRepository;

	private static final String NOT_FOUND = "favorites not found with ";
	private static final String ENABLED = " has been enabled !";
	private static final String DISABLED = " has been disabled !";
	private static final String UNLOCK = "Unlock";

	@Override
	public List<LogInSettings> addLogInSettings(List<LogInSettings> domain) {
		List<LogInSettingsModel> logInSettingsModel = logInSettingsMapper.toModel(domain);
		List<LogInSettingsModel> logInSettingsModelAdded = logInSettingsRepository.saveAll(logInSettingsModel);

		return logInSettingsMapper.toDomains(logInSettingsModelAdded);

	}

	@Override
	public LogInSettingsLog addLogInSettingsLog(LogInSettingsLog domain) {
		LogInSettingsLogModel logInSettingsLogModel = logInSettingsLogMapper.toModel(domain);
		LogInSettingsLogModel logInSettingsLogModelAdded = logInSettingsLogRepository.save(logInSettingsLogModel);

		return logInSettingsLogMapper.toDomain(logInSettingsLogModelAdded);
	}

	@Override
	public List<LogInSettings> updateLogInSettings(List<LogInSettings> domain) {
		List<LogInSettingsModel> logInSettingsModel = logInSettingsMapper.toModel(domain);
		List<LogInSettingsModel> logInSettingsModelAdded = logInSettingsRepository.saveAll(logInSettingsModel);

		return logInSettingsMapper.toDomains(logInSettingsModelAdded);

	}

	@Override
	public List<String> changeUserProfileStatus(List<CustomObjectUpdate> customObjectUpdates) {

		List<String> list = new ArrayList<>();

		if (customObjectUpdates.isEmpty()) {
			return list;
		}

		customObjectUpdates.forEach(obj -> {

			if (obj.getId() == null || obj.getId() == 0) {
				list.add(new StringBuilder().append(NOT_FOUND).append(obj.getId()).toString());
			} else {

				Optional<LogInSettingsModel> findById = logInSettingsRepository.findById(obj.getId());

				if (findById.isPresent()) {
					LogInSettingsModel logInSettingsModel = findById.get();
					logInSettingsModel.setRecordState(
							obj.getRecordState() == Status.ACTIVE ? Status.INACTIVE : obj.getRecordState());

					logInSettingsRepository.save(logInSettingsModel);

					if (Status.ACTIVE.equals(logInSettingsModel.getRecordState())) {
						list.add(new StringBuilder().append(obj.getId()).append(ENABLED).toString());
					}
					if (Status.INACTIVE.equals(logInSettingsModel.getRecordState())) {
						list.add(new StringBuilder().append(obj.getId()).append(DISABLED).toString());
					}
				} else {
					list.add(new StringBuilder().append(NOT_FOUND).append(obj.getId()).toString());
				}
			}
		});

		return list;
	}

	@Override
	public GetLogInSettingsResponse getAllLogInSettings(Filter filter, Pagination pagination) {
		LogInSettingsSpecification specification = new LogInSettingsSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<LogInSettingsModel> results = logInSettingsRepository.findAll(specification, pageable);

		return new GetLogInSettingsResponse(results.getTotalElements(),
				logInSettingsMapper.toDomains(results.getContent()));
	}

	@Override
	public LogInSettings getLogInSettingsList(Long facilityId) {
		LogInSettingsModel results = null;
		LogInSettings resultsList = null;
		results = logInSettingsRepository.findByFacilityId(facilityId);

		resultsList = logInSettingsMapper.toDomain(results);
		return resultsList;
	}

	@Transactional
	@Override
	public int unLockByLockedUserId(Long lockeduserid, Long unlockinguserid) {
		int updateFinal = 0;

		Optional<UserProfileModel> lockUserProfile = userProfileRepository.findById(lockeduserid);

		if (lockUserProfile.isPresent()) {
			LogInSettingsLog logInSettingsLog = new LogInSettingsLog();
			UserProfileModel userProfileModel = lockUserProfile.get();

			logInSettingsLog.setLockUserId(userProfileModel.getId());
			logInSettingsLog.setFacilityId(userProfileModel.getFacilityId());
			logInSettingsLog.setUnLockingUserId(unlockinguserid);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			logInSettingsLog.setUnLockDate(LocalDateTime.parse(userProfileModel.getLockDate(), formatter));

			logInSettingsLog.setUnLockDate(LocalDateTime.now());
			logInSettingsLog.setLockReason(userProfileModel.getLockReason());

			addLogInSettingsLog(logInSettingsLog);
		}

		int updateLockStatus = userProfileRepository.unLockByLockedUserIdLockStatus(UNLOCK, lockeduserid);
		if (updateLockStatus > 0) {
			updateFinal = 1;
		}
		return updateFinal;

	}

	@Transactional
	@Override
	public List<UserProfile> getAllLockedUser(String userName) {

		List<UserProfileModel> models = userProfileRepository.findByuserNameContaining(userName);
		if (models == null || models.isEmpty()) {
			return Collections.emptyList();
		}

		return userProfileMapper.toDomain(models);
	}

	@Override
	public LogInSettingsLog getById(long id) {
		Optional<LogInSettingsLogModel> findById = logInSettingsLogRepository.findById(id);

		if (findById.isPresent()) {

			return logInSettingsLogMapper.toDomain(findById.get());
		}

		return null;
	}

	@Override
	public Long getlimitLoginAttempt(Long facilityId) {
		LogInSettingsModel findById = logInSettingsRepository.findByFacilityId(facilityId);
		Long limitLoginAttempt = (long) 0;
		if (findById != null) {

			limitLoginAttempt = findById.getLimitLoginAttempt();
			return limitLoginAttempt;
		}

		return null;
	}

	@Override
	public List<LogInSettingsLog> findTop3BylockUserIdAndOldPasswrdNotNull(Long lockUserId) {

		List<LogInSettingsLogModel> models = logInSettingsLogRepository
				.findTop3BylockUserIdAndOldPasswrdIsNotNullOrderByIdDesc(lockUserId); // logInSettingsLogRepository.findTop3BylockUserIdAndOldPasswrdNotNullThanOrderByUnLockDateDesc(lockUserId);
		if (models == null || models.isEmpty()) {
			return Collections.emptyList();
		}
		return logInSettingsLogMapper.toDomains(models);
	}

	@Override
	public GetLogInSettingsLogResponse getAllLogInSettingsLog(LogUserFilter filter, Pagination pagination) {
		LogInSettingsLogSpecification specification = new LogInSettingsLogSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<LogInSettingsLogModel> results = logInSettingsLogRepository.findAll(specification, pageable);

		return new GetLogInSettingsLogResponse(results.getTotalElements(),
				logInSettingsLogMapper.toDomains(results.getContent()));
	}

	@Override
	public GetUnLockLogResponse getAllUnLockedUserLog(UserLogFilter filter, Pagination pagination) {
		UnlockedLogSpecification specification = new UnlockedLogSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<UnLockAuditModel> results = unLockAuditRepository.findAll(specification, pageable);

		return new GetUnLockLogResponse(results.getTotalElements(),
				logInSettingsLogMapper.toLogDomains(results.getContent()));
	}

}
