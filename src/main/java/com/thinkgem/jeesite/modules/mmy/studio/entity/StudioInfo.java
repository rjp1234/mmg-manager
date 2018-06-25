/**    
 * 文件名：StudioInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月25日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.studio.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：StudioInfo 类描述： 创建人：Administrator 创建时间：2018年6月25日
 * 下午1:41:59 修改人：Administrator 修改时间：2018年6月25日 下午1:41:59 修改备注：
 * 
 * @version
 * 
 */
public class StudioInfo extends DataEntity<StudioInfo> {

    private static final long serialVersionUID = 1280309769162267031L;

    private String url;// 录音路径

    private String createTime;// 提交时间

    private String userId;// 用户id

    private String lessionId;// 课程id

    private int point;// 分数十位

    private String pointer;// 打分人

    private String pointTime;// 打分时间

    private String type;// 录音类型（弃用）

    private String isPointed;// 是否被打分及复合查询

    private String comment;// 评语

    public static final String POINT_IS_POINTED = "1";// 已打分

    public static final String POINT_IS_NOT_POINTED = "0";// 未打分

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsPointed() {
        return isPointed;
    }

    public void setIsPointed(String isPointed) {
        if (POINT_IS_POINTED.equals(isPointed)) {
            this.isPointed = "and pointer is not null";
        } else if (POINT_IS_NOT_POINTED.equals(isPointed)) {
            this.isPointed = "and pointer is null";
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLessionId() {
        return lessionId;
    }

    public void setLessionId(String lessionId) {
        this.lessionId = lessionId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public String getPointTime() {
        return pointTime;
    }

    public void setPointTime(String pointTime) {
        this.pointTime = pointTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
