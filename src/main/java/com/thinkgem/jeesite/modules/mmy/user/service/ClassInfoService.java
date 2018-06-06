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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private static Lock lock = new ReentrantLock();

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
     * createClass(调用本方法前前必须确认组名grade是否存在)
     * 
     * * （需同步）
     */
    @Transactional(readOnly = false)
    public int createClass(GradeInfo grade, String classname) {
        lock.lock();
        int i = 0;
        // 将班级插入数据库
        try {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setId(IdGen.uuid());
            classInfo.setGradeId(grade.getId());
            classInfo.setName(classname);
            classInfo.setCreateTime(TimeUtils.formateNowDay2());
            classInfo.setCreater(UserUtils.getUser().getId());
            i = countByClassName(classname);
            if (i > 0) {
                return -1;
            }
            i = classDao.insert(classInfo);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
        return i;
    }

    /**
     * 
     * updateClassName(更改班级名称) （需同步）
     * 
     */
    @Transactional(readOnly = false)
    public int updateClassName(String id, String className) {
        lock.lock();
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(id);
        classInfo.setName(className);
        int i = 0;
        try {

            i = countByClassName(className);
            if (i > 0) {
                return -1;
            }
            i = classDao.updateClassName(classInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
        return i;
    }

    /**
     * 
     * countByClassName(计算某名称的班级数)
     * 
     */
    public synchronized int countByClassName(String className) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setName(className);
        return classDao.countByClassName(classInfo);
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

}
