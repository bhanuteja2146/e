package com.clinistats.hepdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.hepdesk.config.filter.UserLogFilter;
import com.clinistats.hepdesk.model.UnLockAuditModel;

public class UnlockedLogSpecification implements Specification<UnLockAuditModel>{
	private static final long serialVersionUID = 1L;
	private final UserLogFilter criteria;
	private List<Predicate> filters;
	
	public UnlockedLogSpecification(UserLogFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<UnLockAuditModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		
		filters = new ArrayList<>();
		if (criteria != null) 
		{	
			if(criteria.getQuery()!=null  && !"".equals(criteria.getQuery())) {
				Predicate userName = criteriaBuilder.like(criteriaBuilder.lower(root.get("userName")), "%" + criteria.getQuery().toLowerCase() + "%");
				filters.add(criteriaBuilder.or(userName));
			}			
			
		}
		query.distinct(true);
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}