package com.clinistats.helpdesk.api.statff;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mapstruct.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.clinistats.helpdesk.constatnts.ResponseStatus;
import com.clinistats.helpdesk.constatnts.ResponseStatusConstants;
import com.clinistats.helpdesk.domain.Facility;
import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.exception.NoSuchElementException;
import com.clinistats.helpdesk.exception.ValidationException;
import com.clinistats.helpdesk.mapper.FacilityMapper;
import com.clinistats.helpdesk.request.GetFacilityRequest;
import com.clinistats.helpdesk.request.UpdateFacilityRequest;
import com.clinistats.helpdesk.response.GenericResponse;
import com.clinistats.helpdesk.response.GenericResponseList;
import com.clinistats.helpdesk.response.GetFacilityResponse;
import com.clinistats.helpdesk.services.interfaces.FacilityUseCase;
import com.clinistats.hepdesk.util.CommonUtil;
import com.clinistats.hepdesk.util.JwtTokenUtil;
import com.clinistats.hepdesk.util.ResponseInterface;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FacilityController {

	@Autowired
	private FacilityUseCase facilityUseCase;

	@Autowired
	private FacilityMapper facilityMapper;

//	@Autowired
//	private UpdateFacilityUseCase updateFacilityUseCase;

//	@Autowired
//	private DeleteFacilityUseCase deleteFacilityUseCase;

//	@Autowired
//	private GetFacilityUseCase getFacilityUseCase;

//	@Autowired
//	private GetAllFacilitiesIdsNamesUseCase getAllFacilitiesIdsNamesUseCase;

//	@Autowired
//	private GetFacilityByIdAndNamesUseCaseImpl getFacilityByIdAndNamesUseCase;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CommonUtil commonUtil;

	private String[] validImageFileTypes = { "jpg", "jpeg", "JPG", "JPEG", "pdf", "PDF", "png", "PNG" };

	private static final String Facilty_Found = "Facility found!";
	private static final String Facility_not_found = "Facility not found!";

	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);

	@PostMapping(value = "/v1/facility")
	@ResponseBody
	@ApiOperation(value = "create facility", consumes = MediaType.IMAGE_JPEG_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "create facility"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> add(@Valid @RequestBody UpdateFacilityRequest request,
			HttpServletRequest httpRequest) throws IOException {

		GenericResponse<Object> createResponse = new GenericResponse<>();

		if (request.getStatus() == null) {
			request.setStatus(Status.ACTIVE);
		}

		Facility unSavedDomain = facilityMapper.toDomain(request);

		String id = facilityUseCase.addFacility(unSavedDomain);
		if (id != null) {
			createResponse.setContent(id);
			createResponse.setMessage("Facility Created!");
			createResponse.setStatus(ResponseStatus.success.toString());
			createResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//			String action = "create facility log";
//			String facilityName = request.getName();
//			String facilityType = request.getType();
//			GenerateFacilityLogs(id, facilityName, facilityType, httpRequest, action); // method called to add facility
			// audit logs, while creating
			// facility
			return createResponse;
		} else {
			logger.error("Error in creating facility ");
			createResponse.setContent(null);
			createResponse.setMessage("facility creation failed");
			createResponse.setStatus(ResponseStatus.error.toString());
			createResponse.setStatusCode(ResponseStatusConstants.error.getValue());
			return createResponse;
		}

	}

	@PutMapping(value = "/v1/facility/update")
	@ResponseBody
	@ApiOperation(value = "update facility")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "update facility"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> updateFacility(@Valid @RequestBody UpdateFacilityRequest request,
			@Context HttpServletRequest httpRequest) throws IOException {

		GenericResponse<Object> genericResponse = new GenericResponse<>();

//		UpdateFacilityRequest request = mapper.readValue(metaData, UpdateFacilityRequest.class); // converted metadata
		// into request
		Facility facilityObj = facilityUseCase.getFacilityById(Long.valueOf(request.getId()));
		if (!request.getName().equalsIgnoreCase(facilityObj.getName())) {
			Long countByName = facilityUseCase.countByName(request.getName());
			if (countByName > 0)
				throw new ValidationException("Facility " + request.getName() + " is already exists");
		}
		if (!request.getPhone().equalsIgnoreCase(facilityObj.getPhone())) {
			Long countByPhone = facilityUseCase.countByPhone(request.getPhone());
			if (countByPhone > 0)
				throw new ValidationException(request.getPhone() + " phone number is already exists");
		}

		/*
		 * if (file == null) { Facility facility =
		 * getFacilityUseCase.getFacilityById(Long.valueOf(request.getId()));
		 * //restoring old url when updating only facilities but not the image
		 * facilityImageUrl = facility.getFacilityImageurl(); }
		 */
		/*
		 * else { facilityImageUrl = uploadAttachment(file); }
		 */
		Facility unSavedDomain = facilityMapper.toDomain(request);
		unSavedDomain.setFacilityImageurl(facilityObj.getFacilityImageurl());
		String id = facilityUseCase.updateFacility(unSavedDomain);

		if (id != null && !id.equalsIgnoreCase("Cannot Update")) {
			genericResponse.setContent(id);
			genericResponse.setMessage("Facility updated!");
			genericResponse.setStatus(ResponseStatus.success.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.update.getValue());
//			String action = "update facility log";
//			String facilityName = request.getName();
//			String facilityType = request.getType();
//			GenerateFacilityLogs(id, facilityName, facilityType, httpRequest, action); // method called to add facility
			// audit logs, while updating
			// facility
		}

		if (id != null && id.equalsIgnoreCase("Cannot Update")) {
			genericResponse.setMessage("Cannot Update Facility ");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
		}

		if (id != null && id.equalsIgnoreCase("Cannot Find Facility with Id ")) {
			genericResponse.setMessage("Error in Updating Facility ");
			genericResponse.setStatus(ResponseStatus.error.toString());
			genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
		}
		return genericResponse;
	}

//
//	@PostMapping(value = "/v1/facility/image") // seperate api to upload facility image
//	@ResponseBody
//	@ApiOperation(value = "upload facility image", consumes = MediaType.IMAGE_JPEG_VALUE)
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "upload facility image"),
//			@ApiResponse(code = 500, message = "error"), })
//	public GenericResponse<Object> uploadFacilityImage(@RequestPart("file") MultipartFile file,
//			@RequestParam("id") String id) {
//
//		GenericResponse<Object> genericResponse = new GenericResponse<>();
//
//		if (file != null) {
//			boolean valid = FileUtility.isValidFileType(file, validImageFileTypes);
//			if (!valid) {
//				ResponseInterface.errorResponse("Invalid image file extension !");
//				genericResponse.setMessage("Invalid image file extension !");
//				return genericResponse;
//			}
//		}
//
//		String res = null;
//
//		try {
//			if (id != null)
//				res = addFacilityUseCase.uploadFacilityImage(file, id);
//			genericResponse = GenericResponseReturn.genericResponseType(res, ResponseStatus.success.toString(),
//					ResponseStatusConstants.update.getValue());
//			return genericResponse;
//		} catch (Exception e) {
//			logger.error("*****exception in FacilityController.uploadFacilityImage*****", e);
//			genericResponse = GenericResponseReturn.genericResponseType("error", ResponseStatus.error.toString(),
//					ResponseStatusConstants.error.getValue());
//			return genericResponse;
//		}
//
//	}
//
//	@GetMapping(value = "/v1/facility/image/{id}") // seperate api to retriew facility image by id
//	@ResponseBody
//	@ApiOperation(value = "get facility image by id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "get facility image by id"),
//			@ApiResponse(code = 500, message = "error"), })
//	public ResponseEntity<InputStreamResource> getFacilityImageById(@PathVariable String id) {
//		logger.info("facilitycontroller,  getFacilityImageById, facilityId=" + id);
//		ResponseEntity<InputStreamResource> retId = getFacilityUseCase.getFacilityImageById(id);
//		GenericResponse<Object> createResponse = ResponseUtil
//				.createResponse("get facility image by id is successfully");
//		if (retId != null)
//			createResponse.setContent(new CreateResponse("Success"));
//		else
//			createResponse.setContent(new CreateResponse("Failed"));
//		return retId;
//	}
//
	@GetMapping(value = "/v1/facility/{id}")
	@ResponseBody
	@ApiOperation(value = "get facility by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get facility by id"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> getFacilityById(@PathVariable("id") int id) {
		GenericResponse<Object> genericResponse = new GenericResponse<>();
		Facility facility = facilityUseCase.getFacilityById(id);
		if (facility == null) {
			logger.info("EHR-UE-FACILITY-1002: "
					+ messageSource.getMessage("facility.facility_not_found_1002", null, Locale.ENGLISH));
			throw new NoSuchElementException(
					messageSource.getMessage("facility.facility_not_found_1002", null, Locale.ENGLISH),
					"EHR-UE-FACILITY-1002");
		}

		genericResponse.setMessage(Facilty_Found);
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
		genericResponse.setContent(facility);
		return genericResponse;

	}

//
//	@GetMapping(value = "/v1/facility/list/{ids}")
//	@ResponseBody
//	@ApiOperation(value = "get facility ids and names by id list")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "get facility ids and names by id list"),
//			@ApiResponse(code = 500, message = "error"), })
//	public GenericResponseList<Object> getFacilityByIdAndNames(@PathVariable String ids) {
//		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
//		List<GetIdNameDto> facilities = getFacilityByIdAndNamesUseCase.getFacilityByIdAndNames(ids);
//		if (facilities != null && !facilities.isEmpty()) {
//			genericResponse = GenericResponseReturn.genericResponseListType(Facilty_Found,
//					ResponseStatus.success.toString(), ResponseStatusConstants.success.getValue(),
//					(long) facilities.size());
//			genericResponse.setContent(facilities);
//			return genericResponse;
//		} else {
//			genericResponse = GenericResponseReturn.genericResponseListType(Facility_not_found,
//					ResponseStatus.success.toString(), ResponseStatusConstants.dataNotExists.getValue(), 0);
//			return genericResponse;
//		}
//	}
//
	@DeleteMapping(value = "/v1/facility/delete")
	@ResponseBody
	@ApiOperation(value = "delete facility by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "delete facility by id"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> deleteFacilityById(@RequestParam String id,
			@Context HttpServletRequest httpRequest) {
		GenericResponse<Object> genericResponse = new GenericResponse<>();
		facilityUseCase.deleteFacilityById(id);

		genericResponse.setMessage(id + " is deleted");
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//		String action = "delete facility log";
//		String facilityName = deletedFacility.getName();
//		String facilityType = deletedFacility.getType();
//		GenerateFacilityLogs(id, facilityName, facilityType, httpRequest, action); // method called to add facility
		// audit logs, while deleting
		// facility
		return genericResponse;
	}

//	@GetMapping(value = "/v1/all_facilityIds")
//	@ResponseBody
//	@ApiOperation(value = "get all facilitiesIds")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "get all facilitiesIds"),
//			@ApiResponse(code = 500, message = "error"), })
//	public GenericResponseList<Object> getAllFacilitiesIdsAndNames() {
//
//		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
//		try {
//			List<Object[]> facilities = getAllFacilitiesIdsNamesUseCase.getAllFacilitiesIds();
//			if (facilities != null && !facilities.isEmpty()) {
//				genericResponse = GenericResponseReturn.genericResponseListType(Facilty_Found,
//						ResponseStatus.success.toString(), ResponseStatusConstants.success.getValue(),
//						(long) facilities.size());
//				genericResponse.setContent(facilities);
//				return genericResponse;
//			} else {
//				genericResponse = GenericResponseReturn.genericResponseListType(Facility_not_found,
//						ResponseStatus.success.toString(), ResponseStatusConstants.dataNotExists.getValue(), 0);
//				return genericResponse;
//			}
//
//		} catch (Exception e) {
//			logger.error("*****exception in FacilityController.getAllFacilitiesIdsAndNames***", e);
//			genericResponse = GenericResponseReturn.genericResponseListType("error!", ResponseStatus.error.toString(),
//					ResponseStatusConstants.error.getValue(), 0);
//			return genericResponse;
//		}
//
//	}

	@PostMapping(value = "/v1/facility/all")
	@ResponseBody
	@ApiOperation(value = "get all facilities")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get all facilities"),
			@ApiResponse(code = 404, message = "not found"), })
	public GenericResponseList<Object> getByPaginationAndFilter(@Valid @RequestBody GetFacilityRequest request) {
		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
		try {
			GetFacilityResponse response = facilityUseCase.getFacility(request.getFilter(), request.getPagination());

			if (response != null && response.getTotalRecords() > 0) {
				genericResponse = ResponseInterface.successListResponse(response.getFacility(),
						Long.valueOf(response.getTotalRecords()), Facilty_Found);
				genericResponse.setContent(response.getFacility());
				return genericResponse;
			} else {
				genericResponse = ResponseInterface.dataNotFoundListResponse(Facility_not_found);
				return genericResponse;
			}

		} catch (Exception e) {
			logger.error("*****exception in FacilityController.getByPaginationAndFilter*****", e);
			genericResponse = ResponseInterface.errorListResponse("error!");
			return genericResponse;
		}

	}

//	@PostMapping(value = "/v1/facility/list/search")
//	@ResponseBody
//	@ApiOperation(value = "get facility list by search")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "get facility list by search"),
//			@ApiResponse(code = 404, message = "not found"), })
//	public GenericResponseList<Object> getFacilityIDNamesListBySearch(@Valid @RequestBody FacilityFilter request,
//			HttpServletRequest webRequest) {
//
//		GenericResponseList<Object> genericResponse = new GenericResponseList<>();
//		try {
//			List<Long> assigedFacilites = commonUtil.getAssigedFacilites(webRequest);
//			if (CollectionUtils.isNotEmpty(assigedFacilites)) {
//				List<GetIdNameColor> idsList = getFacilityUseCase.getFacilityIDNamesListBySearch(request,
//						assigedFacilites);
//
//				if (idsList != null && !idsList.isEmpty()) {
//					genericResponse = GenericResponseReturn.genericResponseListType(Facilty_Found,
//							ResponseStatus.success.toString(), ResponseStatusConstants.success.getValue(),
//							idsList.size());
//					genericResponse.setContent(idsList);
//					return genericResponse;
//				}
//			}
////			else {
////				List<GetIdNameColor> idsList = getFacilityUseCase.getFacilityIDNamesListBySearch(request);
////
////				if (idsList != null && !idsList.isEmpty()) {
////					genericResponse = GenericResponseReturn.genericResponseListType(Facilty_Found,
////							ResponseStatus.success.toString(), ResponseStatusConstants.success.getValue(),
////							(long) idsList.size());
////					genericResponse.setContent(idsList);
////					return genericResponse;
////				}
////			}
//			genericResponse = GenericResponseReturn.genericResponseListType(Facility_not_found,
//					ResponseStatus.success.toString(), ResponseStatusConstants.dataNotExists.getValue(), 0);
//			return genericResponse;
//		} catch (Exception e) {
//			logger.error("*****exception in FacilityController.getByPaginationAndFilter*****", e);
//			genericResponse = GenericResponseReturn.genericResponseListType("error!", ResponseStatus.error.toString(),
//					ResponseStatusConstants.error.getValue(), 0);
//			return genericResponse;
//		}
//	}
//
//	private void GenerateFacilityLogs(String id, String facilityName, String facilityType,
//			HttpServletRequest httpRequest, String action) {
//		FacilityAuditLogRequest facilityAuditRequest = new FacilityAuditLogRequest();
//		facilityAuditRequest.setFacilityId(Long.valueOf(id));
//		facilityAuditRequest.setCreatedBy(getLoggedInUserName(httpRequest));
//		facilityAuditRequest.setDate(getTodaysDate());
//		facilityAuditRequest.setTime(currentTime());
//		facilityAuditRequest.setFacilityName(facilityName);
//		facilityAuditRequest.setFacilityType(facilityType);
//		if (action.equalsIgnoreCase("create facility log")) {
//			facilityAuditRequest.setAction(FacilityAuditLog.FACILITY_CREATED.toString());
//		}
//		if (action.equalsIgnoreCase("update facility log")) {
//			facilityAuditRequest.setAction(FacilityAuditLog.FACILITY_UPDATED.toString());
//		}
//		if (action.equalsIgnoreCase("delete facility log")) {
//			facilityAuditRequest.setAction(FacilityAuditLog.FACILITY_DELETED.toString());
//		}
//		FacilityAuditLogDomain facilityAuditdomain = requestFacilityMapper.toFacilityDomain(facilityAuditRequest);
//
//		addFacilityUseCase.generateFacility(facilityAuditdomain);
//	}
//
//	private String getTodaysDate() {
//		LocalDate date = LocalDate.now();
//		String datetime = date.toString();
//		return datetime.substring(0, 10);
//	}
//
//	private String currentTime() {
//		DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
//		return dateFormat.format(new Date()).toString();
//	}
//
//	private String getLoggedInUserName(HttpServletRequest httpRequest) {
//		String requestTokenHeader = httpRequest.getHeader("Authorization");
//		String username = null;
//		String jwtToken = null;
//
//		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//				logger.info("######### CustomFilterController.JWT username:" + username);
//			} catch (IllegalArgumentException e) {
//				logger.error("#########  CustomFilterController.Unable to get JWT Token");
//			} catch (ExpiredJwtException e) {
//				logger.error("######### CustomFilterController.JWT Token has expired");
//			}
//		} else {
//			logger.warn("######### CustomFilterController.JWT Token does not begin with Bearer String");
//		}
//		return username;
//	}

}
