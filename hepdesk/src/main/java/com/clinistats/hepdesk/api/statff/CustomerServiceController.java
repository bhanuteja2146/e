package com.clinistats.hepdesk.api.statff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clinistats.hepdesk.constatnts.MailBoxType;
import com.clinistats.hepdesk.constatnts.ResponseStatus;
import com.clinistats.hepdesk.constatnts.ResponseStatusConstants;
import com.clinistats.hepdesk.domain.TicketRaiseDomain;
import com.clinistats.hepdesk.domain.TicketResponseContent;
import com.clinistats.hepdesk.domain.Customer;
import com.clinistats.hepdesk.mapper.PortalMessageRequestMapper;
import com.clinistats.hepdesk.request.AddPortalMessageRequest;
import com.clinistats.hepdesk.request.GetPatientPortalRequest;
import com.clinistats.hepdesk.request.UpdateObjectRequest;
import com.clinistats.hepdesk.response.GenericResponse;
import com.clinistats.hepdesk.response.GenericResponseList;
import com.clinistats.hepdesk.response.GetIdName;
import com.clinistats.hepdesk.response.GetPatientPortalMsgResponse;
import com.clinistats.hepdesk.response.PortalMessageListResponse;
import com.clinistats.hepdesk.s3.S3Client;
import com.clinistats.hepdesk.s3.S3FileUpload;
import com.clinistats.hepdesk.services.interfaces.CustomerServiceUseCase;
import com.clinistats.hepdesk.services.interfaces.PortalMessageUseCase;
import com.clinistats.hepdesk.services.interfaces.StaffUseCase;
import com.clinistats.hepdesk.util.FileUtility;
import com.clinistats.hepdesk.util.JwtTokenUtil;
import com.clinistats.hepdesk.util.ResponseInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/support")
public class CustomerServiceController {

	@Autowired
	private CustomerServiceUseCase customerServiceUseCase;

	@Autowired
	private StaffUseCase staffUseCase;

	@Autowired
	private PortalMessageRequestMapper patientPortalMsgRequestMapper;

	@Autowired
	private PortalMessageUseCase portalMessageUseCase;

//	@Value("${entity.getonefileuri}")
//	private String getOneFileUrl;
//
//	@Value("${doc.servicename}")
//	private String docServiceName;
//
//	@Value("${entity.patienturi}")
//	private String patientUrl;
//
//	@Value("${app.servicename}")
//	private String appServiceName;
//
//	@Value("${entity.provideruri}")
//	private String providerUrl;
//
//	@Value("${entity.providerNameuri}")
//	private String providerNameuri;
//
//	@Value("${entity.addprimarynotificationuri}")
//	private String addPrimaryNotificationUri;

//	@Autowired
//	private MicroServiceCallUtil microServiceCallUtil;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private S3Client s3Client;

	private String[] validImageFileTypes = { "jpg", "jpeg", "JPG", "JPEG", "pdf", "PDF", "png", "PNG" };

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private static final String ERROR = "error";
	private static final String NOT_FOUND = "Patient Portal Message not found!";

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);

	@PostMapping(value = "/v1/ticket", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	@ApiOperation(value = "create portal message")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "create portal message"),
			@ApiResponse(code = 500, message = ERROR), })
	// public GenericResponse<Object> add(@Valid @RequestBody
	// AddPortalMessageRequest request, @Context HttpServletRequest httpRequest)
	public GenericResponse<Object> add(@RequestParam(name = "files", required = false) MultipartFile[] attachements,
			@RequestParam("data") String request, @Context HttpServletRequest httpRequest) throws IOException {
		AddPortalMessageRequest addPortalMessageRequest = mapper.readValue(request, AddPortalMessageRequest.class);
		List<String> imageS3UrlList = new ArrayList<String>();

		if (attachements != null) {

			boolean valid = FileUtility.isValidFileTypes(attachements, validImageFileTypes);
			if (!valid)
				return ResponseInterface.errorResponse("Invalid image file extension !");

			for (int i = 0; i < attachements.length; i++) {
				String url = uploadAttachment(attachements[i]);

				imageS3UrlList.add(url);
			}
		}

		addPortalMessageRequest.setAttachmentUrl(imageS3UrlList);

		TicketRaiseDomain portalMessage = patientPortalMsgRequestMapper.toPortalMessage(addPortalMessageRequest);
		String contentId = addPortalMessageRequest.getPortalMessageContentId();
		System.out.println("##############c  content id=" + contentId);
		if (contentId == null || "".equals(contentId) || "0".equals(contentId)) {
			TicketResponseContent portalMessageContent = TicketResponseContent.builder()
					.id(addPortalMessageRequest.getPortalMessageContentId())
//					.(addPortalMessageRequest.getMessageReceiverId())
					.content(addPortalMessageRequest.getTextContent()).build();

			TicketResponseContent savedContentId = portalMessageUseCase.addPortalMessageContent(portalMessageContent);
			contentId = savedContentId.getId();
		}

		System.out.println("PatientPortalMsg::add, input content id=" + contentId);

		List<TicketResponseContent> patientPortalMsgContentList = new ArrayList<>(0);

		TicketResponseContent content = TicketResponseContent.builder()
				.id(addPortalMessageRequest.getPortalMessageContentId())
//				.patientId(addPortalMessageRequest.getPatientId())
				.content(addPortalMessageRequest.getTextContent()).build();

		content.setId(contentId);

		patientPortalMsgContentList.add(0, content);
		System.out.println("  patientPortalMsgContentList size::>>>>>" + patientPortalMsgContentList.size());

		portalMessage.setPatientPortalMsgContentList(patientPortalMsgContentList);

		String userName = getLoggedInUserName(httpRequest);
		portalMessage.setCreatedBy(userName);
		portalMessage.setMailBoxType(MailBoxType.OUTBOX);

		TicketRaiseDomain portlMsg = customerServiceUseCase.addPatientPortalMsg(portalMessage);
//		sendNotification(portlMsg);
		if (portlMsg != null) {

			return ResponseInterface.successResponse(portlMsg, "New ticket raised successfully");
		} else {
			return ResponseInterface.errorResponse(ERROR);
		}
	}

	private String uploadAttachment(MultipartFile attachmnt) throws IOException {
		if (attachmnt == null)
			return null;
		String url = S3FileUpload.uploadFile(attachmnt, s3Client);
		logger.info("Attachmnt uploaded and url = " + url);
		return url;
	}

//	private void sendNotification(PatientPortalMsg portlMsg) {
//		if (portlMsg != null) {
//			Notification not = new Notification();
//			not.setType("patient portal message");
//			not.setProviderId(String.valueOf(portlMsg.getProviderId()));
////				not.setNonProviderId(String.valueOf(task.getPrimaryNonProviderId()));
//			not.setUniqueIdentifier(String.valueOf(portlMsg.getId()));
//			not.setMarkAsRead(false);
//			microServiceCallUtil.postData(docServiceName, addPrimaryNotificationUri, not);
//		}
//	}

	@GetMapping(value = "/v1/ticket/{id}")
	@ResponseBody
	@ApiOperation(value = "get rasised ticket by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get patient-portal-msg by id"),
			@ApiResponse(code = 500, message = ERROR), })
	public GenericResponse<Object> getPatientPortalMsgById(@PathVariable("id") Long id) {
		TicketRaiseDomain patientPortalMessage = customerServiceUseCase.getPatientPortalMsgById(id);
		// PortalMessage portalMessagegetPatientPortalMsgById
		// =getPortalMessageUseCase.getPortalMessageById(id);
		if (patientPortalMessage != null) {
			Collections.reverse(patientPortalMessage.getPatientPortalMsgContentList());
			logger.info("PatientPortalMsgController::getPatientPortalMsgById, portalMessage id="
					+ patientPortalMessage.getId());
			logger.info("PatientPortalMsgController::getPatientPortalMsgById, portalMessage List size:"
					+ patientPortalMessage.getPatientPortalMsgContentList().size());

			return ResponseInterface.successResponse(patientPortalMessage, "Raised ticked found");
		}
		return ResponseInterface.errorResponse("Error");
	}

	@PutMapping(value = "/v1/ticket/softdelete")
	@ResponseBody
	@ApiOperation(value = "soft delete ticket")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "soft delete patient-portal-msg"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> disblePatientPortalMsg(@RequestBody List<UpdateObjectRequest> request) {

		GenericResponse<Object> genericResponse = new GenericResponse<>();

		List<String> resultStrList = customerServiceUseCase
				.changePatientPortalMsg(patientPortalMsgRequestMapper.toDomains(request));

		genericResponse.setMessage("Raised ticket disabled/enabled !");
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
		genericResponse.setContent(resultStrList);
		return genericResponse;
	}

	@PostMapping(value = "/v1/ticket/all")
	@ResponseBody
	@ApiOperation(value = "get raised tickets with filters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get patient-portal-msg with filters"),
			@ApiResponse(code = 404, message = "not found"), @ApiResponse(code = 500, message = "not found"), })

	public GenericResponseList<Object> getPatientPortalMsgByPaginationAndFilter(
			@Valid @RequestBody GetPatientPortalRequest request, @Context HttpServletRequest httpRequest) {

		GetPatientPortalMsgResponse responses = customerServiceUseCase
				.getAllPatientPortalMsgs(request.getPatientPortalFilter(), request.getPagination());
		List<PortalMessageListResponse> portalMsgeResponseLst = new ArrayList<>();

		if (responses != null && responses.getTotalRecords() > 0) {
			for (TicketRaiseDomain portalMessage : responses.getPatientPortalMsgs()) {
//				BasicDetailsResponse patientDetails = null;
				PortalMessageListResponse portalMessageListResponse = new PortalMessageListResponse();
				portalMessageListResponse.setId(portalMessage.getId());
				portalMessageListResponse.setSentBy(portalMessage.getSentBy());
				portalMessageListResponse.setSentDate(portalMessage.getSentDate());

				String provider = portalMessage.getProviderId();
				if (!StringUtils.isBlank(provider)) {
					GetIdName providerDetails = new GetIdName();
					Customer byId = staffUseCase.getById(portalMessage.getProviderId());

					providerDetails.setName(byId.getName());
					providerDetails.setId(Long.valueOf(byId.getId()));
					portalMessageListResponse.setProvider(providerDetails);
				}

//				String userName = getLoggedInUserName(httpRequest);

				portalMessageListResponse.setMailBoxType(portalMessage.getMailBoxType());
//				attachmentSize = portalMessage.getAttachmentUrl().size();
				if (portalMessage.getAttachmentUrl() != null) {
					portalMessageListResponse.setAttachments(portalMessage.getAttachmentUrl().size());

				}

				portalMsgeResponseLst.add(portalMessageListResponse);
			}

			return ResponseInterface.successListResponse(portalMsgeResponseLst, responses.getTotalRecords(),
					"Tickets founds");
		}

		return ResponseInterface.dataNotFoundListResponse(NOT_FOUND);
	}

	private String getLoggedInUserName(HttpServletRequest httpRequest) {
		String requestTokenHeader = httpRequest.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {

				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				logger.info("######### PatientPortalMsgController.JWT username:" + username);
			} catch (IllegalArgumentException e) {
				logger.error("#########  PatientPortalMsgController.Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.error("######### PatientPortalMsgController.JWT Token has expired");
			}
		} else {
			logger.warn("######### PatientPortalMsgController.JWT Token does not begin with Bearer String");
		}
		return username;
	}
}
