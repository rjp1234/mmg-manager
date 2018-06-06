/**    
 * 文件名：UserInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.user.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.base.BaseTest;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;

/**
 * 
 * 项目名称：JeeSite 类名称：UserInfoService 类描述： 创建人：Administrator 创建时间：2018年6月5日
 * 下午4:25:00 修改人：Administrator 修改时间：2018年6月5日 下午4:25:00 修改备注：
 * 
 * @version
 * 
 */

public class GradeInfoServiceTest extends BaseTest {
    @Autowired
    GradeInfoService gradeInfoService;

    @Test
    public void insertTest() {
        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setName("test grade");

        gradeInfo.setCreater("testcreater");
        for (int i = 0; i < 10; i++) {
            try {
                gradeInfoService.insertGrade(gradeInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void getAllTest() {
        System.out.println(gradeInfoService.getAll());
    }

    @Test
    public void findList() {
        Page<GradeInfo> page = new Page<GradeInfo>();
        GradeInfo grade = new GradeInfo();
        System.out.println(gradeInfoService.findList(grade, page).getList());
    }

    @Test
    public void countByNameTest() {
        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setName("test grade");
        // System.out.println(gradeInfoService.countByName(gradeInfo));
    }

    @Test
    public void delTest() {
        List<GradeInfo> list = gradeInfoService.getAll();
        for (GradeInfo gradeInfo : list) {
            gradeInfoService.delById(gradeInfo.getId());
        }
    }

}
