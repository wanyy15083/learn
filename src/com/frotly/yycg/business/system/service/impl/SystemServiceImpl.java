package com.frotly.yycg.business.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.service.SystemService;

@Service
public class SystemServiceImpl extends BaseService implements SystemService {
	@Resource(name="dictinfoDao")
	private BaseDao<Dictinfo> dictinfoDao;

	@Override
	public List<Dictinfo> findDictinfoByTypecode(String typecode) {
		if (StringUtils.isEmpty(typecode)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		DetachedCriteria detachedCriteria = dictinfoDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("typecode", typecode));
		return dictinfoDao.findListByCtriteria(detachedCriteria);
	}

	@Override
	public Dictinfo findDictinfoById(String id) {
		if (StringUtils.isEmpty(id)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		DetachedCriteria detachedCriteria = dictinfoDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("id", id));
		return dictinfoDao.findById(id);
	}

	@Override
	public Dictinfo findDictinfoByTypecodeAndDictcode(String typecode,
			String dictcode) {
		if (StringUtils.isEmpty(typecode) || StringUtils.isEmpty(dictcode)) {
			
		}
		DetachedCriteria detachedCriteria = dictinfoDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("typecode", typecode));
		detachedCriteria.add(Restrictions.eq("dictcode", dictcode));
		return dictinfoDao.findSingleObjByCriteria(detachedCriteria);
	}

}
