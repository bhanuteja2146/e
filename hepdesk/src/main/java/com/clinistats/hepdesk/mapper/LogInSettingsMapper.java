package com.clinistats.hepdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.hepdesk.domain.LogInSettings;
import com.clinistats.hepdesk.domain.LogInSettingsLog;
import com.clinistats.hepdesk.model.LogInSettingsLogModel;
import com.clinistats.hepdesk.model.LogInSettingsModel;

@Mapper(componentModel = "spring")
public interface LogInSettingsMapper {
	
	
	LogInSettingsModel toModel(LogInSettings domain);
	LogInSettings toDomain(LogInSettingsModel logInSettingsModelAdded);
	LogInSettingsLogModel toModel(LogInSettingsLog domain);
	List<LogInSettings> toDomains(List<LogInSettingsModel> logInSettingsModels);
	List<LogInSettingsModel> toModel(List<LogInSettings> domain);
	
}
