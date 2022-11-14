package com.clinistats.hepdesk.response;

import java.util.List;

import com.clinistats.hepdesk.domain.Customer;

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
