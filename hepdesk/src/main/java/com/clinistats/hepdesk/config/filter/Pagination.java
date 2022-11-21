package com.clinistats.hepdesk.config.filter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Sort.Direction;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

	@Min(1)
	private Integer pageNumber;
	@Min(1)
	private Integer pageSize;
	@NotNull
	@NotBlank
	private String sortBy;
	@NotNull
	private Direction sortOrder;
}
