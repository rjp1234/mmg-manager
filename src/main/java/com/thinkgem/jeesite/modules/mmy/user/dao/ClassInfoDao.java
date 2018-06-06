/**    
 * 文件名：ClassInfoDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月6日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;

/**
 * 
 * 项目名称：JeeSite 类名称：ClassInfoDao 类描述： 创建人：Administrator 创建时间：2018年6月6日 下午3:48:58
 * 修改人：Administrator 修改时间：2018年6月6日 下午3:48:58 修改备注：
 * 
 * @version
 * 
 */
@MyBatisDao
public interface ClassInfoDao extends CrudDao<ClassInfo> {

    /**
     * 
     * getByGradeId(根据年级获得旗下所有班级)
     * 
     * 
     */
    public List<ClassInfo> getByGradeId(ClassInfo classInfo);

    /**
     * 
     * updateClassName(更改班级名)
     * 
     */
    public int updateClassName(ClassInfo classInfo);

    /**
     * 
     * countByClassName(计算指定名称的的班级数)
     * 
     * 
     */
    public int countByClassName(ClassInfo classInfo);

    /**
     * 
     * delById(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public int delById(ClassInfo classInfo);

}
