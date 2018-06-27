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

import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionOpreationInfo 类描述： 创建人：Administrator
 * 创建时间：2018年6月27日 下午4:50:57 修改人：Administrator 修改时间：2018年6月27日 下午4:50:57 修改备注：
 * 
 * @version 用于返回
 * 
 */
public class LessionOpreationInfo extends DataEntity<LessionOpreationInfo> {

    private static final long serialVersionUID = -3338319314691363844L;

    private String name;// 课文名称

    private int completeNum;// 总完成人数

    private List<String> issueClassIds;// 课文下发班级名

}
