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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.ThreadPool;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.mmy.user.dao.GradeInfoDao;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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
     * getByClassId(根据班级名称去查询对应的组)
     * 
     * 
     */
    public GradeInfo getByClassId(String classId) {
        return gradeDao.getByClassId(classId);
    }

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
        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setCreater(UserUtils.getUser().getId());
        return gradeDao.getAll(gradeInfo);
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

        gradeInfo.setId(IdGen.uuid());
        gradeInfo.setCreateTime(TimeUtils.formateNowDay2());
        gradeInfo.setCreater(UserUtils.getUser().getId());
        if (countByName(gradeInfo) > 0) {
            return -1;
        }
        int i = gradeDao.insert(gradeInfo);
        return i;
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
    public int updateGradeName(String name, String id) {
        int i = 0;
        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setId(id);
        gradeInfo.setName(name);
        gradeInfo.setCreater(UserUtils.getUser().getId());
        if (countByName(gradeInfo) > 0) {
            return -1;
        }

        i = gradeDao.updateName(gradeInfo);

        return i;
    }

    @Transactional(readOnly = false)
    public int delById(String id) {
        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setId(id);
        return gradeDao.delById(gradeInfo);
    }

    /**
     * 
     * getById(根据id获取组别)
     * 
     * 
     */
    public GradeInfo getById(String gradeId) {
        GradeInfo grade = new GradeInfo();
        grade.setId(gradeId);
        return gradeDao.get(grade);
    }

    private static final Map<String, GradeInfo> bufferGradeMap = new ConcurrentHashMap<String, GradeInfo>();

    private static long timeStamp = System.currentTimeMillis();// 时间除非主动更改，不会随系统时间变化而变

    /**
     * 
     * getByIdBuffer(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public GradeInfo getByIdBuffer(String gradeId) {
        GradeInfo grade = bufferGradeMap.get(gradeId);
        if (grade == null) {
            grade = getById(gradeId);
            bufferGradeMap.put(gradeId, grade);
        } else {
            // 使用map中的数据，但抛出线程去更新其中的数据,线程之间时间间隔1000ms
            // 以避免请求低命中率
            ThreadPool.getInstance().execute(() -> {
                long difTime = System.currentTimeMillis() - timeStamp;
                if (difTime > 10000) {
                    logger.info("grade 信息已经刷新");
                    timeStamp = System.currentTimeMillis();
                    bufferGradeMap.clear();
                    bufferGradeMap.put(gradeId, getById(gradeId));
                } else {
                    logger.info("grade 信息不刷新");
                }
            });

        }
        return grade;

    }

    public static void main(String[] args) throws InterruptedException {
    }

}
