package com.clinistats.helpdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.helpdesk.config.filter.StaffFilter;
import com.clinistats.helpdesk.model.StaffModel;

public class ProviderSpecification implements Specification<StaffModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final StaffFilter criteria;
	private List<Predicate> filters;

	public ProviderSpecification(StaffFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<StaffModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		filters = new ArrayList<Predicate>();
		String searchStrLo = "%" + criteria.getQuery().toLowerCase() + "%";
		String searchStrUp = "%" + criteria.getQuery().toUpperCase() + "%";
		String idText = criteria.getQuery();

		if (criteria != null) {
			if (criteria.getRecordState() != null) {
				filters.add(criteriaBuilder.equal(root.get("recordState"), criteria.getRecordState()));
			}

			if (criteria.getQuery() != null) {
				if (idText.matches("[0-9]+") && idText.length() > 0) {
					Long searchId = Long.parseLong(idText);
					filters.add(criteriaBuilder.equal(root.get("id"), searchId));
				} else if (criteria.getQuery() != null && !criteria.getQuery().isEmpty()) {
					filters.add(criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), searchStrLo),
							(criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), searchStrUp)),
							(criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), searchStrLo)),
							(criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), searchStrUp))));
					// filters.add(criteriaBuilder.or(criteriaBuilder.like(root.get("firstName"),
					// searchStr)));
				}

			}

		}

		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
