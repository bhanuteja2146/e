package com.clinistats.helpdesk.api.statff;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mapstruct.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.clinistats.helpdesk.constatnts.ResponseStatus;
import com.clinistats.helpdesk.constatnts.ResponseStatusConstants;
import com.clinistats.helpdesk.domain.ContactDetails;
import com.clinistats.helpdesk.domain.Customer;
import com.clinistats.helpdesk.domain.Facility;
import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.mapper.StaffMapper;
import com.clinistats.helpdesk.request.AddStaffRequest;
import com.clinistats.helpdesk.request.GetAllStaffRequest;
import com.clinistats.helpdesk.request.UpdateContactDetailsRequest;
import com.clinistats.helpdesk.request.UpdateStaffRequest;
import com.clinistats.helpdesk.response.GenericResponse;
import com.clinistats.helpdesk.response.GenericResponseList;
import com.clinistats.helpdesk.response.GetStaffResponse;
import com.clinistats.helpdesk.services.interfaces.StaffUseCase;
import com.clinistats.helpdesk.services.interfaces.UserProfileUseCase;
import com.clinistats.helpdesk.util.CommonUtil;
import com.clinistats.helpdesk.util.JwtTokenUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

//	@Autowired
//	private StaffUseCase addProviderUsecase;

	@Autowired
	private StaffUseCase staffUseCase;

//	@Autowired
//	private GetProviderUseCase getProviderUseCase;

//	@Autowired
//	private GetAllProvidersUseCase getAllProvidersUseCase;

//	@Autowired
//	private DeleteProviderUseCase deleteProviderUseCase;

	@Autowired
	private StaffMapper providerRequestMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Autowired
//	private GetAllProvidersIdsUseCase getAllProvidersIdsUseCase;

//	@Autowired
//	private GetProviderNamesByIdsUseCase getProviderNamesByIdsUseCase;

//	@Autowired
//	private GetAllProvidersByNameUseCase getAllProvidersByNameUseCase;

//	@Autowired
//	private GetAllProvidersAndNonProviderUseCase getAllProvidersAndNonProviderUseCase;

//	@Autowired
//	private GetFacilityUseCase getFacilityUseCase;

//	@Autowired
//	private AddProviderPhotoUseCase addProviderPhotoUseCase;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

//	@Autowired
//	private GetProviderProfilePhotoUseCase getProviderProfilePhotoUseCase;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserProfileUseCase userProfileUseCase;

	@Autowired
	private CommonUtil commonUtil;
	/*
	 * @LoadBalanced
	 * 
	 * @Bean RestTemplate restTemplate() { return new RestTemplate(); }
	 */

//	@Autowired
//	RestTemplate restTemplate;

//	@Value("${entity.userprofileuri}")
//	private String inputUserProfileUrl;
//
//	@Value("${entity.updateuserprofileuri}")
//	private String updateUserProfileUrl;
//
//	@Value("${entity.deleteuserprofileuri}")
//	private String deleteUserProfileUrl;
//
//	@Value("${userprofile.servicename}")
//	private String serviceUrl;

	public CustomerController() {
		// restTemplate = new RestTemplate();
	}

	private String[] validImageFileTypes = { "jpg", "jpeg", "JPG", "JPEG", "pdf", "PDF", "png", "PNG" };

	private static final String ERROR = "error";

//	@Value("${entity.checkexists}")
//	private String existsUrl;

	@PostMapping(value = "/v1/add")
	@ResponseBody
	@ApiOperation(value = "create staff")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "create staff"),
			@ApiResponse(code = 500, message = "error"), })
	// @CircuitBreaker(name = MAIN_SERVICE, fallbackMethod =
	// "getUserProfileByActivFOSeStatusFallBack")
	public GenericResponse<Object> addProvider(@Valid @RequestBody AddStaffRequest request,
			@Context HttpServletRequest httpRequest) {
		if (request.getDeActiveStatus() == null) {
			request.setDeActiveStatus(Status.ACTIVE);
		}

		GenericResponse<Object> genericResponse = new GenericResponse<>();

		try {
			Customer provider = providerRequestMapper.toDomain(request);
			String id = null;
//			if (request.getPrimaryFacility() != null && !request.getPrimaryFacility().isEmpty()) {
//				Facility facility = new Facility();
//				facility.setId(request.getPrimaryFacility());
			// request.setPrimaryFacilityName(facility.getName());
//				provider.setPrimaryFacility(facility);

			if (request.getPassword().equals(request.getConfirmPassword())) {
				if (!checkUserNameExistOrNot(request.getUsername())) {
					String cellPhone = null;
					ContactDetails contactDetails = provider.getContactDetails();
					if (contactDetails != null) {
						cellPhone = contactDetails.getCellPhone();
						if (cellPhone.length() > 0) {
							Long countByPhone = staffUseCase.countByCellPhone(cellPhone);
							if (countByPhone > 0) {
								genericResponse.setMessage(cellPhone + " cell phone number is already exists!");
								genericResponse.setStatus(ResponseStatus.dataExists.toString());
								genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());
								return genericResponse;
							}
						}
					}

					id = staffUseCase.addStaff(provider);
					UserProfile obj = null;
					if (id != null) {
						obj = saveUserProfile(request, id);
					}
					if (obj != null) {
						obj.getId();
						genericResponse.setMessage("provider Created!");
						genericResponse.setStatus(ResponseStatus.success.toString());
						genericResponse.setStatusCode(ResponseStatusConstants.update.getValue());
						genericResponse.setContent(id);
						String action = "create log";
						String name = request.getFirstName() + " " + request.getLastName();
//								GenerateStaffProviderLogs(id, name, httpRequest, action); // method to create staff provider audit log
						// while adding providers
						return genericResponse;
					}
					logger.info(" Error:  user profile not creation failed.");
					genericResponse.setMessage("User profile create failed.");
					genericResponse.setStatus(ResponseStatus.dataInvalid.toString());
					genericResponse.setStatusCode(ResponseStatusConstants.dataInvalid.getValue());
					return genericResponse;

				}

				logger.error("user profile creation failed.");
				genericResponse.setMessage(" User Already Exists ");
				genericResponse.setStatus(ResponseStatus.dataExists.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
				return genericResponse;

			}
//			} 
			else {
				genericResponse.setMessage("provider not Created!");
				genericResponse.setStatus(ResponseStatus.dataInvalid.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.dataInvalid.getValue());
				return genericResponse;
			}
		} catch (Exception e) {
			logger.error("*****exception in providerController.addProvider*****", e);
			genericResponse.setMessage("error!");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
			return genericResponse;
		}

	}

	@PutMapping(value = "/v1/update")
	@ResponseBody
	@ApiOperation(value = "update provider")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "update provider"),
			@ApiResponse(code = 404, message = "not found"), })
	public GenericResponse<Object> updateProvider(@Valid @RequestBody UpdateStaffRequest request,
			@Context HttpServletRequest httpRequest) {
		GenericResponse<Object> genericResponse = new GenericResponse<>();
		Customer providerObj = staffUseCase.getById(request.getId());
		ContactDetails contactDetailsDB = null;
		String cellPhoneDB = null;
		String cellPhone = null;

		if (providerObj != null) {
			contactDetailsDB = providerObj.getContactDetails();
			if (contactDetailsDB != null)
				cellPhoneDB = contactDetailsDB.getCellPhone();
		}

		try {
			Customer provider = providerRequestMapper.toDomain(request);
			UpdateContactDetailsRequest updatedContactDetails = request.getContactDetails();
			if (updatedContactDetails != null) {
				cellPhone = updatedContactDetails.getCellPhone();
				if (!cellPhone.equalsIgnoreCase(cellPhoneDB)) {
					Long countByPhone = staffUseCase.countByCellPhone(cellPhone);
					if (countByPhone > 0) {
						genericResponse.setMessage(cellPhone + " cell phone number is already exists!");
						genericResponse.setStatus(ResponseStatus.dataExists.toString());
						genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());
						return genericResponse;
					}
				}
			}

			String id = null;
//			if (request.getPrimaryFacility() != null && !request.getPrimaryFacility().isEmpty()) {
//				Facility facility = new Facility();
//				facility.setId(request.getPrimaryFacility());
//				provider.setPrimaryFacility(facility);
			// if
			// (request.getPassword().equals(request.getConfirmPassword()))
			// {
			// String uId = updateUserProfile(request, providerObj);
			id = staffUseCase.updateStaff(provider);

			// }
//			}

			if (id != null) {
				genericResponse.setMessage("Staff updated!");
				genericResponse.setStatus(ResponseStatus.success.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.update.getValue());
				String action = "update log";
//				String name = request.getFirstName() + " " + request.getLastName();
//				GenerateStaffProviderLogs(id, name, httpRequest, action); // method to create staff provider audit log
				// while updating providers
				return genericResponse;
			}
		} catch (Exception e) {
			logger.error("*****exception in providerController.updateProvider*****", e);
			genericResponse.setMessage("error!");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
			return genericResponse;
		}
		return genericResponse;

	}

	@GetMapping(value = "/v1/{id}")
	@ResponseBody
	@ApiOperation(value = "get staff by id")
	@ApiResponses(value = {

			@ApiResponse(code = 200, message = "get staff by id"),

			@ApiResponse(code = 500, message = "error"), })
	public GenericResponseList<Object> getProviderById(@PathVariable("id") String id) {

		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
		try {
			Customer provider = staffUseCase.getById(id);
			if (provider != null) {
				genericResponse.setMessage("Staff found!");
				genericResponse.setStatus(ResponseStatus.success.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
				genericResponse.setContent(provider);
				return genericResponse;
			} else {
				genericResponse.setMessage("staff not found!");
				genericResponse.setStatus(ResponseStatus.success.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());
				return genericResponse;
			}

		} catch (Exception e) {
			logger.error("*****exception in StaffController.getBYId*****", e);
			genericResponse.setMessage("error!");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
			return genericResponse;
		}

	}

	@PostMapping(value = "/v1/all")
	@ResponseBody
	@ApiOperation(value = "get all providers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get all providers"),
			@ApiResponse(code = 500, message = ERROR), })
	// @CircuitBreaker(name = MAIN_SERVICE, fallbackMethod =
	// "getUserProfileByActiveStatusFallBack")
	public GenericResponseList<Object> getAllProviders(@Valid @RequestBody GetAllStaffRequest request) {
		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
		try {
			GetStaffResponse response = staffUseCase.getALLProviders(request.getFilter(), request.getPagination());
			if (response != null && response.getProvider() != null) {
				genericResponse.setMessage("staff found!");
				genericResponse.setStatus(ResponseStatus.success.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
				genericResponse.setContent(response.getProvider());
				genericResponse.setTotalElements(response.getTotalRecords().longValue());
				return genericResponse;
			} else {
				genericResponse.setMessage("staff not found!");
				genericResponse.setStatus(ResponseStatus.success.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());
				return genericResponse;
			}

		} catch (Exception e) {
			logger.error("*****exception in stafController.getAllSatff*****", e);
			genericResponse.setMessage("error!");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
			return genericResponse;
		}

	}

	@DeleteMapping(value = "/v1/delete")
	@ResponseBody
	@ApiOperation(value = "delete staff")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "delete provider"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> deleteprovider(@RequestParam String ids, @Context HttpServletRequest httpRequest) {

		GenericResponse<Object> genericResponse = new GenericResponse<>();
		try {
			if (ids != null) {
				Customer provider = staffUseCase.delete(ids);

//				microServiceCallUtil.deleteUserProfile(provider);
				List<String> resultStrList = userProfileUseCase
						.changeUserProfileStatus(Arrays.asList(Long.valueOf(provider.getId())));

				genericResponse.setMessage("provider deleted!");
				genericResponse.setStatus(ResponseStatus.success.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//				String action = "delete log";
//				String name = provider.getFirstName() + " " + provider.getLastName();
//				GenerateStaffProviderLogs(ids, name, httpRequest, action); // method to create staff provider audit log
				// while deleting providers
				return genericResponse;
			} else {
				genericResponse.setMessage("No data found");
				genericResponse.setStatus(ResponseStatus.datanotExists.toString());
				genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());
				return genericResponse;
			}
		} catch (Exception e) {
			logger.error("*****exception in providerController.deleteProvider*****", e);
			genericResponse.setMessage("error!");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
			return genericResponse;
		}

	}

	private UserProfile saveUserProfile(AddStaffRequest request, String id) throws Exception {
		Facility facility = null;
		UserProfile user = new UserProfile();
		user.setProviderId(Long.parseLong(id));
		if (request.isProviderOrNonprovider()) {
			user.setProviderType("provider");
			user.setUserType("provider");

		} else {
			user.setProviderType("nonprovider");
			if (request.getIsResource() != null && request.getIsResource()) {

				user.setUserType("Resource");
			} else {
				user.setUserType("nonProvider");

			}
		}
		user.setEmailId(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		user.setPwd(encodedPassword);
		user.setUserName(request.getUsername());
		user.setStatus(Status.ACTIVE);
//		if (request.getPrimaryFacility() != null && !request.getPrimaryFacility().equals(""))
//			facility = getFacilityUseCase.getFacilityById(Long.parseLong(request.getPrimaryFacility()));
//		if (facility != null) {
//			user.setFacilityId(Long.parseLong(facility.getId()));
//			user.setFacilityName(facility.getName());
//			logger.info(facility.getName());
//		}
		return userProfileUseCase.addUserProfile(user);

	}

	private boolean checkUserNameExistOrNot(String username) {
		UserProfile byName = userProfileUseCase.getByName(username);
		if (byName != null) {
			return true;
		}
		return false;
	}

//	private boolean checkIfExists(AddProviderRequest request) throws RestClientException, IOException {
//		String modUrl = existsUrl.replace("{username}", request.getUsername());
//		String obj = microServiceCallUtil.postApiResponseData(new UserProfile(), modUrl, false, request.getUsername(),
//				true);
//		return Boolean.parseBoolean(Objects.nonNull(obj) ? obj : "");
//	}
}
