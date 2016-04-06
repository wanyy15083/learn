package com.frotly.yycg.hessian.server;

import java.util.List;

public interface YpxxService {

	//省级药品目录查询不分页
	public List<Ypxx> findYpxxList()throws Exception;
}
