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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.UserInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.UserInfoService;

/**
 * 
 * 项目名称：JeeSite 类名称：UserInfoController 类描述： 创建人：Administrator 创建时间：2018年6月5日
 * 下午1:12:25 修改人：Administrator 修改时间：2018年6月5日 下午1:12:25 修改备注：
 * 
 * @version
 * @模块功能：实现学生用户信息的导入、编辑、查看、删除功能
 */
@Controller
@RequestMapping("${adminPath}/operator/user")
public class UserInfoController extends BaseController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ClassInfoService classInfoService;

    @Autowired
    GradeInfoService gradeInfoService;

    @ModelAttribute
    public UserInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return userInfoService.getById(id);
        } else {
            return new UserInfo();
        }
    }

    /**
     * 
     * userBatchForm(跳转至用户批量添加页面)
     * 
     * 
     */
    @RequestMapping("userBatchForm")
    public String userBatchForm(HttpServletRequest request, HttpServletResponse response, Model model,
            UserInfo userInfo) {
        List<GradeInfo> all = gradeInfoService.getAll();
        model.addAttribute("gradeList", all);
        return "modules/mmy/user/userBatchForm";

    }

    /**
     * 
     * userBatchCreate(批量添加学生用户)
     * 
     * 
     */
    @RequestMapping("userBatchCreate")
    public String userBatchCreate(HttpServletRequest request, HttpServletResponse response, Model model,
            UserInfo userInfo, RedirectAttributes redirectAttributes) {
        System.out.println(userInfo);
        String excelUrl = request.getParameter("excelUrl");
        System.out.println(excelUrl);
        return adminPath;

    }

    /**
     * 
     * userForm(跳转至用户添加/编辑页面)
     * 
     * 
     */
    @RequestMapping("userForm")
    public String userForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/userBatchForm";
    }

    /**
     * 
     * userList(跳转至用户列表页面)
     * 
     */
    @RequestMapping("userList")
    public String userList(HttpServletRequest request, HttpServletResponse response, Model model, UserInfo userInfo) {
        Page<UserInfo> page = new Page<UserInfo>(request, response);
        userInfoService.findPage(page, userInfo);
        return "modules/mmy/user/userBatchForm";
    }

}
