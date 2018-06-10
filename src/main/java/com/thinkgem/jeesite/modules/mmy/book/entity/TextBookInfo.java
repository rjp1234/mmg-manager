/**    
 * 文件名：TextBookInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月10日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：TextBookInfo 类描述： 创建人：Administrator 创建时间：2018年6月10日
 * 下午3:40:23 修改人：Administrator 修改时间：2018年6月10日 下午3:40:23 修改备注：
 * 
 * @version 教材信息数据库存储类
 * 
 */
public class TextBookInfo extends DataEntity<TextBookInfo> {

    private static final long serialVersionUID = 1411267338011202925L;

    private String name;

    private String image;

    private String creater;

    private String createTime;

    private int unitNum;

    private String gradeId;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

}
