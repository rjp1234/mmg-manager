/**    
 * 文件名：UserInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月7日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mmy.user.dao.UserInfoDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.UserInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：UserInfoService 类描述： 创建人：Administrator 创建时间：2018年6月7日
 * 下午8:04:00 修改人：Administrator 修改时间：2018年6月7日 下午8:04:00 修改备注：
 * 
 * @version
 * 
 */
@Service
public class UserInfoService extends CrudService<UserInfoDao, UserInfo> {
    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 
     * getById(根据id获得对应的用户息)
     * 
     */
    public UserInfo getById(String id) {
        return userInfoDao.get(id);
    }

}
