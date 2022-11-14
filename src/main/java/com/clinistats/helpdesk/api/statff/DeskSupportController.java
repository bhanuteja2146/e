package com.clinistats.helpdesk.api.statff;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mapstruct.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.clinistats.helpdesk.constatnts.MailBoxType;
import com.clinistats.helpdesk.constatnts.ResponseStatus;
import com.clinistats.helpdesk.constatnts.ResponseStatusConstants;
import com.clinistats.helpdesk.domain.TicketResponse;
import com.clinistats.helpdesk.domain.TicketResponseContent;
import com.clinistats.helpdesk.mapper.PortalMessageRequestMapper;
import com.clinistats.helpdesk.request.AddPortalMessageRequest;
import com.clinistats.helpdesk.request.GetPortalMessageRequest;
import com.clinistats.helpdesk.request.UpdateObjectRequest;
import com.clinistats.helpdesk.response.GenericResponse;
import com.clinistats.helpdesk.response.GenericResponseList;
import com.clinistats.helpdesk.response.GetPortalMessageResponse;
import com.clinistats.helpdesk.response.PortalMessageListResponse;
import com.clinistats.helpdesk.s3.S3Client;
import com.clinistats.helpdesk.s3.S3FileUpload;
import com.clinistats.helpdesk.services.interfaces.PortalMessageUseCase;
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
public class DeskSupportController {
	@Autowired
	private PortalMessageUseCase portalMessageUseCase;

//	@Autowired	
//	private GetPatientPortalMsgUseCase getPatientPortalMsgUseCase;

//	@Autowired
//	private GetAllPortalMessageUseCase getAllPortalMessageUseCase;

//	@Autowired
//	private DeletePortalMessageUseCase deletePortalMessageUseCase;

	@Autowired
	private PortalMessageRequestMapper portalMessageRequestMapper;

//	@Autowired
//	private AddPortalMessageContentUseCase addPortalMessageContentUseCase;

//	@Autowired
//	private GetDIReviewSoapUseCase getDIReviewSoapUseCase;
//	
//	@Autowired
//	private GetLabReviewSoapUseCase getLabReviewSoapUseCase;
//	
//	@Autowired
//	private AddPatientPortalMsgUseCase addPatientPortalMsgUseCase;

//	@Value("${entity.getonefileuri}")
//	private String getOneFileUrl;
//
//	@Value("${doc.servicename}")
//	private String docServiceName;
//
//	@Value("${entity.patienturi}")
//	private String patientUrl;
//
//	@Value("${entity.provideruri}")
//	private String providerUrl;
//
//	@Value("${app.servicename}")
//	private String appServiceName;

//	@Autowired
//	private MicroServiceCallUtil microServiceCallUtil;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private S3Client s3Client;

//	@Autowired
//	private ExternalServiceCallUtil externalServiceCallUtil;

	private String[] validImageFileTypes = { "jpg", "jpeg", "JPG", "JPEG", "pdf", "PDF", "png", "PNG" };

	private static final String ERROR = "error";
	private static final String NOT_FOUND = "Portal Message not found!";

	private static final Logger logger = LoggerFactory.getLogger(DeskSupportController.class);

	@PostMapping(value = "/v1/ticket/solution")
	@ResponseBody
	@ApiOperation(value = "send ticket solutions")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "send ticket solutions"),
			@ApiResponse(code = 500, message = ERROR), })
	public GenericResponse<Object> add(@RequestParam(name = "files", required = false) MultipartFile[] attachements,
			@RequestParam("data") String addPortalMessageRequest, @Context HttpServletRequest httpRequest)
			throws IOException {
		AddPortalMessageRequest request = mapper.readValue(addPortalMessageRequest, AddPortalMessageRequest.class);
		String userName = getLoggedInUserName(httpRequest);

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

//		if(request.getLabAttachmentIds() != null && !request.getLabAttachmentIds().isEmpty()) {
//		InputStream responseData = microServiceCallUtil.getOneResponseresource(docServiceName, "/api/doc-management/v1/doc-record/get-file/",request.getLabAttachmentIds());
//		if(responseData != null) {
//			String url = uploadiInputStreamFile(responseData);
//			request.setLabAttachmentUrl(url);
//			System.out.println("##############c lab content url="+url);
//		}
//		}

//		if(request.getDiAttachmentIds() != null && !request.getDiAttachmentIds().isEmpty()) {
//		InputStream responsedData = microServiceCallUtil.getOneResponseresource(docServiceName, "/api/doc-management/v1/doc-record/get-file/",request.getDiAttachmentIds());
//		if(responsedData != null) {
//			String url = uploadiInputStreamFile(responsedData);
//			request.setDiAttachmentUrl(url);
//			System.out.println("##############c di content url="+url);
//		}
//		}

		request.setAttachmentUrl(imageS3UrlList);

		TicketResponse portalMessage = portalMessageRequestMapper.toAddDomain(request);
		String contentId = request.getPortalMessageContentId();
		System.out.println("##############c  content id=" + contentId);
		if (contentId == null || "".equals(contentId) || "0".equals(contentId)) {
			TicketResponseContent portalMessageContent = TicketResponseContent.builder()
					.id(request.getPortalMessageContentId()).customerId(request.getCustomerId()).createdBy(userName)
					.createdDate(LocalDate.now()).content(request.getTextContent()).build();

			TicketResponseContent savedMessageContent = portalMessageUseCase
					.addPortalMessageContent(portalMessageContent);
			contentId = savedMessageContent.getId();
		}

		System.out.println("Virtualvisit::add, input content id=" + contentId);

		List<TicketResponseContent> portalMessageContentList = new ArrayList<>(0);

		TicketResponseContent content = TicketResponseContent.builder().id(request.getPortalMessageContentId())
				.customerId(request.getCustomerId()).content(request.getTextContent()).build();

		content.setId(contentId);

		portalMessageContentList.add(0, content);
		System.out.println("  portalMessageContentList size::>>>>>" + portalMessageContentList.size());

		portalMessage.setPortalMessageContentList(portalMessageContentList);

		portalMessage.setCreatedBy(userName);
		portalMessage.setMailBoxType(MailBoxType.OUTBOX);

		TicketResponse portlMsg = portalMessageUseCase.addPortalMessage(portalMessage);
		if (portlMsg != null) {

			return ResponseInterface.successResponse(portlMsg, "Ticket response saved!!!");
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

	@GetMapping(value = "/v1/ticket/solution/{id}")
	@ResponseBody
	@ApiOperation(value = "get ticket solution by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get ticket by id"),
			@ApiResponse(code = 500, message = ERROR), })
	public GenericResponse<Object> getPortalMessageById(@PathVariable("id") Long id) {
		TicketResponse portalMessage = portalMessageUseCase.getPortalMessageById(id);
		// PatientPortalMsg patientPortalMessage =
		// getPatientPortalMsgUseCase.getPatientPortalMsgById(id);
		if (portalMessage != null) {
			Collections.reverse(portalMessage.getPortalMessageContentList());
			logger.info("PortalMessageController::getVirtualVisitById, portalMessage id=" + portalMessage.getId());
			/*
			 * ======= PatientPortalMsg patientPortalMessage =
			 * getPatientPortalMsgUseCase.getPatientPortalMsgById(id); if(portalMessage !=
			 * null) { Collections.reverse(portalMessage.getPortalMessageContentList());
			 * logger.info("PortalMessageController::getVirtualVisitById, portalMessage id="
			 * +portalMessage.getId());
			 * 
			 * >>>>>>> d45c2f273e22b61a91678640a0fe7911de24f6d7
			 * if(portalMessage.getMailBoxType().equals(MailBoxType.INBOX)) {
			 * List<PortalMessageContent> portalMsgContentList =
			 * portalMessageRequestMapper.toPortalMessageList(patientPortalMessage.
			 * getPatientPortalMsgContentList());
			 * portalMessage.setPortalMessageContentList(portalMsgContentList); <<<<<<< HEAD
			 * }
			 */

			return ResponseInterface.successResponse(portalMessage, "Portal Message retrieved successfully");
		}
		return ResponseInterface.errorResponse("Error");
	}

//	
//	@GetMapping(value = "/v1/additional-info/portal-message-attachment/{id}")
//	@ResponseBody
//	@ApiOperation(value = "get portal-message-attachment by id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "get portal-message-attachment by id"),
//			@ApiResponse(code = 500, message = ERROR), })
//	public GenericResponse<Object> getPortalMessageAttachmentById(@PathVariable("id") Long id) 
//	{
//		PortalMessage portalMessage  = getPortalMessageUseCase.getPortalMessageById(id);
//		
//		PortalMessageResponse portalMessageResponse=new PortalMessageResponse();
////		logger.info("PortalMessageController::getPortalMessageAttachmentById, portalMessage id="+portalMessage.getId());
//	String strArr =null;
////		if(!StringUtils.isBlank(portalMessage.getLabIds()))
////		{
////			List<Long> longLabIds = Arrays.asList(portalMessage.getLabIds().split(",")).stream().map(iD -> Long.parseLong(iD)).collect(Collectors.toList());
////			List<LabResponse> portalMsgeLabResponseLst = new ArrayList<>();
////			logger.info("PortalMessageController::getPortalMessageAttachmentById, longLabIds:"+longLabIds);
////			for(Long iD : longLabIds)
////			{
////			 
////				LabReviewSoap labReviewSoap=getLabReviewSoapUseCase.getById(iD); 
////				logger.info("PortalMessageController::getPortalMessageAttachmentById, labReviewSoap size:"+labReviewSoap.getId());
////				LabResponse labResponse =new LabResponse();
////				 
////				if(labReviewSoap!=null  )
////				{
////
////					labResponse.setId(labReviewSoap.getId());
////					labResponse.setLabId(labReviewSoap.getLabId());
////					labResponse.setLabName(labReviewSoap.getLabName());
////					labResponse.setLabDate(labReviewSoap.getOrderDate());
////					if( !StringUtils.isBlank(labReviewSoap.getAttachmentId()))
////					{
////						Object responseData = microServiceCallUtil.getOneResponseData(docServiceName, getOneFileUrl, labReviewSoap.getAttachmentId());
////						 
////						ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
////								false);
////						mapper.setSerializationInclusion(Include.NON_NULL);
////
////						DocRecord docRecord = mapper.convertValue(responseData, DocRecord.class);
////						if(docRecord!=null && docRecord.getDocName()!=null)
////						{
////							logger.info("PortalMessageController::getPortalMessageAttachmentById, docRecord.getDocName():"+docRecord.getDocName());
////							labResponse.setDocName(docRecord.getDocName());
////						}
////					}							 
////					portalMsgeLabResponseLst.add(labResponse);							
////				}			 
////								
////			}	
////			
////			portalMessageResponse.setPortalMessageLabResponseLst(portalMsgeLabResponseLst);
////		}
////		if(!StringUtils.isBlank(portalMessage.getDiIds()))
////		{
////			List<Long> longDiIds = Arrays.asList(portalMessage.getDiIds().split(",")).stream().map(iD -> Long.parseLong(iD)).collect(Collectors.toList());
////			List<DiResponse> portalMsgeDiResponseLst = new ArrayList<>();
////			logger.info("PortalMessageController::getPortalMessageAttachmentById, longDiIds:"+longDiIds);
////			for(Long iD : longDiIds)
////			{
////				DIReviewSoap dIRevwSoap=getDIReviewSoapUseCase.getById(iD);
////				DiResponse diResponse =new DiResponse();
////				if(dIRevwSoap!=null )
////				{
////
////					diResponse.setId(dIRevwSoap.getId());
////					diResponse.setDiId(dIRevwSoap.getDiId());
////					diResponse.setDiName(dIRevwSoap.getDiName());
////					diResponse.setDiDate(dIRevwSoap.getOrderDate());
////					
////					 if(!StringUtils.isBlank(dIRevwSoap.getAttachmentId()))
////					 {
////						 Object responseData = microServiceCallUtil.getOneResponseData(docServiceName, getOneFileUrl, dIRevwSoap.getAttachmentId());
////						 
////							ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
////									false);
////							mapper.setSerializationInclusion(Include.NON_NULL);
////
////							DocRecord docRecord = mapper.convertValue(responseData, DocRecord.class);
////							if(docRecord!=null && docRecord.getDocName()!=null)
////							{
////								logger.info("PortalMessageController::getPortalMessageAttachmentById, docRecord.getDocName():"+docRecord.getDocName());
////								diResponse.setDocName(docRecord.getDocName());
////							}						 
////					 }					
////					 portalMsgeDiResponseLst.add(diResponse);					
////				}
////				 								
////			}	
////			portalMessageResponse.setPortalMessageDiResponseLst(portalMsgeDiResponseLst);
////		}
//		
//		String patient =  portalMessage.getPatientId();
//		if (!StringUtils.isBlank(patient))
//		{
//			portalMessageResponse.setPatient(externalServiceCallUtil.getPatientData(patient.toString())); 
//		} else {
//			portalMessageResponse.setPatient(strArr);
//		}
//		portalMessageResponse.setSubject(portalMessage.getSubject());
//		portalMessageResponse.setId(portalMessage.getId());
//		portalMessageResponse.setPortalMessage(portalMessage);
//		
//		Collections.reverse(portalMessage.getPortalMessageContentList());	
//		logger.info("PortalMessageController::getPortalMessageAttachmentById, portalMessage id="+portalMessage.getId());
//		return ResponseInterface.successResponse(portalMessageResponse, "Portal Message retrieved successfully");
//	  
//		
//	}
//	
//		
//	
	@PutMapping(value = "/v1/ticket/solution/softdelete")
	@ResponseBody
	@ApiOperation(value = "soft delete ticket solution")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "soft delete portal-message"),
			@ApiResponse(code = 500, message = "error"), })
	public GenericResponse<Object> disblePortalMessage(@RequestBody List<UpdateObjectRequest> request) {

		GenericResponse<Object> genericResponse = new GenericResponse<>();

		List<String> resultStrList = portalMessageUseCase
				.changePortalMsg(portalMessageRequestMapper.toDomains(request));

		genericResponse.setMessage("Portal Message disabled/enabled !");
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
		genericResponse.setContent(resultStrList);
		return genericResponse;
	}

//	 
//	
//	@PutMapping(value = "/v1/additional-info/portal-message/delete-object")
//	@ResponseBody
//	@ApiOperation(value = "delete portal-message mapped-object")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "delete portal-message mapped-object"),
//			@ApiResponse(code = 500, message = "error"), })
//	public GenericResponse<Object> deletePortalMessageMappedObject(@RequestBody DeleteMappedObjectRequest request) 
//	{
//		
//		GenericResponse<Object> genericResponse = new GenericResponse<>();
// 
//		String id = deletePortalMessageUseCase.deletePortalMessageMappedObject(portalMessageRequestMapper.toDomain(request));
//			
//		genericResponse.setMessage("Portal Message mapped-object deleted!");
//		genericResponse.setStatus(ResponseStatus.success.toString());
//		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
//		genericResponse.setContent(id); 
//		return genericResponse;
//	}
//	
	@PostMapping(value = "/v1/additionalinfo/portal-message/all")
	@ResponseBody
	@ApiOperation(value = "get portal-message with filters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "get portal-message with filters"),
			@ApiResponse(code = 404, message = "not found"), @ApiResponse(code = 500, message = "not found"), })

	public GenericResponseList<Object> getPortalMessageByPaginationAndFilter(
			@Valid @RequestBody GetPortalMessageRequest request, @Context HttpServletRequest httpRequest) {

		GetPortalMessageResponse responses = portalMessageUseCase.getAllPortalMessage(request.getPortalMessageFilter(),
				request.getPagination());
		List<PortalMessageListResponse> portalMsgeResponseLst = new ArrayList<>();
		String strArr = null;
		if (responses != null && responses.getTotalRecords() > 0) {
			for (TicketResponse portalMessage : responses.getPortalMessages()) {
				int attachmentSize = 0;

				PortalMessageListResponse portalMessageListResponse = new PortalMessageListResponse();
				portalMessageListResponse.setId(portalMessage.getId());
				portalMessageListResponse.setSentBy(portalMessage.getSentBy());
				portalMessageListResponse.setSentDate(portalMessage.getSentDate());

				// String userName = getLoggedInUserName(httpRequest);

				portalMessageListResponse.setMailBoxType(portalMessage.getMailBoxType());
				// attachmentSize=portalMessage.getAttachmentUrl().size();

				if (portalMessage.getAttachmentUrl() != null) {
					attachmentSize = portalMessage.getAttachmentUrl().size();
				}

				portalMessageListResponse.setAttachments(attachmentSize);
				portalMsgeResponseLst.add(portalMessageListResponse);
			}

			return ResponseInterface.successListResponse(portalMsgeResponseLst, responses.getTotalRecords(),
					"Ticket response found");
		} else {

			return ResponseInterface.dataNotFoundListResponse(NOT_FOUND);
		}
	}

	private String getLoggedInUserName(HttpServletRequest httpRequest) {
		String requestTokenHeader = httpRequest.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {

				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				logger.info("######### PortalMessageController.JWT username:" + username);
			} catch (IllegalArgumentException e) {
				logger.error("#########  PortalMessageController.Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.error("######### PortalMessageController.JWT Token has expired");
			}
		} else {
			logger.warn("######### PortalMessageController.JWT Token does not begin with Bearer String");
		}
		return username;
	}
}
