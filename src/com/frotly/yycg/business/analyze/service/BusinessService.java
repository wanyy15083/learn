package com.frotly.yycg.business.analyze.service;

import java.util.List;

import com.frotly.yycg.business.analyze.entity.Yybusiness;
import com.frotly.yycg.business.analyze.pojo.YybusinessCustom;
import com.frotly.yycg.business.analyze.pojo.YybusinssQueryVo;

public interface BusinessService {

	/**
	 * 根据采购单id和药品信息id查询一条交易记录
	 * @param yycgdid
	 * @param ypxxid
	 * @return
	 */
	public Yybusiness findYybusinessByYycgdidAndYpxxid(Integer yycgdid,String ypxxid);
	//按区域统计
	public List<YybusinessCustom> findSumByArea(YybusinssQueryVo yybusinssQueryVo);
}
