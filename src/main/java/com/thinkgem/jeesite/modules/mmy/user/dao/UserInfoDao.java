/**    
 * 文件名：UserInfoDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月7日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.UserInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：UserInfoDao 类描述： 创建人：Administrator 创建时间：2018年6月7日
 * 下午8:04:17 修改人：Administrator 修改时间：2018年6月7日 下午8:04:17 修改备注：
 * 
 * @version
 * 
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {

    /**
     * 
     * insertBatch(批量插入方法)
     * 
     * 
     */
    int insertBatch(List<UserInfo> userList);

    /**
     * 
     * countByLoginname(这里用一句话描述这个方法的作用)
     * 
     * @return
     * 
     * 
     */
    int countByLoginname(UserInfo userInfo);

    /**
     * 
     * delById(根据id删除用户)
     * 
     * 
     */
    int delById(UserInfo userInfo);

}
