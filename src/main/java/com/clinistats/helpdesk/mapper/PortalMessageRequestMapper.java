package com.clinistats.helpdesk.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.TicketRaiseDomain;
import com.clinistats.helpdesk.domain.TicketResponse;
import com.clinistats.helpdesk.domain.TicketResponseContent;
import com.clinistats.helpdesk.model.TicketModel;
import com.clinistats.helpdesk.model.TicketResponseContentModel;
import com.clinistats.helpdesk.model.TicketResponseModel;
import com.clinistats.helpdesk.request.AddPortalMessageRequest;
import com.clinistats.helpdesk.request.UpdateObjectRequest;

@Mapper(componentModel = "spring")
public interface PortalMessageRequestMapper
{

	TicketResponse toAddDomain(@Valid AddPortalMessageRequest request);

	List<CustomObjectUpdate> toDomains(List<UpdateObjectRequest> request);

	TicketResponseModel toModel(TicketResponse portalMessage);

	TicketResponse toDomain(TicketResponseModel saved);

	List<TicketResponse> toDomainsList(List<TicketResponseModel> list);

	TicketModel toPatientModel(TicketResponse portalMessage);

	TicketResponseContentModel toModel(TicketResponseContent content);

	TicketResponseContent toDomain(TicketResponseContentModel savedModel);

	TicketRaiseDomain toPortalMessage(@Valid AddPortalMessageRequest request);


	List<TicketResponseContent> toPatientPortalMsgList(List<TicketResponseContent> portalMessageContentList);

	TicketModel toModel(TicketRaiseDomain patientPortalMsg);

	TicketResponseModel toPortalModel(TicketRaiseDomain patientPortalMsg);

	TicketRaiseDomain toDomain(TicketModel saved);

	List<TicketRaiseDomain> toDomainsListNew(List<TicketModel> list);

	 
//	DeleteMappedObject toDomain(DeleteMappedObjectRequest request);

//	List<PortalMessageContent> toPortalMessageList(List<PatientPortalMsgContent> patientPortalMsgContentList);

}
