package com.clinistats.hepdesk.repositry;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clinistats.hepdesk.model.StaffModel;

@Repository
@Transactional
public interface StaffRepositry extends JpaRepository<StaffModel, Long> {

	boolean existsById(Long id);

	@Query("select p from StaffModel p where p.recordState=0 and p.id = ?1")
	StaffModel findActiveProvider(Long id);

//	@Query("select p from StaffModel p where p.recordState=0 and p.ehrAnalyticStatus = ?1")
//	List<StaffModel> findActiveProviders(EhrAnalyticStatus status);

//	@Query("select p from StaffModel p where p.ehrAnalyticStatus = ?1") // sending all providers as per requirement
//	List<StaffModel> findActiveProviders(EhrAnalyticStatus status);

	@Modifying
	@Query("update StaffModel p set p.recordState=1 where p.id = ?1")
	void updateProviderToDeactive(Long id);

	Optional<StaffModel> findById(Long id);

	@Query("select p.id, concat(p.name) as name from StaffModel p where p.recordState=0")
	List<Object[]> findAllStaffModelIds();

	Page<StaffModel> findAll(Specification<StaffModel> spec, Pageable pageable);

//	Page<StaffModel> findAllProviders(Specification<StaffModel> spec, Pageable pageable);

	@Query("select p.id, concat(p.name) as name from StaffModel p where p.recordState=0 and p.id in ?1")
	List<Object[]> getProviderNamesByIds(List<Long> id);

//	@Query("select new com.clinistats.emr.common.GetIdName(f.id,f.name) from StaffModel f where f.recordState = :recordState and ( UPPER(f.firstName) LIKE ('%' || UPPER(:name) || '%') or UPPER(f.lastName) LIKE ('%' || UPPER(:name) || '%')) and f.globalStatus = true")
//	List<GetIdName> searchProviderByName(String name, Status recordState);

//	@Query("select new com.clinistats.emr.common.GetIdName(f.id,f.name) from StaffModel f where f.recordState = :recordState and f.globalStatus = true")
//	List<GetIdName> searchProviderByName(Status recordState);

//	@Query("select new com.clinistats.emr.common.GetIdName(f.id,f.name) from StaffModel f where f.recordState = 0 and f.id = ?1 ")
//	List<GetIdName> getIdName(long id);

	@Modifying
	@Query("update StaffModel p set p.photoPath=?1 where p.id = ?2")
	void addProviderPhotoPath(String photoPath, Long id);

	@Modifying
	@Query("update StaffModel p set p.signPhotoPath=?1 where p.id = ?2")
	void addProviderSignPhotoPath(String photoPath, Long id);

	@Query("select pr from StaffModel pr where pr.id = :id")
	StaffModel getProviderName(Long id);

//	@Query("select pr from StaffModel pr where pr.primaryFacility.id = :id")
//	List<StaffModel> findByFacilityId(long id);

//	@Query("select distinct(f.specility) from StaffModel f where f.primaryFacility.id = :facilityId and f.recordState = :recordState ")
//	List<String> searchSpecialityByFacility(Long facilityId, Status recordState);

//	@Query("select distinct(f.specility) from StaffModel f where f.primaryFacility.id = :facilityId and f.recordState = :recordState and  ( UPPER(f.specility) LIKE ('%' || UPPER(:query) || '%')) ")
//	List<String> searchSpecialityByFacility(Long facilityId, Status recordState, String query);

//	@Query("select new com.clinistats.emr.common.ProviderIdSpeciality(f.id,f.name) from StaffModel f where f.primaryFacility.id = :facilityId and f.specility = :speciality and f.recordState = :recordState ")
//	List<ProviderIdSpeciality> searchProviderBySpeciality(Long facilityId, String speciality, Status recordState);

//	@Query("select new com.clinistats.emr.common.ProviderIdSpeciality(f.id,f.name) from StaffModel f where f.primaryFacility.id = :facilityId and f.specility = :speciality and f.recordState = :recordState and  ( UPPER(f.name) LIKE ('%' || UPPER(:query) || '%')) ")
//	List<ProviderIdSpeciality> searchProviderBySpeciality(Long facilityId, String speciality, Status recordState,
//			String query);

//	@Query("select f.defaultScreen from StaffModel f where f.id = ?1 ")
//	String getProviderDefaultScreen(Long valueOf);

	@Query("select f.numberOfSlot from StaffModel f where f.id = ?1 ")
	Integer getCount(Long id);

//	@Query("select new com.clinistats.emr.common.GetIdName(f.id,f.name) from StaffModel f where f.id = :id")
//	List<GetIdName> getProviderNamesAndByIds(long id);

//	@Query("select new com.clinistats.emr.common.GetIdName(f.id,f.name) from StaffModel f where f.recordState = :recordState and ( UPPER(f.firstName) LIKE ('%' || UPPER(:name) || '%') or UPPER(f.lastName) LIKE ('%' || UPPER(:name) || '%')) and f.globalStatus = true AND f.id in(:providerId)")
//	List<GetIdName> searchProviderByNameAndId(Status recordState, String name, List<Long> providerId);

//	@Query("select new com.clinistats.emr.common.GetIdName(f.id,f.name) from StaffModel f where f.recordState = :recordState and f.globalStatus = true AND f.id in(:providerId)")
//	List<GetIdName> searchProviderByNameAndId(Status recordState, List<Long> providerId);

}
