package com.frotly.yycg.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {
	//创建DetachedCriteria
	public DetachedCriteria createDetachedCriteria();
	//根据主键查询
	public T findById(Serializable id);
	//新增
	public void insert(T entity);
	//更新
	public void update(T entity);
	//删除
	public void delete(T entity);
	//分页查询数据
	public List<T> findListPageByCriteria(DetachedCriteria detachedCriteria,int firstResult,int maxResults);
	//查询所有数据
	public List<T> findListByCtriteria(DetachedCriteria detachedCriteria);
	//查询总记录数
	public Long findListCount(DetachedCriteria detachedCriteria);
	//查询单个记录
	public T findSingleObjByCriteria(DetachedCriteria detachedCriteria);
}
