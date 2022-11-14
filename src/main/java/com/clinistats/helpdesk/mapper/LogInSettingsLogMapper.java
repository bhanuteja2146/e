package com.clinistats.helpdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.model.LogInSettingsLogModel;
import com.clinistats.helpdesk.model.UnLockAuditModel;
import com.clinistats.helpdesk.response.UnLockAudit;

@Mapper(componentModel = "spring")
public interface LogInSettingsLogMapper {

	LogInSettingsLogModel toModel(LogInSettingsLog domain);

	LogInSettingsLog toDomain(LogInSettingsLogModel logInSettingsLogModelAdded);

	List<LogInSettingsLog> toDomains(List<LogInSettingsLogModel> models);

	List<UnLockAudit> toLogDomains(List<UnLockAuditModel> content);

}
