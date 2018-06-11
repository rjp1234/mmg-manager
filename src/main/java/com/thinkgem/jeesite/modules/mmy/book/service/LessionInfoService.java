/**    
 * 文件名：LessionInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月11日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mmy.book.dao.LessionInfoDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionInfoService 类描述： 创建人：Administrator
 * 创建时间：2018年6月11日 上午10:04:27 修改人：Administrator 修改时间：2018年6月11日 上午10:04:27 修改备注：
 * 
 * @version
 * 
 */
@Service
public class LessionInfoService extends CrudService<LessionInfoDao, LessionInfo> {
    private static final Lock countLock = new ReentrantLock();

    /**
     * 
     * getById(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public LessionInfo getById(String id) {
        LessionInfo lessionInfo = new LessionInfo();
        lessionInfo.setId(id);
        return dao.getById(lessionInfo);
    }

    /**
     * 
     * insertLession
     * 
     * @return 1说明插入成功。-1说明已经存在同名课程
     * 
     */
    @Transactional(readOnly = false)
    public int insertLession(LessionInfo lessionInfo) {
        countLock.lock();
        try {
            int count = countLessionByName(lessionInfo.getName());
            if (count == 0) {
                return dao.insert(lessionInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            countLock.unlock();
        }
        return -1;

    }

    /**
     * 
     * countLessionByName(计算同名教材，不允许超过1)
     * 
     * 
     */
    public int countLessionByName(String name) {
        LessionInfo lessionInfo = new LessionInfo();
        lessionInfo.setName(name);
        return dao.countByName(lessionInfo);
    }

}
