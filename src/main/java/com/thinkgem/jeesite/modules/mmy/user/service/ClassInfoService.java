/**    
 * 文件名：ClassInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月6日    
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
import com.thinkgem.jeesite.modules.mmy.user.dao.ClassInfoDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * 项目名称：JeeSite 类名称：ClassInfoService 类描述： 创建人：Administrator 创建时间：2018年6月6日
 * 下午4:42:53 修改人：Administrator 修改时间：2018年6月6日 下午4:42:53 修改备注：
 * 
 * @version
 * 
 */
@Service
public class ClassInfoService extends CrudService<ClassInfoDao, ClassInfo> {
    @Autowired
    ClassInfoDao classDao;

    /**
     * 
     * getById(根据id获取班级)
     * 
     * 
     */
    public ClassInfo getById(String id) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(id);
        return classDao.get(classInfo);
    }

    /**
     * 
     * getByGrade(根据组id获取旗下所有班级)
     * 
     * 
     */
    public Page<ClassInfo> getPageByGrade(Page<ClassInfo> page, String gradeId) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setGradeId(gradeId);
        classInfo.setPage(page);
        page.setList(classDao.getByGradeId(classInfo));
        return page;
    }

    /**
     * 
     * getListByGrade(根据组名获取对应班级)
     * 
     */
    public List<ClassInfo> getListByGrade(String gradeId) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setGradeId(gradeId);
        return classDao.getByGradeId(classInfo);
    }

    /**
     * 
     * createClass(调用本方法前前必须确认组名grade是否存在)
     * 
     * * （需同步）
     */
    @Transactional(readOnly = false)
    public int createClass(GradeInfo grade, String classname) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(IdGen.uuid());
        classInfo.setGradeId(grade.getId());
        classInfo.setName(classname);
        classInfo.setCreateTime(TimeUtils.formateNowDay2());
        classInfo.setCreater(UserUtils.getUser().getId());
        if (countByClassName(classname) > 0) {
            return -1;
        }
        int i = classDao.insert(classInfo);

        return i;
    }

    /**
     * 
     * updateClassName(更改班级名称) （需同步）
     * 
     */
    @Transactional(readOnly = false)
    public int updateClassName(String id, String className) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(id);
        classInfo.setName(className);
        classInfo.setCreater(UserUtils.getUser().getId());
        if (countByClassName(className) > 0) {
            return -1;
        }

        int i = classDao.updateClassName(classInfo);
        return i;
    }

    /**
     * 
     * countByClassName(计算某名称的班级数)
     * 
     */
    public int countByClassName(String className) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setName(className);
        classInfo.setCreater(UserUtils.getUser().getId());
        return classDao.countByClassName(classInfo);
    }

    /**
     * 
     * updateGradeId(更新班级对应的组，并发时可能导致sql语句报错)
     * 
     * 
     */
    @Transactional(readOnly = false)
    public int updateGradeId(GradeInfo grade, String classId) throws Exception {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setGradeId(grade.getId());
        classInfo.setId(classId);
        return classDao.updateGradeId(classInfo);

    }

    /**
     * 
     * delById(根据id删除班级)
     * 
     * 
     */
    @Transactional(readOnly = false)
    public int delById(String id) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(id);
        return classDao.delById(classInfo);
    }

    /**
     * 
     * getAll(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public List<ClassInfo> getAll() {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setCreater(UserUtils.getUser().getId());
        return classDao.getAll(classInfo);
    }

    /**
     * 
     * countByGradeId(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public int countByGradeId(String gradeId) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setGradeId(gradeId);
        return dao.countByGradeId(classInfo);
    }

}
