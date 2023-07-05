package com.clinistats.helpdesk;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

	private Long id;

	@NotNull
	@NotEmpty
	private String employeeId;

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@Email
	private String email;

    @NotEmpty(message = "Need to provide Phone Number")
	private List<@NotNull String> phoneNumber;

	@NotNull
	@NotEmpty
	private BigDecimal salary;

	@NotNull
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy")
	private LocalDate doj;

}
