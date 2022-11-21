package com.clinistats.hepdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.hepdesk.config.filter.FacilityFilter;
import com.clinistats.hepdesk.model.FacilityModel;

public class FacilitySpecification implements Specification<FacilityModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final FacilityFilter criteria;
	private List<Predicate> filters;
	
	public FacilitySpecification(FacilityFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<FacilityModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		System.out.println(" ----- in toPredicate enter -----");
		filters = new ArrayList<Predicate>();		
				
		String searchStrLo = "%"+ criteria.getQuery().toLowerCase() + "%";
		String searchStrUp = "%"+ criteria.getQuery().toUpperCase() + "%";
		String idText = criteria.getQuery();
		
		if (criteria != null) {
			if(criteria.getRecordState() != null) {
				filters.add(criteriaBuilder.equal(root.get("recordState"), criteria.getRecordState()));
			}
			
			if(criteria.getQuery()!=null) {
				if (idText.matches("[0-9]+") && idText.length() > 0) {
					String facilityId = "%"+idText+"%";
					filters.add(criteriaBuilder.like(root.get("id").as(String.class), facilityId));
				}
				else if(criteria.getQuery() !=null && !criteria.getQuery().isEmpty()) {
					filters.add(criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchStrLo),criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), searchStrUp)));
					
				}
			
			}
			
			if(criteria.getQuery()!=null) {
				if(criteria.getType() != null) {
					String typeUpperCase = criteria.getType().toUpperCase();
					filters.add(criteriaBuilder.or(criteriaBuilder.equal(criteriaBuilder.lower(root.get("type")), searchStrLo),criteriaBuilder.equal(criteriaBuilder.upper(root.get("type")), typeUpperCase)));
				}
				
			}
		}
		
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
