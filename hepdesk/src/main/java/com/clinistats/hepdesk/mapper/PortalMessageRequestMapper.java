package com.clinistats.hepdesk.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.TicketRaiseDomain;
import com.clinistats.hepdesk.domain.TicketResponse;
import com.clinistats.hepdesk.domain.TicketResponseContent;
import com.clinistats.hepdesk.model.TicketModel;
import com.clinistats.hepdesk.model.TicketResponseContentModel;
import com.clinistats.hepdesk.model.TicketResponseModel;
import com.clinistats.hepdesk.request.AddPortalMessageRequest;
import com.clinistats.hepdesk.request.UpdateObjectRequest;

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
