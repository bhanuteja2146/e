package com.clinistats.hepdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.hepdesk.domain.LogInSettingsLog;
import com.clinistats.hepdesk.model.LogInSettingsLogModel;
import com.clinistats.hepdesk.model.UnLockAuditModel;
import com.clinistats.hepdesk.response.UnLockAudit;

@Mapper(componentModel = "spring")
public interface LogInSettingsLogMapper {

	LogInSettingsLogModel toModel(LogInSettingsLog domain);

	LogInSettingsLog toDomain(LogInSettingsLogModel logInSettingsLogModelAdded);

	List<LogInSettingsLog> toDomains(List<LogInSettingsLogModel> models);

	List<UnLockAudit> toLogDomains(List<UnLockAuditModel> content);

}
