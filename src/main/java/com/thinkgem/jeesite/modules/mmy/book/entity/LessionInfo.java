/**    
 * 文件名：LessionInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月11日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionInfo 类描述： 创建人：Administrator 创建时间：2018年6月11日
 * 上午9:55:08 修改人：Administrator 修改时间：2018年6月11日 上午9:55:08 修改备注：
 * 
 * @version
 * 
 */
public class LessionInfo extends DataEntity<LessionInfo> {

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 4942796531338822970L;

    private String name;// 课文名

    private String content;// 课文内容

    private String image;// 课文封面

    private String tStudioUrl;// 教师的录音内容

    private String tContent;// 教师的话文本内容

    private String exampleUrl;// 示范录音

    private int unit;// 课文所在的单元

    private String textId;// 课文对应的教材id

    private String creater;// 创建人

    private String createTime;// 创建时间

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

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String gettStudioUrl() {
        return tStudioUrl;
    }

    public void settStudioUrl(String tStudioUrl) {
        this.tStudioUrl = tStudioUrl;
    }

    public String gettContent() {
        return tContent;
    }

    public void settContent(String tContent) {
        this.tContent = tContent;
    }

    public String getExampleUrl() {
        return exampleUrl;
    }

    public void setExampleUrl(String exampleUrl) {
        this.exampleUrl = exampleUrl;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

}
