package com.frotly.yycg.business.analyze.dao;

import java.util.List;

import com.frotly.yycg.business.analyze.pojo.YybusinssQueryVo;

public interface AnalyzeDao {

	//按区域统计
	public List<Object[]> findSumByArea(YybusinssQueryVo yybusinssQueryVo);
}
