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
@Table(name = "CONTACT_DETAILS")
//lombok annotations for setters and getters
@Data
//Generates implementations for the equals and hashCode methods inherited by all objects, based on relevant fields.
@EqualsAndHashCode(callSuper = false)
@Embeddable
public class ContactDetailsModel extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTACT_DETAIL_ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CELL_PHONE")
	private String cellPhone;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@Column(name = "Fax")
	private String fax;

	@Column(name = "pager")
	private String pager;

	@Column(name = "is_email", columnDefinition = "BOOLEAN DEFAULT true")
	private Boolean isEmail;

	@Column(name = "reason")
	private String reason;

	@OneToOne(mappedBy = "contactDetails", cascade = CascadeType.ALL)
	private StaffModel provider;

}
