package com.clinistats.helpdesk.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.clinistats.helpdesk.config.filter.FacilityFilter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.dao.interfaces.FacilityDao;
import com.clinistats.helpdesk.domain.Facility;
import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.exception.ValidationException;
import com.clinistats.helpdesk.mapper.FacilityMapper;
import com.clinistats.helpdesk.model.FacilityModel;
import com.clinistats.helpdesk.repositry.FacilityRepository;
import com.clinistats.helpdesk.repositry.StaffRepositry;
import com.clinistats.helpdesk.response.GetFacilityResponse;
import com.clinistats.helpdesk.response.GetIdName;
import com.clinistats.helpdesk.response.GetIdNameDto;
import com.clinistats.helpdesk.specification.FacilitySpecification;
import com.clinistats.hepdesk.util.FileImageExtension;

@Repository("facilityDaoImpl")
class FacilityDaoImpl implements FacilityDao {
	
	@Value("${base.path}")
	private String basePath;

	@Autowired
	private FacilityRepository facilityRepository;

//	@Autowired
//	private FacilityAuditLogRepository facilityAuditLogRepository;

	@Autowired
	private FacilityMapper facilityMapper;

//	@Autowired
//	private AppointmentRepository appointmentRepository;

	@Autowired
	private StaffRepositry providerRepository;

//	@Autowired
//	private NonProviderRepository nonProviderRepository;

//	@Autowired
//	private PatientRepository patientRepository;
	
	private final String newFacility="facility_";

	private static final Logger logger = LoggerFactory.getLogger(FacilityDaoImpl.class);

	@Override
	public String addFacility(Facility facility) {
		logger.debug(" to string facility=" + facility.getName());
		FacilityModel model = facilityMapper.toModel(facility);
		logger.debug(" to model,facility=" + model.getName() + " --- " + model.getId());

		if (facility.getPhone() != null && !facility.getPhone().isEmpty()) {
			boolean phoneExists = facilityRepository.existsByPhone(model.getPhone());
			if (phoneExists) {
				logger.debug(" Phone number already exists with another facility = " + model.getPhone());
				throw new ValidationException(
						"Phone: " + model.getPhone() + " already exists with another facility");
			}
		}

		model.setRecordState(Status.ACTIVE);
//		model.setEhrAnalyticStatus(EhrAnalyticStatus.NOT_SENT); //making facility status to be "NOT_SENT" while adding
//		model.setEhrAnalyticRemark("Not Sent To Analytics");
		facilityRepository.save(model);         // saved facility withou facility image
																					    // path
		return model.getId().toString();
	}

	@Override
	public Facility getFacilityById(long id) {
		FacilityModel model = facilityRepository.findById(id);
		if (model == null)
			return null;
		if (model.getRecordState() == Status.INACTIVE)
			return null;

		return facilityMapper.toDomain(model);
	}

	@Override
	public boolean existByName(String name) {
		return facilityRepository.existByName(name);
	}

	@Override
	public List<GetIdNameDto> getFacilityByIdAndNames(String ids) {

		try {

			List<Long> longIds = new ArrayList<>();
			if (ids.contains(",")) {
				longIds = Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id))
						.collect(Collectors.toList());
			} else {
				longIds.add(Long.parseLong(ids));
			}
			List<FacilityModel> provider = facilityRepository.findAllById(longIds);

			return facilityMapper.toCustomDomain(provider);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	public GetFacilityResponse getAllFacilities(Pageable paging) {
		Page<FacilityModel> pagedResult = facilityRepository.findAll(paging);
		return new GetFacilityResponse(facilityMapper.toDomains(pagedResult.toList()), pagedResult.getTotalElements());
	}

	@Override
	public String updateFacility(Facility facility) {
		Optional<FacilityModel> findById = facilityRepository.findById(Long.valueOf(facility.getId()));
		if (findById.isPresent()) {
			FacilityModel model = facilityMapper.toModel(facility);
			model.setRecordState(Status.ACTIVE);
//			model.setEhrAnalyticStatus(EhrAnalyticStatus.NOT_SENT); //making facility back to NOT_SENT while updating
			facilityRepository.save(model); // updated facility without facility image path
			return model.getId().toString();
		} else {
			return "Cannot Find Facility with Id ";
		}

	}

	@Override
	public String deleteFacilities(List<String> ids) {
		for (String facilityId : ids) {
			FacilityModel facilityModel = facilityRepository.findById(Integer.parseInt(facilityId));
			facilityModel.setRecordState(Status.INACTIVE);
			facilityRepository.save(facilityModel);
		}
		return null;
	}

	@Override
	public Facility deleteFacilityById(String id) {
		FacilityModel facilityModel = facilityRepository.findById(Integer.parseInt(id));
		if (facilityModel == null)
			return null;

		// now check if this facility id being used for appointment.
		Long fId = facilityModel.getId();
//		List<AppointmentModel> appointmentList = appointmentRepository.findByFacilityId(fId);
//		List<Long> appointmentIds = new ArrayList<>();
//		for (AppointmentModel appt : appointmentList) {
//			appointmentIds.add(appt.getId());
//		}
//		if (!appointmentList.isEmpty()) {
//			logger.debug(" Cannot delete this facility as this being used by appointments, appointmentIds ="
//					+ appointmentIds);
//			throw new ValidationException(
//					"Cannot delete. Already Appointments are there for this facility");
//		}

		//NEED TO CONFIRM
//		List<ProviderModel> providerList = providerRepository.findByFacilityId(fId); // now check if this facility id being used for Provider.
//		List<Long> providerIds = new ArrayList<>();
//		for (ProviderModel pro : providerList) {
//			providerIds.add(pro.getId());
//		}
//		if (!providerList.isEmpty()) {
//			logger.debug(" Cannot delete this facility as this being used by Provider, ProviderId =" + providerIds);
//			throw new ValidationException(
//					"Cannot delete. because this facility are used in Provider");
//		}

//		List<NonProviderModel> nonProviderList = nonProviderRepository.findByFacilityId(fId); 		// now check if this facility id being used for NonProvider.
//		List<Long> nonProviderIds = new ArrayList<>();
//		for (NonProviderModel nonPro : nonProviderList) {
//			nonProviderIds.add(nonPro.getId());
//		}
//		if (!nonProviderList.isEmpty()) {
//			logger.debug(
//					" Cannot delete this facility as this being used by NonProvider, NonProviderId =" + nonProviderIds);
//			throw new ValidationException(
//					"Cannot delete. because this facility are used in NonProvider");
//		}

//		List<PatientModel> patientList = patientRepository.findByFacilityId(fId); 		// now check if this facility id being used for Patient.
//		List<Long> patientIds = new ArrayList<>();
//		for (PatientModel pat : patientList) {
//			patientIds.add(pat.getId());
//		}
//		if (!patientList.isEmpty()) {
//			logger.debug(" Cannot delete this facility as this being used by Patient, PatientId =" + patientIds);
//			throw new ValidationException(
//					"Cannot delete. because this facility are used in Patient");
//		}

		facilityModel.setRecordState(Status.INACTIVE);
		FacilityModel deletedModel = facilityRepository.save(facilityModel);

		return facilityMapper.toDomain(deletedModel);
	}

	@Override
	public List<Object[]> getAllFacilitiesIds() {

		return facilityRepository.findAllFacilitiesIds();
	}

	@Override
	public GetFacilityResponse getFacility(FacilityFilter filter, Pagination pagination) {
		FacilitySpecification specification = new FacilitySpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());
		logger.debug("Sort = " + sort.toString());
		Page<FacilityModel> results = facilityRepository.findAll(specification,
				PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort));
		return new GetFacilityResponse(facilityMapper.toDomains(results.getContent()), results.getTotalElements());

	}

//	@Override
//	public List<GetIdNameColor> getFacilityIDNamesListBySearch(FacilityFilter request) {
//		List<GetIdNameColor> results = null;
//		if (request.getQuery() == "") {
//			results = facilityRepository.findFacilitiesByNameSearch(request.getRecordState());
//		} else {
//			results = facilityRepository.findFacilitiesByNameSearch(request.getRecordState(), request.getQuery());
//
//		}
//		return results;
//	}

	@Override
	public GetIdNameDto getFacilityByIdAndName(String id) {
		FacilityModel facilityName = facilityRepository.getFacilityName(Long.valueOf(id));
		GetIdNameDto facilityIdAndName = new GetIdNameDto();
		if (facilityName != null) {
			facilityIdAndName.setId(facilityName.getId().toString());
			facilityIdAndName.setName(facilityName.getName());
		}
		return facilityIdAndName;
	}

	@Override
	public Long countByName(String name) {
		return facilityRepository.countByNameIgnoreCase(name);
	}

	@Override
	public Long countByPhone(String phone) {
		return facilityRepository.countByPhoneIgnoreCase(phone);
	}

//	@Override
//	public void generateFacility(FacilityAuditLogDomain domain) {
//		FacilityAuditLogModel model = facilityMapper.toAuditModel(domain);
//		model.setRecordstate(Status.ACTIVE);
//		facilityAuditLogRepository.save(model);
//
//	}

//	@Override
//	public FacilityAuditLogResponse getAllFacilityLogsWithPagination(FacilityAuditLogFilter filter, Pagination pagination) { // Getting facilities logs with paginations
//		FacilityAuditLogSpecification specification = new FacilityAuditLogSpecification(filter);
//		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());
//		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
//		Page<FacilityAuditLogModel> pageModels = facilityAuditLogRepository.findAll(specification, pageable);
//		return new FacilityAuditLogResponse(facilityMapper.toFacilityLogDomains(pageModels.toList()),
//				pageModels.getTotalElements());
//	}

	@Override
	public String uploadFacilityImage(MultipartFile file, String id) {
		
		logger.debug(" Before calling facilityRepository on uploadFacilityImage");
		FacilityModel model = facilityRepository.getFacilityName(Long.valueOf(id));
		if(model == null) {			
			throw new ValidationException("Error: Facility does not exists with this id");
		}

		logger.debug("######################## patient model ="+model.getId());
		
		Facility facility = facilityMapper.toDomain(model);
		String facilityId = facility.getId();
		logger.debug("########################  id ="+facilityId);


		String facilityPath = null;

		if( facilityId != null ) {
			logger.debug("Patient Id="+facilityId);
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());			
			facilityPath = FileImageExtension.storeProfilePicture(facilityId,file,basePath,newFacility);
				logger.debug("After storing facility image, facilityPath = "+facilityPath);
				if(facilityPath != null) {
					model.setFacilityImageurl(facilityPath);   
					facilityRepository.save(model) ;                      //added the file name into the db after saving in the folder
	            logger.debug(" Facility Image Path saved, path int DB");
				}else {
					logger.debug(" Facility Image Path Not saved into DB");					
				}
			
			return "uploading the image file:-"+fileName+" is success";
		}
		else
			return "Failed"; 
	}
	
	
	@Override
	public ResponseEntity<InputStreamResource> getFacilityImageById(String facilityId) {
		String baseDir = basePath;
		String uploadsDir = baseDir.concat(File.separator);
		logger.debug(" ------- storing file in path = "+uploadsDir);
		
		Facility facility = this.getFacilityById(Long.parseLong(facilityId));
		String facilityFileName=null;
		if(facility!=null) {
			 facilityFileName = facility.getFacilityImageurl();
		}
		String facilityFolder=newFacility.concat(facilityId);
		logger.debug("Entered method: getFacilityImageById, FacilityDaoImpl, FacilityFileName = "+facilityFileName);
		String completeFileName = uploadsDir.concat(File.separator).concat(facilityFolder).concat(File.separator).concat(facilityFileName);
		
		
		logger.debug("Entered method: getFacilityImageById, FacilityDaoImpl, completeFileName="+completeFileName);
		
		return FileImageExtension.imageExtension(completeFileName);
		
	}

	
	


//	@Override
//	@Transactional
//	public List<Facility> getFaclities() {
//		  int limit = 50;
//			List<Facility> facilityList = new ArrayList<>();
//			List<Facility> facilities = new ArrayList<>();
//			List<FacilityModel> facilityModels = facilityRepository.findActiveFacilities(EhrAnalyticStatus.NOT_SENT);
//			if (facilityModels != null && !facilityModels.isEmpty()) {
//				facilityList.addAll(facilityMapper.toDomains(facilityModels));
//				if (facilityList.size() <= limit) {
//					return facilityList;
//				} else {
//					for (int i = 0; i <= limit - 1; i++) {
//						facilities.add(facilityList.get(i));
//					}
//					return facilities;
//				}
//			}
//			return new ArrayList<>();
//	}

	@Override
	@Transactional
	public Facility updateFacilityWithAnalyticStatus(Facility facility) {
		Optional<FacilityModel> findById = facilityRepository.findById(Long.valueOf(facility.getId()));
		if (findById.isPresent()) {
			FacilityModel model = facilityMapper.toModel(facility);
			FacilityModel updateModel = facilityRepository.save(model); 
			return facilityMapper.toDomain(updateModel);
		} else {
			return null;
		}
	}

	@Override
	public List<GetIdName> getFacilityIdAndNamesById(String facilityId) {
		return  facilityRepository.findIdAndNameById(Long.valueOf(facilityId));
	}

//	@Override
//	public List<GetIdNameColor> getFacilityIDNamesListBySearch(FacilityFilter request, List<Long> assigedFacilites) {
//		List<GetIdNameColor> results = null;
//		if (request.getQuery() == "") {
//			results = facilityRepository.findFacilitiesByNameSearch(request.getRecordState(),assigedFacilites );
//		} else {
//			results = facilityRepository.findFacilitiesByNameSearch(request.getRecordState(), request.getQuery(), assigedFacilites);
//
//		}
//		return results;
//	}

	

}
