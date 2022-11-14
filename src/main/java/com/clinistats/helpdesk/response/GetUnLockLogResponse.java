package com.clinistats.helpdesk.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUnLockLogResponse {

	private long totalRecords;
	private List<UnLockAudit> unLockAudit;

}
