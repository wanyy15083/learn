package com.frotly.yycg.business.system.web.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.PageParameter;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.DataGridResultInfo;
import com.frotly.yycg.base.web.action.result.ProcessResult;
import com.frotly.yycg.base.web.action.result.ResultInfo;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysUser;
import com.frotly.yycg.business.system.pojo.SysUserCustom;
import com.frotly.yycg.business.system.pojo.SysUserQueryVo;
import com.frotly.yycg.business.system.service.SystemService;
import com.frotly.yycg.business.system.service.UserService;
import com.hp.hpl.sparta.xpath.ThisNodeTest;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<SysUserQueryVo>{

	//@RequiresPermissions("user:list")
	public String queryuser(){
		SysUserQueryVo sysUserQueryVo = getModel();
		List<Dictinfo> dictinfoByTypecode = serviceFacade.getSystemService().findDictinfoByTypecode("s01");
		sysUserQueryVo.setGroupList(dictinfoByTypecode);
		return "queryuser";
	}
	//@RequiresPermissions("user:list")
	public String queryuser_result(){
		SysUserQueryVo sysUserQueryVo = getModel();
		int page = sysUserQueryVo.getPage();
		int rows = sysUserQueryVo.getRows();
		long total = serviceFacade.getUserService().findSysUserListCount(sysUserQueryVo);
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<SysUser> sysUserList = serviceFacade.getUserService().findSysUserList(sysUserQueryVo, firstResult, rows);
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, sysUserList);
		this.setProcessResult(dataGridResultInfo);
		return "queryuser_result";
	}
	
	public String adduser(){
		SysUserQueryVo sysUserQueryVo = getModel();
		SystemService systemService = serviceFacade.getSystemService();
		List<Dictinfo> groupList = systemService.findDictinfoByTypecode("s01");
		List<Dictinfo> userstateList = systemService.findDictinfoByTypecode("s02");
		sysUserQueryVo.setGroupList(groupList);
		sysUserQueryVo.setUserstateList(userstateList);
		return "adduser";
	}
	
	public String addusersubmit(){
		SysUserQueryVo sysUserQueryVo = getModel();
		serviceFacade.getUserService().insertSysUser(sysUserQueryVo.getSysUserCustom());
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	public String deleteuser(){
		SysUserQueryVo sysUserQueryVo = getModel();
		serviceFacade.getUserService().deleteysUser(sysUserQueryVo.getSysUserCustom().getId());
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
}
