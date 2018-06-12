/**    
 * 文件名：lessionClassBindService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月12日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.mmy.book.dao.LessionClassBindDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionClassBindInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * 项目名称：mmg-manager 类名称：lessionClassBindService 类描述： 创建人：Administrator
 * 创建时间：2018年6月12日 上午11:23:18 修改人：Administrator 修改时间：2018年6月12日 上午11:23:18 修改备注：
 * 
 * @version
 * 
 */
@Service
public class LessionClassBindService extends CrudService<LessionClassBindDao, LessionClassBindInfo> {

    /**
     * 
     * lessionBindClass(课文下发给对应的班级)
     * 
     * 
     */
    @Transactional(readOnly = false)
    public synchronized int lessionBindClass(String lessionId, String classId) {
        int i = countSameBind(classId, lessionId);
        if (i > 0) {
            return -1;
        }
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setId(IdGen.uuid());
        bind.setCreateTime(TimeUtils.formateNowDay2());
        bind.setCreater(UserUtils.getUser().getId());
        bind.setLessionId(lessionId);
        bind.setClassId(classId);
        return dao.lessionBindClass(bind);
    }

    /**
     * 
     * getByLessionId(根据课文id获得下发的班级id)
     * 
     * 
     */
    public List<LessionClassBindInfo> getByLessionId(String lessionId) {
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setLessionId(lessionId);
        return dao.getByLessionId(bind);
    }

    /**
     * 
     * getByClassId(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public List<LessionClassBindInfo> getByClassId(String classId) {
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setClassId(classId);
        return dao.getByClassId(bind);
    }

    /**
     * 
     * countSameBind(根据班级id和课文id确认是否存在对应的记录id)
     * 
     * 
     */
    public int countSameBind(String classId, String lessionId) {
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setClassId(classId);
        bind.setLessionId(lessionId);
        return dao.countSameBind(bind);
    }

    /**
     * 
     * delById(删除下发)
     * 
     */
    @Transactional(readOnly = false)
    public int delById(String id) {
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setId(id);
        return dao.delById(bind);

    }

    /**
     * 
     * getById(获取下发记录)
     * 
     */
    public LessionClassBindInfo getById(String id) {
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setId(id);
        return dao.getById(bind);
    }

    @Transactional(readOnly = false)
    public int delByClassIdAndLessionId(String classId, String lessionId) {
        LessionClassBindInfo bind = new LessionClassBindInfo();
        bind.setClassId(classId);
        bind.setLessionId(lessionId);
        return dao.delByClassIdAndLessionId(bind);
    }
}
