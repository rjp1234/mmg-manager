/**    
 * 文件名：LessionInfoDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月11日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionInfoDao 类描述： 创建人：Administrator 创建时间：2018年6月11日
 * 上午9:54:43 修改人：Administrator 修改时间：2018年6月11日 上午9:54:43 修改备注：
 * 
 * @version
 * 
 */
@MyBatisDao
public interface LessionInfoDao extends CrudDao<LessionInfo> {

    /**
     * 
     * getById(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    LessionInfo getById(LessionInfo lessionInfo);

    /**
     * 
     * countLessionByName(这里用一句话描述这个方法的作用)
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     * 
     */
    int countByName(LessionInfo lessionInfo);

}
