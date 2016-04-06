package com.frotly.yycg.business.ypml.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.ypml.entity.Ypxx;
import com.frotly.yycg.util.HxlsOptRowsInterface;

/**
 * 导入药品信息
 * @author Frotly
 *
 */
@Service
public class YpxxImportServiceImpl extends BaseService implements
		HxlsOptRowsInterface {
	
	@Resource(name="ypxxDao")
	private BaseDao<Ypxx> ypxxDao;

	
	//此方法由HxlsRead调用，在通过HSSF事件读取excel文件每一行结束时调用
	/**
	 * @param sheetIndex sheet表格序号
	 * @param curRow 行的序号
	 * @param rowlist 每一行的数据
	 * @return 如果保存成功返回success串，否则 返回失败原因
	 * @throws Exception
	 * @see cn.itcast.yycg.util.HxlsOptRowsInterface#saveOptRows(int, int, java.util.List)
	 */
	@Override
	public String saveOptRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		//从rowlist中解析出一行数据
		//按照模板中标识 的列的顺序去解析，因为用户按照模板去导入数据
		//通用名	剂型	规格	转换系数	中标价格	生产企业	商品名	交易状态(1：正常,2：暂停) 单位
		
		String mc = rowlist.get(0);//通用名
		//对导入的数据进行校验，校验格式是否合法，必埴项
		//....
		String jx = rowlist.get(1);//剂型	
		String gg = rowlist.get(2);//规格
		String zhxs = rowlist.get(3);//转换系数
		Float zbjg = Float.parseFloat(rowlist.get(4));//中标价格
		String scqymc = rowlist.get(5);//生产企业
		String spmc = rowlist.get(6);//商品名
		String jyzt = rowlist.get(7);//交易状态(1：正常,2：暂停)
		if(jyzt==null ||(!jyzt.equals("1") && !jyzt.equals("2"))){
			
			return "交易状态输入错误";
		}
		String dw = rowlist.get(8);
		Ypxx ypxx = new Ypxx();
		//通用名	剂型	规格	转换系数	中标价格	生产企业	商品名	交易状态(1：正常,2：暂停)
		ypxx.setMc(mc);
		ypxx.setJx(jx);
		ypxx.setGg(gg);
		ypxx.setZhxs(zhxs);
		ypxx.setZbjg(zbjg);
		ypxx.setScqymc(scqymc);
		ypxx.setSpmc(spmc);
		//根据交易状态业务代码查询dictinfo记录
		Dictinfo dictinfoByJyzt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("003", jyzt);
		ypxx.setDictinfoByJyzt(dictinfoByJyzt);
		ypxx.setDw(dw);
		ypxxDao.insert(ypxx);
		
		return "success";
	}

}
