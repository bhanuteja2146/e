package com.clinistats.helpdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.helpdesk.config.filter.UserFilter;
import com.clinistats.helpdesk.model.UserProfileModel;

public class UserProfileSpecification implements Specification<UserProfileModel> {

	private static final long serialVersionUID = 1L;
	private final UserFilter criteria;
	private List<Predicate> filters;

	public UserProfileSpecification(UserFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<UserProfileModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		filters = new ArrayList<>();
		if (criteria != null) {
			if (criteria.getQuery() != null && !"".equals(criteria.getQuery()))
				filters.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userName")),
						"%" + criteria.getQuery().toLowerCase() + "%"));

			if (criteria.getStatus() != null)
				filters.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));

			if (criteria.getFaciltiyId() != null && criteria.getFaciltiyId() > 0)
				filters.add(criteriaBuilder.equal(root.get("facilityId"), criteria.getFaciltiyId()));

			if (criteria.getUserType() != null && !"".equals(criteria.getUserType()))
				filters.add(criteriaBuilder.equal(root.get("userType"), criteria.getUserType()));

		}

		query.distinct(true);
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
