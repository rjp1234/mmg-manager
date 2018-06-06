/**    
 * 文件名：ClassInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月6日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：JeeSite 类名称：ClassInfo 类描述： 创建人：Administrator 创建时间：2018年6月6日 下午4:39:16
 * 修改人：Administrator 修改时间：2018年6月6日 下午4:39:16 修改备注：
 * 
 * @version
 * 
 */
public class ClassInfo extends DataEntity<ClassInfo> {

    private static final long serialVersionUID = -4314698803590060226L;

    private String gradeId;

    private String name;

    private String creater;

    private String createTime;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
