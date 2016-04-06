package com.frotly.yycg.business.ypml.service;

import java.util.List;

import com.frotly.yycg.business.ypml.entity.Ypxx;
import com.frotly.yycg.business.ypml.pojo.YpxxQueryVo;

public interface YpxxService {

	/**
	 * 查询所有药品信息
	 * @param ypxxQueryVo
	 * @return
	 */
	public List<Ypxx> findYpxxList(YpxxQueryVo ypxxQueryVo);
	
}
