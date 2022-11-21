/**
 * 
 */
package com.clinistats.hepdesk.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.clinistats.hepdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Kranthi Reddy
 *
 */
@Getter
@Setter
public class UpdateObjectRequest {
	
	@NotNull @NotEmpty
	private Long id;

	private Status recordState;

}
