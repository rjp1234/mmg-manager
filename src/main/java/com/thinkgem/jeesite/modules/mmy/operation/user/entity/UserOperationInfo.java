/**    
 * 文件名：UserOperationInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月30日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.operation.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：UserOperationInfo 类描述： 创建人：Administrator 创建时间：2018年6月30日
 * 上午9:41:07 修改人：Administrator 修改时间：2018年6月30日 上午9:41:07 修改备注：
 * 
 * @version
 * 
 */
public class UserOperationInfo extends DataEntity<UserOperationInfo> {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = -7718793649328745407L;

    private String userName;// 学生姓名

    private String className;// 班级名称

    private String userId;// 学生id

    private String classId;// 班级id

    private int totalWork;// 已布置的作业总量

    private int compelteWork;// 已完成的作业总量

    private int numA;// 获得a级评定的次数

    private int numP;

    private int numE;

    private int avg;// 平均评定

    private int classuserNum;// 班级人数

    private int ranking;// 平均分排名（平均评定分高于该学生的总数）

    private String creater;

    /**
     * creater
     * 
     * @return the creater
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getCreater() {
        return creater;
    }

    /**
     * @param creater
     *            the creater to set
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * userName
     * 
     * @return the userName
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * className
     * 
     * @return the className
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getClassName() {
        return className;
    }

    /**
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * userId
     * 
     * @return the userId
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * classId
     * 
     * @return the classId
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getClassId() {
        return classId;
    }

    /**
     * @param classId
     *            the classId to set
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * totalWork
     * 
     * @return the totalWork
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getTotalWork() {
        return totalWork;
    }

    /**
     * @param totalWork
     *            the totalWork to set
     */
    public void setTotalWork(int totalWork) {
        this.totalWork = totalWork;
    }

    /**
     * compelteWork
     * 
     * @return the compelteWork
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getCompelteWork() {
        return compelteWork;
    }

    /**
     * @param compelteWork
     *            the compelteWork to set
     */
    public void setCompelteWork(int compelteWork) {
        this.compelteWork = compelteWork;
    }

    /**
     * numA
     * 
     * @return the numA
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getNumA() {
        return numA;
    }

    /**
     * @param numA
     *            the numA to set
     */
    public void setNumA(int numA) {
        this.numA = numA;
    }

    /**
     * numP
     * 
     * @return the numP
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getNumP() {
        return numP;
    }

    /**
     * @param numP
     *            the numP to set
     */
    public void setNumP(int numP) {
        this.numP = numP;
    }

    /**
     * numE
     * 
     * @return the numE
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getNumE() {
        return numE;
    }

    /**
     * @param numE
     *            the numE to set
     */
    public void setNumE(int numE) {
        this.numE = numE;
    }

    /**
     * avg
     * 
     * @return the avg
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getAvg() {
        return avg;
    }

    /**
     * @param avg
     *            the avg to set
     */
    public void setAvg(int avg) {
        this.avg = avg;
    }

    /**
     * classuserNum
     * 
     * @return the classuserNum
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getClassuserNum() {
        return classuserNum;
    }

    /**
     * @param classuserNum
     *            the classuserNum to set
     */
    public void setClassuserNum(int classuserNum) {
        this.classuserNum = classuserNum;
    }

    /**
     * ranking
     * 
     * @return the ranking
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getRanking() {
        return ranking;
    }

    /**
     * @param ranking
     *            the ranking to set
     */
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

}
