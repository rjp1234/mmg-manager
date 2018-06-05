/**    
 * 文件名：GradeInfoController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;

/**
 * 
 * 项目名称：JeeSite 类名称：GradeInfoController 类描述： 创建人：Administrator 创建时间：2018年6月5日
 * 下午3:21:56 修改人：Administrator 修改时间：2018年6月5日 下午3:21:56 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/grade")
public class GradeInfoController extends BaseController {
    @Autowired
    GradeInfoService gradeInfoService;

    @RequestMapping("gradeList")
    public String gradeForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GradeInfo> page = new Page<GradeInfo>(request, response);
        GradeInfo gradeInfo = new GradeInfo();
        String name = request.getParameter("name");
        gradeInfo.setName(name);
        page = gradeInfoService.findList(gradeInfo, page);
        model.addAttribute("page", page);
        return "modules/mmy/grade/gradeList";
    }

    @RequestMapping("updateGradeName")
    @ResponseBody
    public Map<String, Object> updateGradeName(HttpServletRequest request, HttpServletResponse response, Model model) {
        return null;
    }

}
