package com.frotly.yycg.business.ypml.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.business.ypml.entity.Ypxx;
import com.frotly.yycg.business.ypml.pojo.YpxxCustom;
import com.frotly.yycg.business.ypml.pojo.YpxxQueryVo;
import com.frotly.yycg.business.ypml.service.YpxxService;

@Service
public class YpxxServiceImpl extends BaseService implements YpxxService {

	@Resource(name="ypxxDao")
	private BaseDao<Ypxx> ypxxDao;
	
	@Override
	public List<Ypxx> findYpxxList(YpxxQueryVo ypxxQueryVo) {
		DetachedCriteria detachedCriteria = ypxxDao.createDetachedCriteria();
		this.findYpxxListCondition(detachedCriteria, ypxxQueryVo);
		return ypxxDao.findListByCtriteria(detachedCriteria);
	}

	private void findYpxxListCondition(DetachedCriteria detachedCriteria,YpxxQueryVo ypxxQueryVo){
		if (ypxxQueryVo != null) {
			YpxxCustom ypxxCustom = ypxxQueryVo.getYpxxCustom();
			if (ypxxCustom != null) {
				if (StringUtils.isNotEmpty(ypxxCustom.getPinyin())) {
					detachedCriteria.add(Restrictions.like("pingyin", "%"+ypxxCustom.getPinyin()+"%"));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getJx())) {
					detachedCriteria.add(Restrictions.eq("jx", ypxxCustom.getJx()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getGg())) {
					detachedCriteria.add(Restrictions.eq("gg", ypxxCustom.getGg()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getZhxs())) {
					detachedCriteria.add(Restrictions.eq("zhxs", ypxxCustom.getZhxs()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getBm())) {
					detachedCriteria.add(Restrictions.eq("bm", ypxxCustom.getBm()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getScqymc())) {
					detachedCriteria.add(Restrictions.like("scqymc", "%"+ypxxCustom.getScqymc()+"%"));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getSpmc())) {
					detachedCriteria.add(Restrictions.like("spmc", "%"+ypxxCustom.getSpmc()+"%"));
				}
				if (ypxxCustom.getZbjg() != null) {
					detachedCriteria.add(Restrictions.between("zbjg", ypxxCustom.getZbjglower(), ypxxCustom.getZbjgupper()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getLb())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByLb.id", ypxxCustom.getLb()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getJyzt())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByJyzt.id", ypxxCustom.getJyzt()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getZlcc())) {
					detachedCriteria.add(Restrictions.eq("zlcc", ypxxCustom.getZlcc()));
				}
			}
		}
	}


}
