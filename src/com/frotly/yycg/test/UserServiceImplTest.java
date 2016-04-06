package com.frotly.yycg.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.frotly.yycg.business.system.entity.SysUser;
import com.frotly.yycg.business.system.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-*.xml"})
public class UserServiceImplTest {

	@Autowired
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindSysUserById() {
		SysUser sysUser = userService.findSysUserById("70");
		System.out.println(sysUser);
	}

	@Test
	public void testFindSysUserByUsercode() {
		SysUser sysUser = userService.findSysUserByUsercode("gszmdc");
		System.out.println(sysUser);
	}

}
