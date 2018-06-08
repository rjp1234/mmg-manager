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

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static final Lock loginnameLock = new ReentrantLock();

    /**
     * 
     * getById(根据id获得对应的用户息)
     * 
     */
    public UserInfo getById(String id) {
        return userInfoDao.get(id);
    }

    @Transactional(readOnly = false)
    public int insertBatch(List<UserInfo> userList) {
        return userInfoDao.insertBatch(userList);
    }

    public int countByLoginname(String loginname) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginname(loginname);
        loginnameLock.lock();
        int i = 0;
        try {
            i = userInfoDao.countByLoginname(userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            loginnameLock.unlock();
        }
        return i;

    }

    /**
     * 
     * delById(这里用一句话描述这个方法的作用)
     * 
     */
    @Transactional(readOnly = false)
    public int delById(String id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfoDao.delById(userInfo);

    }

}
