package com.frotly.yycg.business.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.business.analyze.dao.AnalyzeDao;
import com.frotly.yycg.business.analyze.entity.Yybusiness;
import com.frotly.yycg.business.analyze.pojo.YybusinessCustom;
import com.frotly.yycg.business.analyze.pojo.YybusinssQueryVo;
import com.frotly.yycg.business.analyze.service.BusinessService;

@Service
public class BusinessServiceImpl extends BaseService implements BusinessService {

	@Resource(name="businessDao")
	private BaseDao<Yybusiness> businessDao;
	@Autowired
	private AnalyzeDao analyzeDao;
	
	@Override
	public Yybusiness findYybusinessByYycgdidAndYpxxid(Integer yycgdid,
			String ypxxid) {
		DetachedCriteria detachedCriteria = businessDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("yycgd.id", yycgdid));
		detachedCriteria.add(Restrictions.eq("ypxx.id", ypxxid));
		return businessDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public List<YybusinessCustom> findSumByArea(
			YybusinssQueryVo yybusinssQueryVo) {
		List<Object[]> list = analyzeDao.findSumByArea(yybusinssQueryVo);
		List<YybusinessCustom> listCustoms = new ArrayList<YybusinessCustom>();
		for (Object[] objects : list) {
			YybusinessCustom yybusinessCustom = new YybusinessCustom();
			yybusinessCustom.setAreaid(objects[0].toString());
			yybusinessCustom.setAreaname(objects[1].toString());
			yybusinessCustom.setCgje(Float.parseFloat(objects[2].toString()));
			listCustoms.add(yybusinessCustom);
		}
		
		return listCustoms;
	}

}
