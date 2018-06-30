/**    
 * 文件名：UserOperationController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月30日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.operation.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.operation.user.entity.UserOperationInfo;
import com.thinkgem.jeesite.modules.mmy.operation.user.service.UserOperationService;

/**
 * 
 * 项目名称：mmg-manager 类名称：UserOperationController 类描述： 创建人：Administrator
 * 创建时间：2018年6月30日 上午9:38:40 修改人：Administrator 修改时间：2018年6月30日 上午9:38:40 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/userOperation")
public class UserOperationController extends BaseController {
    @Autowired
    UserOperationService userOperationService;

    @ModelAttribute
    public UserOperationInfo get() {
        return new UserOperationInfo();
    }

    @RequestMapping("userList")
    public String userList(HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes, UserOperationInfo userOperationInfo) {
        Page<UserOperationInfo> page = new Page<UserOperationInfo>(request, response);
        userOperationService.findPage(page, userOperationInfo);
        model.addAttribute(page);
        return adminPath;
    }

}
