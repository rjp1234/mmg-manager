/**    
 * 文件名：UserInfoController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 
 * 项目名称：JeeSite 类名称：UserInfoController 类描述： 创建人：Administrator 创建时间：2018年6月5日
 * 下午1:12:25 修改人：Administrator 修改时间：2018年6月5日 下午1:12:25 修改备注：
 * 
 * @version
 * @模块功能：实现学生用户信息的导入、编辑、查看、删除功能
 */
@Controller
@RequestMapping("${adminPath}/user/student")
public class UserInfoController extends BaseController {

    /**
     * 
     * userBatchForm(跳转至用户批量添加页面)
     * 
     * 
     */
    @RequestMapping("userBatchForm")
    public String userBatchForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        return returnPrefix + "user/userBatchForm";

    }

    /**
     * 
     * userForm(跳转至用户添加/编辑页面)
     * 
     * 
     */
    @RequestMapping("userForm")
    public String userForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        return returnPrefix + "user/userBatchForm";
    }

    /**
     * 
     * userList(跳转至用户列表页面)
     * 
     */
    @RequestMapping("userList")
    public String userList(HttpServletRequest request, HttpServletResponse response, Model model) {
        return returnPrefix + "user/userList";
    }

}
