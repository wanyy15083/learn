package com.frotly.yycg.business.system.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.business.system.entity.SysUser;
import com.frotly.yycg.business.system.entity.Usergys;
import com.frotly.yycg.business.system.entity.Userjd;
import com.frotly.yycg.business.system.entity.Useryy;
import com.frotly.yycg.business.system.pojo.SysUserCustom;
import com.frotly.yycg.business.system.pojo.SysUserQueryVo;

public interface UserService {
	//根据主键查询用户
	public SysUser findSysUserById(String id);
	//根据帐号查询用户
	public SysUser findSysUserByUsercode(String usercode);
	//新增用户
	public void insertSysUser(SysUserCustom sysUserCustom);
	//更新用户
	public void updateSysUser(SysUserCustom sysUserCustom);
	//删除用户
	public void deleteysUser(String userId);
	
	//分页查询数据:firstResult:分页当前页起始下标，0开始；maxResults：本页显示的最大记录数
	public List<SysUser> findSysUserList(SysUserQueryVo sysUserQueryVo,int firstResult,int maxResults);
	//查询用户总记录数
	public Long findSysUserListCount(SysUserQueryVo sysUserQueryVo);
	
	//根据单位名称查询监督单位
	public Userjd findUserjdByMc(String mc);
	//根据单位名称查询医院单位
	public Useryy findUseryyByMc(String mc);
	//根据地区模糊查询医院
	public List<Useryy> findUseryyList(String dq);
	//根据单位名称查询供货商单位
	public Usergys findUsergysByMc(String mc);
	
	//用户登录
	public ActiveUser checkUser(String usercode,String password);
	
	//构建activeUser,不需要校验用户名和密码
	public ActiveUser createActiveUser(String usercode);
}
