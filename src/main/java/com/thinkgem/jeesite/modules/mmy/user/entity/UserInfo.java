/**    
 * 文件名：UserInfo.java    
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
 * 项目名称：JeeSite 类名称：UserInfo 类描述： 创建人：Administrator 创建时间：2018年6月5日 下午1:57:35
 * 修改人：Administrator 修改时间：2018年6月5日 下午1:57:35 修改备注：
 * 
 * @version
 * 
 */
public class UserInfo extends DataEntity<UserInfo> {

    private static final long serialVersionUID = 7908727426662505469L;

    private String password;// 用户密码

    private String loginname;// 登录名

    private String phonenum;// 手机号

    private String nickname;// 昵称

    private String realname;// 真实姓名

    private String image;// 用户头像

    private int userType;// 用户类型

    private String createTime;// 用户创建时间

    private String classId;// 所属班级

    private String creater;// 创建人

    private String gradeId;// 组名

    public static final int USER_TYPE_LOCAL_STUDENT = 0;// 用户类型为本地学生

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

}
