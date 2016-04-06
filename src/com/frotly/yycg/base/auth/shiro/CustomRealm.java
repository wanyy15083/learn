package com.frotly.yycg.base.auth.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.service.ServiceFacade;
import com.frotly.yycg.business.system.entity.SysPermission;
import com.frotly.yycg.business.system.entity.SysUser;

public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	private ServiceFacade serviceFacade;
	
	
	//提供自定义的realm的名称
	@Override
	public String getName() {
		return "customRealm";
	}

	//获取 用户认证后的身份信息（包括账号和密码。。。）
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String usercode = (String) token.getPrincipal();
		SysUser sysUser = serviceFacade.getUserService().findSysUserByUsercode(usercode);
		if (sysUser == null) {
			return null;
		}
		String password = sysUser.getPwd();
		ActiveUser activeUser = serviceFacade.getUserService().createActiveUser(usercode);
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password, getName());
		return simpleAuthenticationInfo;
		
	}
	
	//获取授权信息（权限信息）
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
		List<String> permissions = new ArrayList<String>();
		List<SysPermission> sysPermissionList = activeUser.getSysPermissionList();
		for (SysPermission sysPermission : sysPermissionList) {
			String permission_key = sysPermission.getVchar1();
			if (StringUtils.isNotEmpty(permission_key)) {
				permissions.add(permission_key);
				
			}
		}
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
		
	}

	

}
