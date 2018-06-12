/**    
 * 文件名：LessionClassBindDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月12日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionClassBindInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionClassBindDao 类描述： 创建人：Administrator
 * 创建时间：2018年6月12日 上午11:25:53 修改人：Administrator 修改时间：2018年6月12日 上午11:25:53 修改备注：
 * 
 * @version
 * 
 */
@MyBatisDao
public interface LessionClassBindDao extends CrudDao<LessionClassBindInfo> {

    /**
     * 
     * lessionBindClass(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    int lessionBindClass(LessionClassBindInfo bind);

    List<LessionClassBindInfo> getByLessionId(LessionClassBindInfo bind);

    List<LessionClassBindInfo> getByClassId(LessionClassBindInfo bind);

    int countSameBind(LessionClassBindInfo bind);

    /**
     * 
     * delById(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    int delById(LessionClassBindInfo bind);

    int delByClassIdAndLessionId(LessionClassBindInfo bind);

    LessionClassBindInfo getById(LessionClassBindInfo bind);

}
