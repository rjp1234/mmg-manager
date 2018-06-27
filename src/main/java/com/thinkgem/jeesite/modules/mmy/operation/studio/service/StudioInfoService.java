/**    
 * 文件名：StudioInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月25日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.operation.studio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.mmy.operation.studio.dao.StudioDao;
import com.thinkgem.jeesite.modules.mmy.operation.studio.entity.StudioInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：StudioInfoService 类描述： 创建人：Administrator 创建时间：2018年6月25日
 * 下午1:48:27 修改人：Administrator 修改时间：2018年6月25日 下午1:48:27 修改备注：
 * 
 * @version
 * 
 */
@Service
public class StudioInfoService extends CrudService<StudioDao, StudioInfo> {

    /**
     * 
     * getPage(根据条件获得对应的分页参数)
     * 
     * 
     * @param name
     * 
     * @param @return
     *            设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     * 
     */
    public Page<StudioInfo> getPage(Page<StudioInfo> page, StudioInfo studioInfo) {
        studioInfo.setPage(page);
        page.setList(dao.findList(studioInfo));
        return page;

    }

    /**
     * 
     * getByLessionIdAndUserId(根据用户id、课文id去查找对应的录音记录)
     * 
     * @throws 当查询出来的个数大于1时，不符合现有逻辑，需查询造成脏数据的原因并解决
     * 
     * @当课程下可能存在复数录音文件时，该方法需要重写
     */
    public StudioInfo getByLessionIdAndUserId(String userId, String lessionId) throws Exception {
        StudioInfo studio = new StudioInfo();
        studio.setLessionId(lessionId);
        studio.setUserId(userId);
        List<StudioInfo> list = dao.getByLessionIdAndUserId(studio);
        if (list.size() > 1) {
            throw new RuntimeException(
                    "multiple studio record is exits in database check it and delete the wrong data:userId=" + userId
                            + ";lessionId=" + lessionId);
        }

        return list.size() == 1 ? list.get(1) : null;

    }

    @Transactional(readOnly = false)
    public int pointStudio(String studioId, String comment, int point, String pointer) {
        StudioInfo studio = new StudioInfo();
        studio.setId(studioId);
        studio.setComment(comment);
        studio.setPoint(String.valueOf(point));
        studio.setPointer(pointer);
        studio.setPointTime(TimeUtils.formateNowDay2());
        return dao.point(studio);

    }

    @Transactional(readOnly = false)
    public int delete(String id) {
        StudioInfo studio = new StudioInfo();
        studio.setId(id);
        return dao.delete(studio);

    }

    /**
     * 
     * getNextUnpointStudio(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public StudioInfo getNextUnpointStudio(String lessionId, String classId) {
        StudioInfo studio = new StudioInfo();
        studio.setLessionId(lessionId);
        studio.setClassId(classId);
        return dao.getNextUnpointStudio(studio);

    }

    /**
     * 
     * getById(这里用一句话描述这个方法的作用)
     * 
     */
    public StudioInfo getById(String id) {
        StudioInfo studio = new StudioInfo();
        studio.setId(id);
        return dao.get(studio);
    }

}
