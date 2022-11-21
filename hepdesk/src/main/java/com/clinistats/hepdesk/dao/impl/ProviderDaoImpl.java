package com.clinistats.hepdesk.dao.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.StaffFilter;
import com.clinistats.hepdesk.dao.interfaces.StaffDao;
import com.clinistats.hepdesk.domain.Customer;
import com.clinistats.hepdesk.domain.Status;
import com.clinistats.hepdesk.mapper.StaffMapper;
import com.clinistats.hepdesk.model.StaffModel;
import com.clinistats.hepdesk.repositry.ContactDetailsRepository;
import com.clinistats.hepdesk.repositry.StaffRepositry;
import com.clinistats.hepdesk.response.GetStaffResponse;
import com.clinistats.hepdesk.specification.ProviderSpecification;

@Repository("ProviderDaoImpl")

public class ProviderDaoImpl implements StaffDao {

	private static final Logger logger = LoggerFactory.getLogger(ProviderDaoImpl.class);

	@Autowired
	private StaffRepositry providerRepository;
//
//	@Autowired
//	private StaffProviderAuditLogRepository staffProviderAuditLogRepository;

	@Autowired
	private StaffMapper providerMapper;

//	@Value("${base_provider.path}")
//	private String basePath;

	@Autowired
	private ContactDetailsRepository contactDetailsRepository;

	@Override
	public String addStaff(Customer provider) {
		StaffModel model = providerMapper.toModel(provider);
		model.setRecordState(Status.ACTIVE);
//		model.setEhrAnalyticStatus(EhrAnalyticStatus.NOT_SENT); // making Provider status to be "NOT_SENT" while adding
//		model.setEhrAnalyticRemark("Not Sent To Analytics");
		model.setGlobalStatus(true);
		model.setFirstName(StringUtils.capitalize(model.getFirstName()).trim());
		model.setLastName(StringUtils.capitalize(model.getLastName()).trim());
		providerRepository.save(model);
		return model.getId().toString();
	}

	@Override
	public Customer getById(String id) {
		StaffModel findById = null;
		if (id != null)
			findById = providerRepository.findActiveProvider(Long.valueOf(id));
		return providerMapper.toDomain(findById);

	}

	@Override
	public GetStaffResponse getAll(StaffFilter filter, Pagination pagination) {
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());
		ProviderSpecification specification = new ProviderSpecification(filter);

		Page<StaffModel> providerModels = providerRepository.findAll(specification,
				PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort));
		return new GetStaffResponse(providerMapper.toDomains(providerModels.getContent()),
				providerModels.getTotalElements());

	}

	@Override
	public boolean deleteById(String id) {
		if (id != null) {
			Optional<StaffModel> findById = providerRepository.findById(Long.parseLong(id));
			if (findById.isPresent()) {
				providerRepository.delete(findById.get());

				return true;
			}
		}
		return false;
	}

	@Override
	public String updateStaff(Customer provider) {
		StaffModel model = providerMapper.toModel(provider);
		model.setFirstName(StringUtils.capitalize(model.getFirstName()).trim());
		model.setLastName(StringUtils.capitalize(model.getLastName()).trim());
		model.setRecordState(Status.ACTIVE);
//		model.setEhrAnalyticStatus(EhrAnalyticStatus.NOT_SENT); // making provider back to "NOT_SENT" while updating
		Optional<StaffModel> providerModel = providerRepository.findById(model.getId());
		if (providerModel.isPresent()) {
			providerRepository.save(model);
		}
		return model.getId().toString();

	}

	@Override
	public Customer delete(String ids) {

		Optional<StaffModel> providerModel = providerRepository.findById(Long.parseLong(ids));
		if (providerModel.isPresent()) {
			providerRepository.updateProviderToDeactive(providerModel.get().getId());
			return providerMapper.toDomain(providerModel.get());
		}
		return null;
	}

//	@Override
//	public List<Object[]> getAllProvidersIds() {
//		return providerRepository.findAllProviderModelIds();
//
//	}

//	@Override
//	public List<GetIdNameDto> getProviderNamesByIds(String ids) {
//		try {
//			List<Long> longIds = new ArrayList<>();
//			if (ids.contains(",")) {
//				longIds = Arrays.asList(ids.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
//			} else {
//				longIds.add(Long.parseLong(ids));
//			}
//			List<StaffModel> provider = providerRepository.findAllById(longIds);
//			return providerMapper.toCustomDomain(provider);
//
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return new ArrayList<>();
//
//		}
//	}
//
//	@Override
//	public List<GetIdName> getALLProvidersByName(ProviderFilter filter) {
//		return providerRepository.searchProviderByName(filter.getQuery(), filter.getRecordState());
//
//	}
//
//	@Override
//	public List<GetIdName> getProviderIDNamesListBySearch(ProviderFilter filter) {
//		List<GetIdName> results = null;
//		if (filter.getQuery() == "") {
//			results = providerRepository.searchProviderByNameAndId(filter.getRecordState(), filter.getProviderIds());
//		} else {
//			results = providerRepository.searchProviderByNameAndId(filter.getRecordState(), filter.getQuery(),
//					filter.getProviderIds());
//
//		}
//		return results;
//	}
//
//	@Override
//	public ResponseEntity<InputStreamResource> addProviderPhoto(MultipartFile file, String providerId) {
//		ResponseEntity<InputStreamResource> returnStream = null;
//
//		logger.debug(" Before calling provider repository on findById");
//		Optional<StaffModel> model = providerRepository.findById(Long.parseLong(providerId));
//		if (!model.isPresent()) {
//			throw new ValidationException("Error: Provider does not exists with this id");
//		}
//
//		logger.debug("######################## provider model =" + model.get().getId());
//
//		Provider provider = providerMapper.toDomail(model.get());
//		provider.getId();
//		logger.debug("########################  provider object =" + provider);
//
//		String photoPath = null;
//
//		if (providerId != null) {
//			logger.debug("Provider Id=" + providerId);
//			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//			String extension = FilenameUtils.getExtension(fileName);
//			if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")) {
//				photoPath = FileImageExtension.storeProfilePicture(providerId, file, basePath, null);
//				logger.debug("After storing profile picture, photoPath = " + photoPath);
//				if (photoPath != null) {
//					providerRepository.addProviderPhotoPath(photoPath, model.get().getId());
//					returnStream = getProviderProfilePictureByPhotoPath(providerId, photoPath);
//				}
//				logger.debug(" Provider Photo Path saved, path int DB");
//			} else {
//				logger.debug(" Provider Photo Path Not saved into DB");
//			}
//
//		} else {
//			throw new ValidationException("Invalid operation!   File is not jpg format");
//		}
//
//		return returnStream;
//	}
//
//	@Override
//	public ResponseEntity<InputStreamResource> getProviderProfilePictureById(String providerId) {
//
//		Optional<StaffModel> model = providerRepository.findById(Long.parseLong(providerId));
//
//		if (!model.isPresent()) {
//			throw new ValidationException(" Exception, given provider id does not exists!");
//		}
//
//		Provider provider = providerMapper.toDomail(model.get());
//		String profileFName = provider.getPhotoPath();
//		if (profileFName != null) {
//			return getProviderProfilePictureByPhotoPath(providerId, profileFName);
//		}
//		return null;
//	}
//
//	public ResponseEntity<InputStreamResource> getProviderProfilePictureByPhotoPath(String providerId,
//			String fileName) {
//
//		String base_dir = new String(basePath);
//		String uploads_dir = base_dir.concat(File.separator);
//		logger.debug(" ------- storing file in path = " + uploads_dir);
//
//		logger.debug("Entered method: getProviderProfilePictureById, ProviderDaoImpl, profileFName = " + fileName);
//		String completeFileName = uploads_dir.concat(File.separator).concat(providerId).concat(File.separator)
//				.concat(fileName);
//
//		logger.debug(
//				"Entered method: getProviderProfilePictureById, ProviderDaoImpl, completeFileName=" + completeFileName);
//
//		return FileImageExtension.imageExtension(completeFileName);
//
//	}
//
//	@Override
//	public GetIdNameDto getProviderNameById(String id) {
//		StaffModel providerName = providerRepository.getProviderName(Long.valueOf(id));
//		GetIdNameDto providerNameAndId = new GetIdNameDto();
//		if (providerName != null) {
//			providerNameAndId.setId(providerName.getId().toString());
//			providerNameAndId.setName(providerName.getName());
//		}
//		return providerNameAndId;
//	}

	@Override
	public Long countByCellPhone(String cellPhone) {
		return contactDetailsRepository.countByCellPhoneIgnoreCase(cellPhone);
	}

//	@Override
//	public GetProviderResponse getALLProvidersTTD(TTDProviderFilter providerFilter, Pagination pagination) {
//		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());
//		TTDProviderSpecification specification = new TTDProviderSpecification(providerFilter);
//
//		Page<StaffModel> providerModels = providerRepository.findAll(specification,
//				PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort));
//		return new GetProviderResponse(providerMapper.toDomains(providerModels.getContent()),
//				providerModels.getTotalElements());
//	}
//
//	@Override
//	public List<String> getAllSpecialities(SpecialityFilter filter) {
//
//		if (filter.getQuery() == "") {
//			return providerRepository.searchSpecialityByFacility(Long.parseLong(filter.getFacilityId()),
//					filter.getRecordState());
//		} else {
//			return providerRepository.searchSpecialityByFacility(Long.parseLong(filter.getFacilityId()),
//					filter.getRecordState(), filter.getQuery());
//
//		}
//
//	}
//
//	@Override
//	public List<ProviderIdSpeciality> getAllProvidersBySpecialities(FacilitySpecialityFilter filter) {
//		if (filter.getQuery() == "") {
//			return providerRepository.searchProviderBySpeciality(Long.parseLong(filter.getFacilityId()),
//					filter.getSpeciality(), filter.getRecordState());
//		} else {
//			return providerRepository.searchProviderBySpeciality(Long.parseLong(filter.getFacilityId()),
//					filter.getSpeciality(), filter.getRecordState(), filter.getQuery());
//
//		}
//
//	}
//
//	@Override
//	public void generateStaffProvider(StaffproviderAuditLogDomain domain) { // Generating StaffProviders logs for
//																			// adding,updating and deleting providers
//		StaffProviderAuditLogModel model = providerMapper.toStaffRoviderLogModel(domain);
//		staffProviderAuditLogRepository.save(model);
//
//	}
//
//	@Override
//	public StaffProviderAuditLogResponse getAllStaffProvidersLogsWithPagination(StaffProvidersAuditLogFilter filter,
//			Pagination pagination) {
//		StaffProviderAuditLogSpecification specification = new StaffProviderAuditLogSpecification(filter);
//		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());
//		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
//		Page<StaffProviderAuditLogModel> pageModels = staffProviderAuditLogRepository.findAll(specification, pageable);
//		return new StaffProviderAuditLogResponse(providerMapper.toStaffProviderLogDomains(pageModels.toList()),
//				pageModels.getTotalElements());
//
//	}
//
//	@Override
//	public String getProviderDefaultScreen(String id) {
//		return providerRepository.getProviderDefaultScreen(Long.valueOf(id));
//	}
//
//	@Override
//	@Transactional
//	public List<Provider> getProviders() {
//		int limit = 50;
//		List<Provider> providerList = new ArrayList<>();
//		List<Provider> providers = new ArrayList<>();
//		List<StaffModel> providerModels = providerRepository.findActiveProviders(EhrAnalyticStatus.NOT_SENT);
//		if (providerModels != null && !providerModels.isEmpty()) {
//			providerList.addAll(providerMapper.toDomains(providerModels));
//			if (providerList.size() <= limit) {
//				return providerList;
//			} else {
//				for (int i = 0; i <= limit - 1; i++) {
//					providers.add(providerList.get(i));
//				}
//				return providers;
//			}
//		}
//		return new ArrayList<>();
//	}
//
//	@Override
//	@Transactional
//	public Provider updateProviderWithAnalyticStatus(Provider provider) {
//		if (provider == null) {
//			return null;
//		}
//		StaffModel savedModel = providerRepository.save(providerMapper.toModel(provider));
//		return providerMapper.toDomail(savedModel);
//
//	}
//
//	@Override
//	public String addProviderSignPhoto(MultipartFile file, String providerId) {
//
//		logger.debug(" Before calling provider repository on findById");
//		Optional<StaffModel> model = providerRepository.findById(Long.parseLong(providerId));
//		if (!model.isPresent()) {
//			throw new ValidationException("Error: Provider does not exists with this id");
//		}
//
//		logger.debug("######################## provider model =" + model.get().getId());
//
//		Provider provider = providerMapper.toDomail(model.get());
//		logger.debug("########################  provider object =" + provider);
//
//		String photoPath = null;
//
//		if (providerId != null) {
//			logger.debug("Provider Id=" + providerId);
//
//			photoPath = FileImageExtension.storeProfilePicture(providerId, file, basePath, null);
//			if (photoPath != null) {
//				providerRepository.addProviderSignPhotoPath(photoPath, model.get().getId());
//				return photoPath;
//			}
//
//		} else {
//			throw new ValidationException("Invalid operation!   File is not jpg format");
//		}
//
//		return photoPath;
//	}
//
//	@Override
//	public ResponseEntity<InputStreamResource> getProviderSignPictureById(String id) {
//
//		Optional<StaffModel> model = providerRepository.findById(Long.parseLong(id));
//
//		if (!model.isPresent()) {
//			throw new ValidationException(" Exception, given provider id does not exists!");
//		}
//
//		Provider provider = providerMapper.toDomail(model.get());
//		String profileFName = provider.getSignPhotoPath();
//		if (profileFName == null) {
//			return null;
//		}
//		return getProviderProfilePictureByPhotoPath(id, profileFName);
//	}
//
//	@Override
//	public List<GetIdName> getProviderNamesAndByIds(String providerId) {
//		return providerRepository.getProviderNamesAndByIds(Long.parseLong(providerId));
//	}
//
//	@Override
//	public List<GetIdName> getProviderIDNamesListBySearch(@Valid ProviderFilter filter, List<Long> providerId) {
//		List<GetIdName> results = null;
//
//		if (!CollectionUtils.isEmpty(providerId)) {
//			if (filter.getQuery() == "") {
//				results = providerRepository.searchProviderByNameAndId(filter.getRecordState(), providerId);
//			} else {
//				results = providerRepository.searchProviderByNameAndId(filter.getRecordState(), filter.getQuery(),
//						providerId);
//
//			}
//		} else {
//			if (filter.getQuery() == "") {
//				results = providerRepository.searchProviderByName(filter.getRecordState());
//			} else {
//				results = providerRepository.searchProviderByName(filter.getQuery(), filter.getRecordState());
//
//			}
//		}
//		return results;
//	}

}
