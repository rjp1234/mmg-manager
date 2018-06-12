/**    
 * 文件名：LessionClassBindInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月12日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionClassBindInfo 类描述： 创建人：Administrator
 * 创建时间：2018年6月12日 上午11:23:31 修改人：Administrator 修改时间：2018年6月12日 上午11:23:31 修改备注：
 * 
 * @version
 * 
 */
public class LessionClassBindInfo extends DataEntity<LessionClassBindInfo> {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 8305774600421026876L;

    private String lessionId;

    private String classId;

    private String createTime;

    private String creater;

    public String getLessionId() {
        return lessionId;
    }

    public void setLessionId(String lessionId) {
        this.lessionId = lessionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

}
