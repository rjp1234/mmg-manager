/**    
 * 文件名：ClassInfoController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月6日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;

/**
 * 
 * 项目名称：JeeSite 类名称：ClassInfoController 类描述： 创建人：Administrator 创建时间：2018年6月6日
 * 下午2:34:29 修改人：Administrator 修改时间：2018年6月6日 下午2:34:29 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/class")
public class ClassInfoController extends BaseController {
    @Autowired
    ClassInfoService classService;

    @Autowired
    GradeInfoService gradeService;

    @ModelAttribute
    public ClassInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return classService.getById(id);
        } else {
            return new ClassInfo();
        }
    }

    /**
     * 
     * classForm(跳转至班级页面)
     * 
     */

    @RequestMapping("classForm")
    public String classForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<GradeInfo> all = gradeService.getAll();
        model.addAttribute("gradeList", all);
        return "modules/mmy/class/classForm";
    }

    /**
     * 
     * createClass(创建班级)
     * 
     */
    @RequestMapping("createClass")
    public String createClass(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo,
            RedirectAttributes redirectAttributes) {
        String gradeId = classInfo.getGradeId();
        // 组别校验
        GradeInfo gradeInfo = gradeService.getById(gradeId);
        if (gradeInfo == null) {
            addMessage(redirectAttributes, "组别不存在");
            return "redirect:" + adminPath + "/operator/class/classForm";
        }
        int i = classService.createClass(gradeInfo, classInfo.getName());
        if (i == 1) {
            addMessage(redirectAttributes, "班级名" + classInfo.getName() + "创建成功");
        } else {
            addMessage(redirectAttributes, "班级名" + classInfo.getName() + "创建失败");
            return "redirect:" + adminPath + "/operator/class/classForm";
        }
        return adminPath;
    }

    @RequestMapping("classList")
    public String classList(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo,
            Model model) {
        Page<ClassInfo> page = new Page<ClassInfo>(request, response);
        page = classService.findPage(page, classInfo);
        model.addAttribute("page", page);
        return adminPath;

    }

    /**
     * 
     * checkClassName(ajax确认班级名称是否合法)
     */
    @RequestMapping("checkClassName")
    @ResponseBody
    public Map<String, Object> checkClassName(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("flag", false);
        returnMap.put("msg", "<span style='color:red'>该名称已存在</span>");
        String className = request.getParameter("name");
        int i = classService.countByClassName(className);
        if (i == 0) {
            returnMap.put("flag", true);
            returnMap.put("msg", "<span style='color:green'>该名称可以使用</span>");
        }
        return returnMap;

    }

    public static void main(String[] args) {
        System.out.println(Global.getAdminPath());
    }
}
