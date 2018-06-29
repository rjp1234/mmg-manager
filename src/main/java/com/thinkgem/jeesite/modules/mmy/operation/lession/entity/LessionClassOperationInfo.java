/**    
 * 文件名：LessionOpreationInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.operation.lession.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionOpreationInfo 类描述： 创建人：Administrator
 * 创建时间：2018年6月27日 下午4:50:57 修改人：Administrator 修改时间：2018年6月27日 下午4:50:57 修改备注：
 * 
 * @version 用于返回
 * 
 */
public class LessionClassOperationInfo extends DataEntity<LessionClassOperationInfo> {

    private static final long serialVersionUID = -3338319314691363844L;

    private String lessionId;// 课文id

    private String classId;// 班级id

    private String className;// 班级名称

    private String lessionName;// 课文名称

    private int completeNum;// 总完成人数

    private int totalNum;// 班级总人数

    private int pointedNum;// 被打分过的录音总数

    private String gradeName;// 组名

    private int numA;// A优秀人数

    private int numP;// P良好人数

    private int numE;// E不合格人数

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * lessionId
     * 
     * @return the lessionId
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getLessionId() {
        return lessionId;
    }

    /**
     * @param lessionId
     *            the lessionId to set
     */
    public void setLessionId(String lessionId) {
        this.lessionId = lessionId;
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
     * lessionName
     * 
     * @return the lessionName
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getLessionName() {
        return lessionName;
    }

    /**
     * @param lessionName
     *            the lessionName to set
     */
    public void setLessionName(String lessionName) {
        this.lessionName = lessionName;
    }

    /**
     * completeNum
     * 
     * @return the completeNum
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getCompleteNum() {
        return completeNum;
    }

    /**
     * @param completeNum
     *            the completeNum to set
     */
    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    /**
     * totalNum
     * 
     * @return the totalNum
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getTotalNum() {
        return totalNum;
    }

    /**
     * @param totalNum
     *            the totalNum to set
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * pointedNum
     * 
     * @return the pointedNum
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public int getPointedNum() {
        return pointedNum;
    }

    /**
     * @param pointedNum
     *            the pointedNum to set
     */
    public void setPointedNum(int pointedNum) {
        this.pointedNum = pointedNum;
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

}
