package com.clinistats.hepdesk.dao.impl;

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

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PatientPortalFilter;
import com.clinistats.hepdesk.constatnts.MailBoxType;
import com.clinistats.hepdesk.dao.interfaces.CustomerServiceDao;
import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.TicketRaiseDomain;
import com.clinistats.hepdesk.domain.Status;
import com.clinistats.hepdesk.mapper.PortalMessageRequestMapper;
import com.clinistats.hepdesk.model.TicketModel;
import com.clinistats.hepdesk.model.TicketResponseContentModel;
import com.clinistats.hepdesk.model.TicketResponseModel;
import com.clinistats.hepdesk.repositry.PatientPortalMsgRepository;
import com.clinistats.hepdesk.repositry.PortalMessageContentRepository;
import com.clinistats.hepdesk.repositry.PortalMessageRepository;
import com.clinistats.hepdesk.response.GetPatientPortalMsgResponse;
import com.clinistats.hepdesk.specification.PatientPortalMsgSpecification;

@Repository
public class CustomerServiceDaoImpl implements CustomerServiceDao {

	@Autowired
	private PortalMessageRequestMapper mapper;

	@Autowired
	private PatientPortalMsgRepository repository;

	@Autowired
	private PortalMessageRepository portalRepository;

	@Autowired
	private PortalMessageContentRepository portalMessageContentRepository;

	private static final String NOT_FOUND = " Patient Portal Message not found with ";
	private static final String ENABLED = " has been enabled !";
	private static final String DISABLED = " has been disabled !";

	@Override
	public TicketRaiseDomain addPatientPortalMsg(TicketRaiseDomain patientPortalMsg) {
		TicketModel patientPortalMsgModel = mapper.toModel(patientPortalMsg);
		patientPortalMsgModel.setRecordState(Status.ACTIVE);
		TicketModel saved = repository.save(patientPortalMsgModel);

		// saving PortalMsgModel
		TicketResponseModel portalMessageModel = mapper.toPortalModel(patientPortalMsg);
		List<TicketResponseContentModel> portalMessageContent = new ArrayList<TicketResponseContentModel>();

		patientPortalMsg.getPatientPortalMsgContentList().parallelStream().forEach(pa -> {
			TicketResponseContentModel test = new TicketResponseContentModel();
//			test.setPatientId(pa.getPatientId());
			test.setContent(pa.getContent());
			test.setRecordState(Status.ACTIVE);
			test.setCreatedDate(LocalDate.now());
			portalMessageContent.add(test);
		});

		List<TicketResponseContentModel> saveAll = portalMessageContentRepository.saveAll(portalMessageContent);
		portalMessageModel.setPortalMessageContentList(saveAll);
		portalMessageModel.setMailBoxType(MailBoxType.INBOX);
		portalMessageModel.setRecordState(Status.ACTIVE);
		portalRepository.save(portalMessageModel);

		return mapper.toDomain(saved);
	}

	@Override
	public List<String> changePatientPortalMsg(List<CustomObjectUpdate> customObjectUpdates) {

		List<String> list = new ArrayList<>();

		if (customObjectUpdates.isEmpty()) {
			return list;
		}

		customObjectUpdates.forEach(obj -> {

			if (obj.getId() == null || obj.getId() == 0) {
				list.add(new StringBuilder().append(NOT_FOUND).append(obj.getId()).toString());
			} else {

				Optional<TicketModel> findById = repository.findById(obj.getId());

				if (findById.isPresent()) {
					TicketModel portalMessageModel = findById.get();
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
	public GetPatientPortalMsgResponse getAllPatientPortalMsgs(PatientPortalFilter filter, Pagination pagination) {
		PatientPortalMsgSpecification portalMsgSpecification = new PatientPortalMsgSpecification(filter);
		Sort sort = Sort.by(pagination.getSortOrder(), pagination.getSortBy());

		Pageable pageable = PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize(), sort);
		Page<TicketModel> results = repository.findAll(portalMsgSpecification, pageable);

		return new GetPatientPortalMsgResponse(mapper.toDomainsListNew(results.toList()), results.getTotalElements());
	}

	@Override
	public TicketRaiseDomain getPatientPortalMsgById(Long id) {
		Optional<TicketModel> findById = repository.findById(id);
		if (findById.isPresent()) {
			TicketModel patientPortalMsgModel = findById.get();
			TicketRaiseDomain domain = mapper.toDomain(patientPortalMsgModel);
			return domain;
		}
		return null;

	}

}
