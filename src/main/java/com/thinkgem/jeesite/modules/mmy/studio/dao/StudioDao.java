/**    
 * 文件名：StudioDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月25日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.studio.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.studio.entity.StudioInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：StudioDao 类描述： 创建人：Administrator 创建时间：2018年6月25日
 * 下午1:47:37 修改人：Administrator 修改时间：2018年6月25日 下午1:47:37 修改备注：
 * 
 * @version
 * 
 */
@MyBatisDao
public interface StudioDao extends CrudDao<StudioInfo> {
    public List<StudioInfo> getByLessionIdAndUserId(StudioInfo studio);

    /**
     * 
     * point(更新分数)
     * 
     */
    public int point(StudioInfo studio);
}