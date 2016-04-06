package com.frotly.yycg.base.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.frotly.yycg.base.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	private Class<T> entityClass;
	public BaseDaoImpl(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	
	@Override
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass);
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public void insert(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public List<T> findListPageByCriteria(DetachedCriteria detachedCriteria,
			int firstResult, int maxResults) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public List<T> findListByCtriteria(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	@Override
	public Long findListCount(DetachedCriteria detachedCriteria) {
		detachedCriteria.setProjection(Projections.rowCount());
		List list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = (Long) list.get(0);
		detachedCriteria.setProjection(null);
		detachedCriteria.setResultTransformer(detachedCriteria.ROOT_ENTITY);
		return total;
	}

	@Override
	public T findSingleObjByCriteria(DetachedCriteria detachedCriteria) {
		List<T> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

}
