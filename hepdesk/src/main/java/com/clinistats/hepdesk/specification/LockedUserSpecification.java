package com.clinistats.hepdesk.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.hepdesk.config.filter.UserFilter;
import com.clinistats.hepdesk.model.UserProfileModel;

public class LockedUserSpecification implements Specification<UserProfileModel> {

	private static final long serialVersionUID = 1L;
	private final UserFilter criteria;
	private List<Predicate> filters;

	private static final String LOCK_STATUS = "Lock";

	public LockedUserSpecification(UserFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<UserProfileModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		filters = new ArrayList<>();
		if (criteria != null) {
			if (criteria.getQuery() != null && !"".equals(criteria.getQuery())) {
				boolean spaceContains = criteria.getQuery().contains(" ");
				boolean commaContains = criteria.getQuery().contains(",");
				if (commaContains) { // checking for commas in between firstname and lastname
					List<String> asList = Arrays.asList(criteria.getQuery().split(","));
					filters.add(criteriaBuilder.and(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
									asList.get(0).toLowerCase()),
							criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
									asList.get(1).toLowerCase())));
				} else if (spaceContains) { // checking for commas in between firstname and lastname
					List<String> asList = Arrays.asList(criteria.getQuery().split(" "));
					filters.add(criteriaBuilder.and(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
									asList.get(0).toLowerCase()),
							criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
									asList.get(1).toLowerCase())));
				} else {
					Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
							"%" + criteria.getQuery().toLowerCase() + "%");
					Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
							"%" + criteria.getQuery().toLowerCase() + "%");
					filters.add(criteriaBuilder.or(firstName, lastName));
				}
			}

			if (criteria.getStatus() != null)
				filters.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));

		}

		filters.add(criteriaBuilder.equal(root.get("lockStatus"), LOCK_STATUS));

		query.distinct(true);
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
