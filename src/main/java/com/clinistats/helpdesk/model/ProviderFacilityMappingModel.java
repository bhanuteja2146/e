package com.clinistats.helpdesk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions_provider_facility_mapping")
@Getter
@Setter
public class ProviderFacilityMappingModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "facility_id", nullable = false)
	private Long facilityId;

	@Column(name = "provider_id", nullable = false)
	private Long providerId;
	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "resource", nullable = false)
	private boolean resource;

	@Column(name = "created_by")
	private String createdBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "record_status")
	private Status status;
}
