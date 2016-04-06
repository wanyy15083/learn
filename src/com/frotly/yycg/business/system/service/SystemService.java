package com.frotly.yycg.business.system.service;


import java.util.List;

import com.frotly.yycg.business.system.entity.Dictinfo;

/**
 * 系统管理
 * @author Frotly
 *
 */
public interface SystemService {
	//根据typecode查询字典明细
	public List<Dictinfo> findDictinfoByTypecode(String typecode);
	//根据数据字典明细id查询对应字典信息
	public Dictinfo findDictinfoById(String id);
	/**
	 * 根据数据字典的类型代码和业务代码查询一条记录
	 * @param typecode
	 * @param dictcode
	 * @return
	 */
	public Dictinfo findDictinfoByTypecodeAndDictcode(String typecode,String dictcode);
	
}
