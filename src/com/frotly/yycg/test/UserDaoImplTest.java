package com.frotly.yycg.test;

import org.springframework.context.ApplicationContext;

public class UserDaoImplTest {

	private ApplicationContext context;
	
	/*
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml","classpath:spring/applicationContext-*.xml");
	}

	@Test
	public void testFindSysUserById() {
		UserDao userDao =  (UserDao) context.getBean("userDao");
		SysUser sysUser = userDao.findSysUserById("70");
		System.out.println(sysUser);
	}

	@Test
	public void testFindSysUserByCriteria() {
		UserDao userDao =  (UserDao) context.getBean("userDao");
		DetachedCriteria criteria = userDao.createDetachedCriteria();
		criteria.add(Restrictions.like("username", "CC镇%"));
		List<SysUser> list = userDao.findSysUserByCriteria(criteria);
		System.out.println(list);
	}
*/
}
