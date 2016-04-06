package com.frotly.yycg.base.dao;

import java.util.List;

public interface ViewDao {
	//原生 sql查询列表，实现分页
	public List findListPageBySqlQuery(final String sql,final List<Object> parameter, final int page,final int pageSize);
	//原生 sql查询列表，不实现分页
	public List findListBySqlQuery(final String sql,final List<Object> parameter);
	//原生 sql查询返回单个对象
	public Object findSingleObjByQuery(final String sql, final List<Object> parameter);
}
