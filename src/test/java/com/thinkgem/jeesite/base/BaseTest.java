/**    
 * 文件名：BaseTest.java    
 *    
 * 版本信息：    
 * 日期：2018年4月21日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 项目名称：JeeSite 类名称：BaseTest 类描述： 创建人：Administrator 创建时间：2018年4月21日 下午7:25:03
 * 修改人：Administrator 修改时间：2018年4月21日 下午7:25:03 修改备注：
 * 
 * @version
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/spring-context*.xml", "classpath*:/spring-mvc.xml" })
// 添加注视@Transactional 回滚对数据库操作
@Transactional
public class BaseTest {

}
