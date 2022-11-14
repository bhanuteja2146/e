package com.clinistats.helpdesk.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PortalMessageFilter;
import com.clinistats.helpdesk.constatnts.MailBoxType;
import com.clinistats.helpdesk.dao.interfaces.PortalMessageDao;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.domain.TicketResponse;
import com.clinistats.helpdesk.domain.TicketResponseContent;
import com.clinistats.helpdesk.mapper.PortalMessageRequestMapper;
import com.clinistats.helpdesk.model.TicketContentModel;
import com.clinistats.helpdesk.model.TicketModel;
import com.clinistats.helpdesk.model.TicketResponseContentModel;
import com.clinistats.helpdesk.model.TicketResponseModel;
import com.clinistats.helpdesk.repositry.PatientPortalMsgContentRepository;
import com.clinistats.helpdesk.repositry.PatientPortalMsgRepository;
import com.clinistats.helpdesk.repositry.PortalMessageContentRepository;
import com.clinistats.helpdesk.repositry.PortalMessageRepository;
import com.clinistats.helpdesk.response.GetPortalMessageResponse;
import com.clinistats.helpdesk.specification.PortalMessageSpecification;

@Repository("portalMessageDaoImpl")
public class PortalMessageDaoImpl implements PortalMessageDao {

	@Autowired
	private PortalMessageRequestMapper mapper;

	@Autowired
	private PortalMessageRepository repository;

	@Autowired
	private PatientPortalMsgRepository patientPrtlRepository;

	@Autowired
	private PatientPortalMsgContentRepository patientPortalMsgContentRepository;

	@Autowired
	private PortalMessageContentRepository messageContentRepository;

	private static final String NOT_FOUND = " Portal Message not found with ";
	private static final String ENABLED = " has been enabled !";
	private static final String DISABLED = " has been disabled !";

	@Override
	public TicketResponse getPortalMessageById(Long id) {
		Optional<TicketResponseModel> findById = repository.findById(id);
		if (findById.isPresent()) {
			TicketResponseModel portalMessageModel = findById.get();
			TicketResponse domain = mapper.toDomain(portalMessageModel);
			return domain;
		}
		return null;
	}

	@Override
	public TicketResponse addPortalMessage(TicketResponse portalMessage) {
		TicketResponseModel portalMessageModel = mapper.toModel(portalMessage);
		portalMessageModel.setRecordState(Status.ACTIVE);
		TicketResponseModel saved = repository.save(portalMessageModel);

		// saving PatientPortalMsgModel
		TicketModel patnPortalMessageModel = mapper.toPatientModel(portalMessage);
		List<TicketContentModel> portalMessageContent = new ArrayList<TicketContentModel>();

		portalMessage.getPortalMessageContentList().parallelStream().forEach(pa -> {
			TicketContentModel testing = new TicketContentModel();
			testing.setCustomerId(pa.getCustomerId());
			testing.setContent(pa.getContent());
			testing.setRecordState(Status.ACTIVE);
			testing.setCreatedDate(LocalDate.now());
			portalMessageContent.add(testing);
		});

		List<TicketContentModel> saveAll = patientPortalMsgContentRepository.saveAll(portalMessageContent);
		patnPortalMessageModel.setPatientPortalMsgContentList(saveAll);
		patnPortalMessageModel.setMailBoxType(MailBoxType.INBOX);
		patnPortalMessageModel.setRecordState(Status.ACTIVE);
		patientPrtlRepository.save(patnPortalMessageModel);

		return mapper.toDomain(saved);
	}

	@Override
	public List<String> changePortalMsg(List<CustomObjectUpdate> customObjectUpdates) {
		List<String> list = new ArrayList<>();

		if (customObjectUpdates.isEmpty()) {
			return list;
		}

		customObjectUpdates.forEach(obj -> {

			if (obj.getId() == null || obj.getId() == 0) {
				list.add(new StringBuilder().append(NOT_FOUND).append(obj.getId()).toString());
			} else {

				Optional<TicketResponseModel> findById = repository.findById(obj.getId());

				if (findById.isPresent()) {
					TicketResponseModel portalMessageModel = findById.get();
					portalMessageModel
							.setRecordState(obj.getRecordState() == null ? Status.INACTIVE : obj.getRecordState());

					repository.save(portalMessageModel);

					if (Status.ACTIVE.equals(portalMessageModel.getRecordState())) {
						list.add(new StringBuilder().append(portalMessageModel.getId()).append(ENABLED).toString());
					}
					if (Status.INACTIVE.equals(portalMessageModel.getRecordState())) {
						list.add(new StringBuilder().append(portalMessageModel.getId()).append(DISABLED).toString());
					}
				} else {
					list.add(new StringBuilder().append(NOT_FOUND).append(obj.getId()).toString());
				}
			}
		});

		return list;

	}

	@Override
	public GetPortalMessageResponse getAllPortalMessage(PortalMessageFilter filter, Pagination pagination) {
		PortalMessageSpecification portalMsgSpecification = new PortalMessageSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<TicketResponseModel> results = repository.findAll(portalMsgSpecification, pageable);

		return new GetPortalMessageResponse(mapper.toDomainsList(results.toList()), results.getTotalElements());
	}

//	@Override
//	public String deletePortalMessageMappedObject(DeleteMappedObject domain) {
//		PortalMessage portalMsg = getPortalMessageById(domain.getPortalMessageId());
//		PortalMessageModel portalMessageModel = mapper.toModel(portalMsg);
//		if (portalMessageModel != null) {
//			if (domain.getObjectType().equals(MappedObjectType.LAB)) {
//				String labIds = portalMsg.getLabIds();
//				if (labIds != null && !"".equals(labIds)) {
//					System.out.println(" ^^^^^^^^^^^  labIds:" + labIds);
//					List<Long> longLabIds = Arrays.asList(labIds.split(",")).stream()
//							.map(labId -> Long.parseLong(labId)).collect(Collectors.toList());
//					System.out.println(" ^^^^########## ^^^^^^  longLabIds:" + longLabIds);
//					longLabIds.removeIf(ele -> (ele == domain.getObjectId()));
//
//					String updatedLabIds = Joiner.on(",").join(longLabIds);
//					System.out.println(" ^^^^########## ^^^^^^  updatedLabIds:" + updatedLabIds);
//
//					portalMessageModel.setLabIds(updatedLabIds);
//
//					repository.save(portalMessageModel);
//
//				}
//
//			}
//			if (domain.getObjectType().equals(MappedObjectType.DI)) {
//				String diIds = portalMsg.getDiIds();
//				if (diIds != null && !"".equals(diIds)) {
//					System.out.println(" ^^^^^^^^^^^  diIds:" + diIds);
//					List<Long> longDiIds = Arrays.asList(diIds.split(",")).stream().map(labId -> Long.parseLong(labId))
//							.collect(Collectors.toList());
//					System.out.println(" ^^^^########## ^^^^^^  longDiIds:" + longDiIds);
//					longDiIds.removeIf(ele -> (ele == domain.getObjectId()));
//
//					String updatedDiIds = Joiner.on(",").join(longDiIds);
//					System.out.println(" ^^^^########## ^^^^^^  updatedDiIds:" + updatedDiIds);
//
//					portalMessageModel.setDiIds(updatedDiIds);
//
//					repository.save(portalMessageModel);
//
//				}
//			}
//		}
//
//		return portalMessageModel.getId() + " has been updated successfully";
//	}

	@Override
	public TicketResponseContent addPortalMessageContent(TicketResponseContent content) {
		TicketResponseContentModel model = mapper.toModel(content);
		model.setRecordState(Status.ACTIVE);
		TicketResponseContentModel savedModel = messageContentRepository.save(model);
		return mapper.toDomain(savedModel);
	}

}
