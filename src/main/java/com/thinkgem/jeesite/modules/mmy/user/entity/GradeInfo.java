/**    
 * 文件名：GradeInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：JeeSite 类名称：GradeInfo 类描述： 创建人：Administrator 创建时间：2018年6月5日 下午3:32:51
 * 修改人：Administrator 修改时间：2018年6月5日 下午3:32:51 修改备注：
 * 
 * @version
 * 
 */
public class GradeInfo extends DataEntity<GradeInfo> {

    private static final long serialVersionUID = 6030821546772217220L;

    private String name;

    private String creater;

    private String createTime;

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
