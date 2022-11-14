package com.clinistats.helpdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.helpdesk.config.filter.LogUserFilter;
import com.clinistats.helpdesk.model.LogInSettingsLogModel;

public class LogInSettingsLogSpecification implements Specification<LogInSettingsLogModel> {
	private static final long serialVersionUID = 1L;
	private final LogUserFilter criteria;
	private List<Predicate> filters;

	public LogInSettingsLogSpecification(LogUserFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<LogInSettingsLogModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {

		filters = new ArrayList<>();
		if (criteria != null) {

			if (criteria.getLockUserIds() != null && criteria.getLockUserIds().size() > 0)
				filters.add(criteriaBuilder.in(root.get("lockUserId")).value(criteria.getLockUserIds()));

			if (criteria.getStatus() != null)
				filters.add(criteriaBuilder.equal(root.get("recordState"), criteria.getStatus()));
		}

		query.distinct(true);
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}