/**    
 * 文件名：GradeInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.mmy.user.dao.GradeInfoDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;

/**
 * 
 * 项目名称：JeeSite 类名称：GradeInfoService 类描述： 创建人：Administrator 创建时间：2018年6月5日
 * 下午3:31:08 修改人：Administrator 修改时间：2018年6月5日 下午3:31:08 修改备注：
 * 
 * @version
 * 
 */
@Service
public class GradeInfoService extends CrudService<GradeInfoDao, GradeInfo> {
    @Autowired
    GradeInfoDao gradeDao;

    /**
     * 
     * findList(获取组别列表分页)
     * 
     */
    public Page<GradeInfo> findList(GradeInfo gradeInfo, Page<GradeInfo> page) {
        gradeInfo.setPage(page);
        page.setList(gradeDao.findList(gradeInfo));
        return page;
    }

    /**
     * 
     * getAll(获取全部的组别信息)
     * 
     * 
     */
    public List<GradeInfo> getAll() {
        return gradeDao.getAll();
    }

    /**
     * 
     * insertGrade(创建新分组)
     * 
     * @return -1、已存在同名的组别，无法插入 0、插入失败 1 成功插入
     * 
     */
    @Transactional(readOnly = false)
    public int insertGrade(GradeInfo gradeInfo) {
        int i = countByName(gradeInfo);
        if (i != 0) {
            return -1;
        }
        gradeInfo.setId(IdGen.uuid());
        gradeInfo.setCreateTime(TimeUtils.formateNowDay2());
        return gradeDao.insert(gradeInfo);
    }

    /**
     * 
     * countByName(根据名称进行计数，delFlag=0)
     * 
     */
    private int countByName(GradeInfo gradeInfo) {
        return gradeDao.countByName(gradeInfo);

    }

    /**
     * updateGradeName(更新组名)
     * 
     * @return -1、已存在同名的组别，无法插入 0、插入失败 1 成功插入
     */
    @Transactional(readOnly = false)
    public int updateGradeName(GradeInfo gradeInfo) {
        int i = countByName(gradeInfo);
        if (i != 0) {
            return -1;
        }
        return gradeDao.update(gradeInfo);
    }

    @Transactional(readOnly = false)
    public int delById(GradeInfo gradeInfo) {
        return gradeDao.delById(gradeInfo);
    }

}
