package com.clinistats.helpdesk.response;

import java.util.List;

import com.clinistats.helpdesk.domain.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetStaffResponse {

	private List<Customer> provider;
	private Long totalRecords;
}
