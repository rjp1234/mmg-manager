/**    
 * 文件名：ClassInfoService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月6日    
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
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;

/**
 * 
 * 项目名称：JeeSite 类名称：ClassInfoService 类描述： 创建人：Administrator 创建时间：2018年6月6日
 * 下午7:45:23 修改人：Administrator 修改时间：2018年6月6日 下午7:45:23 修改备注：
 * 
 * @version
 * 
 */
public class ClassInfoServiceTest extends BaseTest {
    @Autowired
    ClassInfoService classService;

    @Test
    public void createClass_test() {
        GradeInfo grade = new GradeInfo();
        grade.setId("22ff9cf3501d4533a5931e4309d6a618");
        classService.createClass(grade, "班级1");
        for (int i = 0; i < 10; i++) {
            System.out.println(classService.createClass(grade, "班级" + i));
        }

    }

    @Test
    public void updateClass_test() {
        List<ClassInfo> list = classService.getPageByGrade(new Page<ClassInfo>(), "22ff9cf3501d4533a5931e4309d6a618")
                .getList();
        for (ClassInfo classInfo : list) {
            if (classInfo.getName().equals("班级0")) {
                classService.delById(classInfo.getId());
                continue;
            }
            classService.updateClassName(classInfo.getId(), "班级100");

        }

    }

}
