package com.clinistats.helpdesk;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTaxResponse {

	private Long id;

	private String employeeId;

	private String firstName;

	private String lastName;

	private String email;

	private List<String> phoneNumber;

	private BigDecimal salary;

	private LocalDate doj;

	private BigDecimal yearlySalry;

	private BigDecimal taxAmount;

	private BigDecimal accesAmount;

}
