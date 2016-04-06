package com.frotly.yycg.base.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.frotly.yycg.base.dao.ViewDao;

public class ViewDaoImpl extends HibernateDaoSupport implements ViewDao {

	@Override
	public List findListPageBySqlQuery(final String sql, final   List<Object> parameter,
			final  int firstResult, final int maxResults) {
		
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery sqlQuery = session.createSQLQuery(sql);//创建一个原生 sql查询对象
						if(parameter !=null){
							
							for(int i=0;i<parameter.size();i++){
								sqlQuery.setParameter(i, parameter.get(i));//向sql中占位符号设置参数
							}
						}
						

						sqlQuery.setFirstResult(firstResult);//设置分页参数
						sqlQuery.setMaxResults(maxResults); //设置分页参数
						return sqlQuery.list();
					}
					
				});
		return list;
	}
	@Override
	public List findListBySqlQuery(final String sql, final   List<Object> parameter) {
		
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						if(parameter !=null){
							
							for(int i=0;i<parameter.size();i++){
								sqlQuery.setParameter(i, parameter.get(i));
							}
						}

						return sqlQuery.list();
					}
					
				});
		return list;
	}
	@Override
	public Object findSingleObjByQuery(final String sql, final  List<Object> parameter) {
		
		Object object = this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				
				//填充参数
				if(parameter !=null){
					
					for(int i=0;i<parameter.size();i++){
						sqlQuery.setParameter(i, parameter.get(i));
					}
				}
				
				return sqlQuery.uniqueResult();
				
			}
			
		});
		
		return object;
	}
	
}
