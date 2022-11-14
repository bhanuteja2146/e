package com.clinistats.helpdesk.mapper;

/**
 * 
 */

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.model.UserProfileModel;

/**
 * @author LENOVO
 *
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper {

	UserProfile toDomain(UserProfileModel userProfileModel);

	List<UserProfile> toDomain(List<UserProfileModel> userProfileModels);

	UserProfileModel toModel(UserProfile userProfile);

	List<UserProfileModel> toModel(List<UserProfile> userProfiles);
//	 List<UserProfile> toDomains(List<UserProfileModel> content);
}
