package com.clinistats.helpdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.model.LogInSettingsLogModel;
import com.clinistats.helpdesk.model.LogInSettingsModel;

@Mapper(componentModel = "spring")
public interface LogInSettingsMapper {
	
	
	LogInSettingsModel toModel(LogInSettings domain);
	LogInSettings toDomain(LogInSettingsModel logInSettingsModelAdded);
	LogInSettingsLogModel toModel(LogInSettingsLog domain);
	List<LogInSettings> toDomains(List<LogInSettingsModel> logInSettingsModels);
	List<LogInSettingsModel> toModel(List<LogInSettings> domain);
	
}
