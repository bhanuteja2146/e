package com.clinistats.hepdesk.services.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.clinistats.hepdesk.dao.interfaces.ProvFacPermissionMappingDao;
import com.clinistats.hepdesk.domain.ProviderFacilityPermssionMappingDomain;
import com.clinistats.hepdesk.domain.Status;
import com.clinistats.hepdesk.services.interfaces.ProvFacPermissionMappingUseCase;

/**
 * @author lukman.d
 *
 */
@Service
public class ProvFacPermissionMappingUseCaseImpl implements ProvFacPermissionMappingUseCase {

	@Autowired
	private ProvFacPermissionMappingDao provFacPermissionMappingDaoImpl;

	/**
	 * Process assigns/de assigns new/existing facility to provider
	 * 
	 */
	@Override
	public List<Long> mapFacilityToProviders(String providerId, List<String> facilityId, boolean resource) {

		System.out.println("providerId " + providerId + " " + facilityId);

		try {
			if (providerId != null && !providerId.isBlank() && Long.parseLong(providerId) > 0
					&& !CollectionUtils.isEmpty(facilityId)) {
				List<Long> convertedId = facilityId.parallelStream().map(Long::parseLong).collect(Collectors.toList());
				System.out.println("convertedId " + convertedId);

				// UN MAPPING NEW FACILITY EXISTING
				provFacPermissionMappingDaoImpl.updateFacStatusExcludingFacilityId(Long.parseLong(providerId),
						Status.INACTIVE, convertedId, resource);

				// MAPPING NEW FACILITY EXISTING IF NOT PRES
				convertedId.forEach(fac -> {
					ProviderFacilityPermssionMappingDomain exisitngPermission = provFacPermissionMappingDaoImpl
							.findByFacilityIdAndProviderId(fac, Long.parseLong(providerId), resource);
					if (exisitngPermission != null && Status.INACTIVE.compareTo(exisitngPermission.getStatus()) == 0) {
						exisitngPermission.setStatus(Status.ACTIVE);
						provFacPermissionMappingDaoImpl.add(exisitngPermission);

					} else if (exisitngPermission == null) {

						provFacPermissionMappingDaoImpl.add(ProviderFacilityPermssionMappingDomain.builder()
								.facilityId(fac).providerId(Long.parseLong(providerId)).status(Status.ACTIVE)
								.resource(resource).build());
					}

				});

				return convertedId;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

//	@Override
//	public Map<Long, List<Long>> deassignFacilityToProviders(String providerId, List<String> facilityId) {
//
//		if (!StringUtils.isBlank(providerId) && Long.parseLong(providerId) > 0
//				&& !CollectionUtils.isEmpty(facilityId)) {
//			facilityId.forEach(fac -> {
//				ProviderFacilityMappingModel exisitngPermission = provFacRepo
//						.findByFacilityIdAndProviderId(Long.parseLong(fac), Long.parseLong(providerId));
//
//				if (exisitngPermission != null && Status.INACTIVE.compareTo(exisitngPermission.getStatus()) != 0) {
//					exisitngPermission.setStatus(Status.INACTIVE);
//
//					provFacRepo.save(exisitngPermission);
//				}
//			});
//
//			return getMappedFacilityListByStatus(providerId, Status.ACTIVE);
//		}
//
//		return new HashMap();
//	}

	/**
	 * Process return assigned/de- assigned facilities to providers by provider id,
	 * status
	 * 
	 * Where status= ACTIVE returns assigned facilities, Where status= INACTIVE
	 * returns de-assigned facilities
	 *
	 */
	@Override
	public Map<Long, List<Long>> getMappedFacilityListByStatus(String providerId, Status status, boolean resource) {
		Map<Long, List<Long>> allFacilityList = new HashMap<>();
		if (providerId != null && !providerId.isBlank()) {
			System.out.println("providerId " + providerId + "" + providerId.length());
//			Arrays.asList(providerId.split(",")).parallelStream().collect(Collectors.toMap(providerId,s-> provFacRepo.getAllFacilityMappedByProvider(Long.parseLong(s), Status.ACTIVE)));

//			Map<Long, List<Long>> collect = Arrays.asList(providerId.split(",")).parallelStream()
//					.map(s -> provFacRepo.getAllFacilityMappedByProvider(Long.parseLong(s), Status.ACTIVE))
//					.collect(Collectors.toMap(key -> Long.parseLong(key.toString()), value -> value));

			List<Long> collect = Arrays.asList(providerId.split(",")).parallelStream()
					.filter(s -> s != null && !s.isBlank() && !"null".equalsIgnoreCase(s)).map(Long::parseLong)
					.collect(Collectors.toList());
			for (Long id : collect) {
				List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
						.getAllFacilityMappedByProvider(id, status, resource);
				if (!CollectionUtils.isEmpty(collect)) {

					allFacilityList.put(id, allFacilityMappedByProvider.stream().map(s -> s.getProviderId())
							.collect(Collectors.toList()));
				}
			}

			System.out.println("assigned list= collect" + collect);

			return allFacilityList;
		}
		return Collections.emptyMap();

	}

	@Override
	public Map<Long, List<ProviderFacilityPermssionMappingDomain>> getMappedProviderListByFacilityAndStatus(
			String facilityId, Status status, String resource) {
		Map<Long, List<ProviderFacilityPermssionMappingDomain>> allFacilityList = new HashMap<>();
		if (facilityId != null && !facilityId.isBlank()) {
			System.out.println("facility id " + facilityId);
//			Arrays.asList(providerId.split(",")).parallelStream().collect(Collectors.toMap(providerId,s-> provFacRepo.getAllFacilityMappedByProvider(Long.parseLong(s), Status.ACTIVE)));

//			Map<Long, List<Long>> collect = Arrays.asList(providerId.split(",")).parallelStream()
//					.map(s -> provFacRepo.getAllFacilityMappedByProvider(Long.parseLong(s), Status.ACTIVE))
//					.collect(Collectors.toMap(key -> Long.parseLong(key.toString()), value -> value));

			List<Long> collect = Arrays.asList(facilityId.split(",")).parallelStream()
					.filter(s -> s != null && !s.isBlank() && !"null".equalsIgnoreCase(s)).map(Long::parseLong)
					.collect(Collectors.toList());
			for (Long id : collect) {
				if ("prov".equalsIgnoreCase(resource)) {
					List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
							.getAllProviderMappedByFacility(id, status, false);
					if (!CollectionUtils.isEmpty(collect) && !allFacilityMappedByProvider.isEmpty()) {
						allFacilityList.put(id, allFacilityMappedByProvider);
					}
				} else if ("non".equalsIgnoreCase(resource)) {
					List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
							.getAllProviderMappedByFacility(id, status, true);
					if (!CollectionUtils.isEmpty(collect) && !allFacilityMappedByProvider.isEmpty()) {
						allFacilityList.put(id, allFacilityMappedByProvider);
					}
				} else if ("all".equalsIgnoreCase(resource)) {
					List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
							.getAllProviderMappedByFacility(id, status);
					if (!CollectionUtils.isEmpty(collect) && !allFacilityMappedByProvider.isEmpty()) {
						allFacilityList.put(id, allFacilityMappedByProvider);
					}
				}
			}

			System.out.println("assigned list= collect" + collect);

			return allFacilityList;
		}
		return Collections.emptyMap();

	}

	@Override
	public Map<Long, List<String>> getMappedUserNameByFacility(String facilityId, Status status, String resource) {
		Map<Long, List<String>> allFacilityList = new HashMap<>();
		if (facilityId != null && !facilityId.isBlank()) {
			System.out.println("facility id " + facilityId);
//			Arrays.asList(providerId.split(",")).parallelStream().collect(Collectors.toMap(providerId,s-> provFacRepo.getAllFacilityMappedByProvider(Long.parseLong(s), Status.ACTIVE)));

//			Map<Long, List<Long>> collect = Arrays.asList(providerId.split(",")).parallelStream()
//					.map(s -> provFacRepo.getAllFacilityMappedByProvider(Long.parseLong(s), Status.ACTIVE))
//					.collect(Collectors.toMap(key -> Long.parseLong(key.toString()), value -> value));

			List<Long> collect = Arrays.asList(facilityId.split(",")).parallelStream()
					.filter(s -> s != null && !s.isBlank() && !"null".equalsIgnoreCase(s)).map(Long::parseLong)
					.collect(Collectors.toList());
			for (Long id : collect) {
				if ("prov".equalsIgnoreCase(resource)) {
					List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
							.getAllProviderMappedByFacility(id, status, false);
					if (!CollectionUtils.isEmpty(collect) && !allFacilityMappedByProvider.isEmpty()) {
						allFacilityList.put(id,
								allFacilityMappedByProvider.stream()
										.filter(s -> s.getCreatedBy() != null && !s.getCreatedBy().isBlank()
												&& !"null".equalsIgnoreCase(s.getCreatedBy()))
										.map(s -> s.getUserName()).collect(Collectors.toList()));
					}
				} else if ("non".equalsIgnoreCase(resource)) {
					List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
							.getAllProviderMappedByFacility(id, status, true);
					if (!CollectionUtils.isEmpty(collect) && !allFacilityMappedByProvider.isEmpty()) {
						allFacilityList.put(id,
								allFacilityMappedByProvider.stream()
										.filter(s -> s.getCreatedBy() != null && !s.getCreatedBy().isBlank()
												&& !"null".equalsIgnoreCase(s.getCreatedBy()))
										.map(s -> s.getUserName()).collect(Collectors.toList()));
					}
				} else if ("all".equalsIgnoreCase(resource)) {
					List<ProviderFacilityPermssionMappingDomain> allFacilityMappedByProvider = provFacPermissionMappingDaoImpl
							.getAllProviderMappedByFacility(id, status);
					if (!CollectionUtils.isEmpty(collect) && !allFacilityMappedByProvider.isEmpty()) {
						allFacilityList.put(id,
								allFacilityMappedByProvider.stream()
										.filter(s -> s.getCreatedBy() != null && !s.getCreatedBy().isBlank()
												&& !"null".equalsIgnoreCase(s.getCreatedBy()))
										.map(s -> s.getUserName()).collect(Collectors.toList()));
					}
				}
			}

			System.out.println("assigned list= collect" + collect);

			return allFacilityList;
		}
		return Collections.emptyMap();
	}

//	/**
//	 *
//	 */
//	@Override
//	public List<ProviderFacilityMappingModel> getMappedFacilityListByStatus(String providerId) {
//
//		if (StringUtils.isNotBlank(providerId) && Long.parseLong(providerId) > 0) {
//			return provFacRepo.findByProviderId(Long.parseLong(providerId));
//
//		}
//		return Collections.emptyList();
//	}
}
