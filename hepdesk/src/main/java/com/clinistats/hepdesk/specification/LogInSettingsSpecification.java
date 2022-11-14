package com.clinistats.hepdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.hepdesk.config.filter.Filter;
import com.clinistats.hepdesk.model.LogInSettingsModel;

public class LogInSettingsSpecification implements Specification<LogInSettingsModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Filter criteria;
	private List<Predicate> filters;

	public LogInSettingsSpecification(Filter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<LogInSettingsModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {

		filters = new ArrayList<>();

		if (criteria != null) {
			String searchStrLo = "%" + criteria.getQuery().toLowerCase() + "%";
			String searchStrUp = "%" + criteria.getQuery().toUpperCase() + "%";
			String idText = criteria.getQuery();

			if (criteria.getRecordState() != null) {
				filters.add(criteriaBuilder.equal(root.get("recordState"), criteria.getRecordState()));
			}
			if (criteria.getQuery() != null) {
				if (idText.matches("[0-9]+") && idText.length() > 0) {
					Long searchId = Long.parseLong(idText);
					filters.add(criteriaBuilder.equal(root.get("id"), searchId));
					filters.add(criteriaBuilder.equal(root.get("facilityId"), searchId));
				} else if (criteria.getQuery() != null && !criteria.getQuery().isEmpty()) {
					filters.add(criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityName")), searchStrLo),
							criteriaBuilder.like(criteriaBuilder.upper(root.get("facilityName")), searchStrUp)));
				}
			}
		}
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
