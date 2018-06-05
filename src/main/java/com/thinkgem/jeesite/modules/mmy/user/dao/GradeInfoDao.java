/**    
 * 文件名：GradeDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;

/**
 * 
 * 项目名称：JeeSite 类名称：GradeDao 类描述： 创建人：Administrator 创建时间：2018年6月5日 下午3:32:20
 * 修改人：Administrator 修改时间：2018年6月5日 下午3:32:20 修改备注：
 * 
 * @version
 * 
 */
@MyBatisDao
public interface GradeInfoDao extends CrudDao<GradeInfo> {

    /**
     * 
     * getAll(获取全部的组别信息)
     * 
     * 
     */
    public List<GradeInfo> getAll();

    /**
     * countByName(查询名称对应的组别是否已经存在)
     */
    public int countByName(GradeInfo gradeInfo);

    /**
     * 
     * delById(这里用一句话描述这个方法的作用)
     * 
     */
    public int delById(GradeInfo gradeInfo);

}
