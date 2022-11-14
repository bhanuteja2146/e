package com.clinistats.hepdesk.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author swapna
 * 
 *         Date 13/05/2020.
 */

@Entity
@Table(name = "PRIMARY_CONTACT_DETAILS")
//lombok annotations for setters and getters
@Data
//Generates implementations for the equals and hashCode methods inherited by all objects, based on relevant fields.
@EqualsAndHashCode(callSuper = false)

@Embeddable
public class PrimaryContactDetailsModel extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRIMARY_CONTACT_DETAIL_ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "COUNTRY")
	private String country;

	@OneToOne(mappedBy = "addressInfo", cascade = CascadeType.ALL)
	private StaffModel staff;

	@Column(name = "DIRECTIONS")
	private String directions;

	private String region;

}