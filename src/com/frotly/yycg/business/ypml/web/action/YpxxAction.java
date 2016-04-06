package com.frotly.yycg.business.ypml.web.action;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;
import com.frotly.yycg.business.ypml.entity.Ypxx;
import com.frotly.yycg.business.ypml.pojo.YpxxQueryVo;
import com.frotly.yycg.hessian.server.YpxxService;
import com.frotly.yycg.util.ExcelExportSXXSSF;
import com.frotly.yycg.util.HxlsOptRowsInterface;
import com.frotly.yycg.util.HxlsRead;

@Controller
@Scope("prototype")
public class YpxxAction extends BaseAction<YpxxQueryVo> {
	
	@Autowired
	private HxlsOptRowsInterface ypxxImportService;
	
	//注入hessian服务的代理对象
	@Autowired
	private YpxxService ypxxServiceProxy;
	
	//进入药品目录同步页面
	public String sysnc(){
		
		return "sysnc";
	}
	
	//药品目录同步提交
	public String sysncsubmit(){
		//调用代理对象
		List<com.frotly.yycg.hessian.server.Ypxx> ypxxList = null;
		try {
			ypxxList = ypxxServiceProxy.findYpxxList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将查到的药品信息插入到市平台Ypxx表中
		return "processResult";
	}

	//进入到处页面
	public String export(){
		
		return "export";
	}
	
	//导出页面数据提交
	public String exportsubmit(){
		YpxxQueryVo ypxxQueryVo = this.getModel();
		
		//调用service查询药品信息
		List<Ypxx> list = serviceFacade.getYpxxService().findYpxxList(ypxxQueryVo);
		//通过工具类导出excel
		//导出文件存放的路径，并且是虚拟目录指向的路径
		String filePath = "c:/temp/";
		//导出文件的前缀
		String filePrefix="ypxx";
		//-1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
		int flushRows=100;
		
		//指导导出数据的title
		List<String> fieldNames=new ArrayList<String>();
		fieldNames.add("流水号");
		fieldNames.add("通用名");
		fieldNames.add("生产企业名称");
		fieldNames.add("商品名称");
		fieldNames.add("剂型");
		fieldNames.add("规格");
		fieldNames.add("转换系数");
		fieldNames.add("中标价格");
		fieldNames.add("交易状态");
		
		//告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
		List<String> fieldCodes=new ArrayList<String>();
		fieldCodes.add("bm");//药品流水号
		fieldCodes.add("mc");//通用名
		fieldCodes.add("scqymc");//生产企业名称
		fieldCodes.add("spmc");//商品名称
		fieldCodes.add("jx");//剂型
		fieldCodes.add("gg");//规格
		fieldCodes.add("zhxs");//转换系数
		
		fieldCodes.add("zbjg");//中标价格
		fieldCodes.add("dictinfoByJyzt.info");//交易状态
		
		//注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应

		
		//开始导出，执行一些workbook及sheet等对象的初始创建
		ExcelExportSXXSSF excelExportSXXSSF = null;
		//输出文件，返回下载文件的http地址
		String webpath = null;
		try {
			excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
			
			//执行导出
			excelExportSXXSSF.writeDatasByObject(list);
			//输出文件，返回下载文件的http地址
			webpath = excelExportSXXSSF.exportFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//返回成功信息
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil
				.createSuccess(Config.MESSAGE, 313, new Object[] { list.size(),
						webpath })));
		return "processResult";
	}
	
	//进入导入页面
	public String importypxx(){
		
		return "importypxx";
	}
	//导入数据提交
	public String importypxxsubmit(){
		YpxxQueryVo ypxxQueryVo = this.getModel();
		//确定上传文件临时目录 
		String filePath = "c:/temp/";
		//获取上传文件的原始名称
		String ypxximportfileFileName = ypxxQueryVo.getYpxximportfileFileName();
		
		//拼接一个新文件名称，避免和上传目录中的文件重名
		String fileName_new = UUID.randomUUID()
				+ ypxximportfileFileName.substring(ypxximportfileFileName
						.lastIndexOf("."));
		//上传成功的文件（在内存中）
		File ypxximportfile = ypxxQueryVo.getYpxximportfile();
		
		//创建一个file
		File fileNew = new File(filePath+fileName_new);
		
		//复制文件，将ypxximportfile上传成功的临时 文件复制到指定目录中
		HxlsRead xls2csv = null;
		try {
			FileUtils.copyFile(ypxximportfile, fileNew);
			
			//使用工具类读取fileNew文件内容
			//第一个参数就是导入的文件
			//第二个参数就是导入文件中哪个sheet
			//第三个参数导入接口的实现类对象
			xls2csv = new HxlsRead(fileNew.getAbsolutePath(),1,ypxxImportService);
			//执行导入
			xls2csv.process();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		//获取导入的结果信息，返回到页面
		//导入成功数量
		long optRows_success = xls2csv.getOptRows_success();
		//导入失败数量
		long optRows_failure = xls2csv.getOptRows_failure();
		
		//获取导入失败信息
		List<String> failmsgs = xls2csv.getFailmsgs();
		//失败信息对应的失败记录
		List<List<String>> failrows = xls2csv.getFailrows();//对应原始导入文件内容
		
		//将导入失败原因和失败记录导出一个excel文件，提示用户下载
		//....
		
		
		//创建提示信息对象
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 314, new Object[]{optRows_success,optRows_failure}));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
}
