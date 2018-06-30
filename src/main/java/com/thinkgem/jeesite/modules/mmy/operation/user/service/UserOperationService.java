/**    
 * 文件名：UserOperationService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月30日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.operation.user.service;

import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mmy.operation.user.dao.UserOperationDao;
import com.thinkgem.jeesite.modules.mmy.operation.user.entity.UserOperationInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：UserOperationService 类描述： 创建人：Administrator
 * 创建时间：2018年6月30日 上午9:55:29 修改人：Administrator 修改时间：2018年6月30日 上午9:55:29 修改备注：
 * 
 * @version
 * 
 */
@Service
public class UserOperationService extends CrudService<UserOperationDao, UserOperationInfo> {

}
