package com.clinistats.hepdesk.services.impl;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clinistats.hepdesk.config.filter.FacilityFilter;
import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.dao.interfaces.FacilityDao;
import com.clinistats.hepdesk.domain.Facility;
import com.clinistats.hepdesk.exception.ValidationException;
import com.clinistats.hepdesk.response.GetFacilityResponse;
import com.clinistats.hepdesk.services.interfaces.FacilityUseCase;

@Service
public class AddFacilityUseCaseImpl implements FacilityUseCase {

	@Autowired
	private FacilityDao facilityDao;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(AddFacilityUseCaseImpl.class);

	@Override
	public String addFacility(Facility facility) {

		boolean exist = facilityDao.existByName(facility.getName());
		if (exist) {
			// throw new ValidationException(Messages.RESOURCE_FACILITY_NAME_ALREADY_EXIST);
			logger.info("EHR-UE-APPOINTMENT-1001: "
					+ messageSource.getMessage("facility.already_exists_1001", null, Locale.ENGLISH));
			throw new ValidationException(
					messageSource.getMessage("facility.already_exists_1001", null, Locale.ENGLISH),
					"EHR-UE-APPOINTMENT-1001");

		}

		return facilityDao.addFacility(facility);
	}

//	@Override
//	public void generateFacility(FacilityAuditLogDomain domain) {
//		facilityDao.generateFacility(domain);
//		
//	}

	@Override
	public String uploadFacilityImage(MultipartFile file, String id) {

		return facilityDao.uploadFacilityImage(file, id);
	}

	@Override
	public String updateFacility(Facility facility) {
		return facilityDao.updateFacility(facility);
	}

	@Override
	public Facility getFacilityById(long id) {
		return facilityDao.getFacilityById(id);
	}

	@Override
	public Long countByName(String name) {
		return facilityDao.countByName(name);
	}

	@Override
	public Long countByPhone(String phone) {
		return facilityDao.countByPhone(phone);
	}

	@Override
	public void deleteFacilities(List<String> ids) {
		facilityDao.deleteFacilities(ids);
	}

	@Override
	public Facility deleteFacilityById(String id) {
		return facilityDao.deleteFacilityById(id);
	}

	@Override
	public GetFacilityResponse getFacility(FacilityFilter filter, Pagination pagination) {
		return facilityDao.getFacility(filter, pagination);
	}


	@Override
	public ResponseEntity<InputStreamResource> getFacilityImageById(String id) {

		return facilityDao.getFacilityImageById(id);
	}


}
