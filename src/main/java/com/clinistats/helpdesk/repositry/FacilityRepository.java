package com.clinistats.helpdesk.repositry;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.FacilityModel;
import com.clinistats.helpdesk.response.GetIdName;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityModel, Long> {

	FacilityModel findById(long id);

//	@Query("select p from FacilityModel p where p.recordState=0 and p.ehrAnalyticStatus = ?1")
//	List<FacilityModel> findActiveFacilities(EhrAnalyticStatus status);

//	@Query("select p from FacilityModel p where p.ehrAnalyticStatus = ?1") // we are sending all facilities as per
																			// requirement
//	List<FacilityModel> findActiveFacilities(EhrAnalyticStatus status);

	@Query("select f.id, f.name from FacilityModel f where f.recordState=0 and f.status=0")
	List<Object[]> findAllFacilitiesIds();

	Page<FacilityModel> findAll(Specification<FacilityModel> spec, Pageable pageable);

	boolean existsByPhone(String phone);

	@Query("select case when count(c)> 0 then true else false end from FacilityModel c where lower(c.name) like lower(:name)")
	boolean existByName(@Param("name") String name);

	@Query("select f.id, f.name from FacilityModel f where f.recordState=0 and id=:id")
	Object findIdAndName(long id);

//	@Query("select new com.clinistats.emr.common.GetIdNameColor(f.id,f.name,f.facilityColor) from FacilityModel f where f.status=0 and f.recordState = :recordState and UPPER(f.name) LIKE ('%' || UPPER(:name) || '%')")
//	List<GetIdNameColor> findFacilitiesByNameSearch(Status recordState, String name);

//	@Query("select new com.clinistats.emr.common.GetIdNameColor(f.id,f.name,f.facilityColor) from FacilityModel f where f.status=0 and f.recordState = :recordState and f.id in(:facilityId)")
//	List<GetIdNameColor> findFacilitiesByNameSearch(Status recordState, List<Long> facilityId);

//	@Query("select new com.clinistats.emr.common.GetIdNameColor(f.id,f.name,f.facilityColor) from FacilityModel f where f.status=0 and f.recordState = :recordState and UPPER(f.name) LIKE ('%' || UPPER(:name) || '%') AND f.id in(:facilityId)")
//	List<GetIdNameColor> findFacilitiesByNameSearch(Status recordState, String name, List<Long> facilityId);

//	@Query("select new com.clinistats.emr.common.GetIdNameColor(f.id,f.name,f.facilityColor) from FacilityModel f where f.status=0 and f.recordState = :recordState")
//	List<GetIdNameColor> findFacilitiesByNameSearch(Status recordState);

	@Query("select new com.clinistats.helpdesk.response.GetIdName(f.id,f.name) from FacilityModel f where f.recordState = 0 and f.id = ?1 ")
	List<GetIdName> getIdName(long id);

	@Query("select f from FacilityModel f where f.id = :id")
	FacilityModel getFacilityName(Long id);

	long countByNameIgnoreCase(String name);

	long countByPhoneIgnoreCase(String phone);

	@Query("select new com.clinistats.helpdesk.response.GetIdName(f.id,f.name) from FacilityModel f where f.id = ?1 ")
	List<GetIdName> findIdAndNameById(Long id);

}