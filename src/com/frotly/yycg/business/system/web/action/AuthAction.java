package com.frotly.yycg.business.system.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.PageParameter;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.DataGridResultInfo;
import com.frotly.yycg.base.web.action.result.ResultInfo;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysPermission;
import com.frotly.yycg.business.system.entity.SysRole;
import com.frotly.yycg.business.system.pojo.SysRoleCustom;
import com.frotly.yycg.business.system.pojo.SysRoleQueryVo;


@Controller
@Scope("prototype")
public class AuthAction extends BaseAction<SysRoleQueryVo> {

	public String rolelist(){
		return "rolelist";
	}
	
	public String rolelist_result(){
		SysRoleQueryVo sysRoleQueryVo = getModel();
		long total = serviceFacade.getAuthService().findSysRoleCount();
		int page = sysRoleQueryVo.getPage();
		int rows = sysRoleQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<SysRole> sysRoleList = serviceFacade.getAuthService().findSysRoleList(sysRoleQueryVo, firstResult, rows);
		List<SysRoleCustom> sysRoleCustomList = new ArrayList<SysRoleCustom>();
		for (SysRole sysRole : sysRoleList) {
			SysRoleCustom sysRoleCustom = new SysRoleCustom();
			BeanUtils.copyProperties(sysRole, sysRoleCustom);
			Dictinfo dictinfoByGroupid = serviceFacade.getSystemService().findDictinfoById(sysRole.getGroupid());
			sysRoleCustom.setDictinfoByGroupid(dictinfoByGroupid);
			sysRoleCustomList.add(sysRoleCustom);
		}
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, sysRoleCustomList);
		this.setProcessResult(dataGridResultInfo);
		return "processResult";
	}
	
	public String roleauth(){
		SysRoleQueryVo sysRoleQueryVo = getModel();
		List<SysPermission> sysPermissionListByRoleId = serviceFacade.getAuthService().findSysPermissionByRoleId(sysRoleQueryVo.getRoleId());
		StringBuffer sb = new StringBuffer();
		for (SysPermission sysPermission : sysPermissionListByRoleId) {
			String permissionid = sysPermission.getId();
			if (permissionid != null && permissionid != "") {
				sb.append(permissionid+",");
			}
		}
		sysRoleQueryVo.setSysPermissionsByRoleId(sb.toString());
		return "roleauth";
	}
	
	public String permissionall(){
		SysRoleQueryVo sysRoleQueryVo = getModel();
		List<SysPermission> sysPermissionList = serviceFacade.getAuthService().findSysPermissionList();
		sysRoleQueryVo.setSysPermissionList(sysPermissionList);
		return "permissionall";
	}
	
	public String roleauthsubmit(){
		SysRoleQueryVo sysRoleQueryVo = getModel();
		String roleId = sysRoleQueryVo.getRoleId();
		String sysPermissionsByRoleId = sysRoleQueryVo.getSysPermissionsByRoleId();
		List<String> sysPermissionids = new ArrayList<String>();
		String[] split = sysPermissionsByRoleId.split(",");
		for (int i = 0; i < split.length; i++) {
			sysPermissionids.add(split[i]);
		}
		serviceFacade.getAuthService().saveRoleAuthorize(roleId, sysPermissionids);
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
}
