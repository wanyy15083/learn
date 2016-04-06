package com.frotly.yycg.business.analyze.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frotly.yycg.base.dao.impl.ViewDaoImpl;
import com.frotly.yycg.business.analyze.dao.AnalyzeDao;
import com.frotly.yycg.business.analyze.pojo.YybusinessCustom;
import com.frotly.yycg.business.analyze.pojo.YybusinssQueryVo;

@Repository
public class AnalyzeDaoImpl extends ViewDaoImpl implements AnalyzeDao {

	// 由于继承了HibernateDaoSupport需要注入sessionFactory
	@Autowired
	public void setSF(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Object[]> findSumByArea(YybusinssQueryVo yybusinssQueryVo) {

		// 要执行的sql
		/*
		 * select n.parentid,sys_area.areaname,n.cgje from ( select
		 * m.parentid,sum(m.cgje)cgje from ( select (select sys_area.parentid
		 * from sys_area where useryy.dq = sys_area.id) parentid,yybusiness.cgje
		 * from yybusiness, useryy where yybusiness.useryyid = useryy.id )m
		 * group by m.parentid )n,sys_area where n.parentid = sys_area.id
		 */

		// 创建一个参数的list，此list的长度和上边拼接查询条件用的参数的个数以及参数位置保持一致
		List<Object> parameter = new ArrayList<Object>();
		// 在此方法拼接上边的sql，如果有查询条件带上查询条件
		StringBuffer sql = new StringBuffer();
		sql.append("select n.parentid,sys_area.areaname,n.cgje from ( ");
		sql.append("select m.parentid,sum(m.cgje)cgje from ( ");
		sql.append(" select (select sys_area.parentid  from sys_area  ");
		sql.append(" where useryy.dq = sys_area.id) parentid,yybusiness.cgje ");
		sql.append(" from yybusiness, useryy ");
		sql.append("where yybusiness.useryyid = useryy.id ");

		// 如果有查询条件，这里要进行拼接
		if (yybusinssQueryVo != null) {
			YybusinessCustom yybusinessCustom = yybusinssQueryVo
					.getYybusinessCustom();
			if (yybusinessCustom != null) {
				if (yybusinessCustom.getStartDate() != null) {
					// 拼接起始时间
					// 拼接sql语句
					sql.append(" and yybusiness.tjtime >? ");
					parameter.add(yybusinessCustom.getStartDate());// 将放入占位符号中的参数填充到list
				}
				if (yybusinessCustom.getEndDate() != null) {
					// 拼接截止时间
					// 拼接sql语句
					sql.append(" and yybusiness.tjtime <? ");
					parameter.add(yybusinessCustom.getEndDate());// 将放入占位符号中的参数填充到list
				}

			}

		}
		sql.append(")m ");
		sql.append("group by m.parentid ");
		sql.append(")n,sys_area where n.parentid = sys_area.id ");

		return this.findListBySqlQuery(sql.toString(), parameter);
	}
}
