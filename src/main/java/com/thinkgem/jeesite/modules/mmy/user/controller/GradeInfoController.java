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
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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

    @ModelAttribute
    public GradeInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return gradeInfoService.get(id);
        } else {
            return new GradeInfo();
        }
    }

    @RequestMapping("gradeList")
    public String gradeForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GradeInfo> page = new Page<GradeInfo>(request, response);
        GradeInfo gradeInfo = new GradeInfo();
        String name = request.getParameter("name");
        gradeInfo.setName(name);
        page = gradeInfoService.findList(gradeInfo, page);

        List<GradeInfo> list = page.getList();
        // 将查询创建者的登录名
        for (GradeInfo grade : list) {
            String userId = grade.getCreater();
            User user = UserUtils.get(userId);
            if (user != null) {
                grade.setCreater(user.getLoginName());
            } else {
                grade.setCreater("<span style='color:red'>未登记的权限者</span>");
            }

        }
        model.addAttribute("page", page);
        return "modules/mmy/grade/gradeList";
    }

    /**
     * 
     * updateGradeName(更新组名)
     * 
     * 
     */
    @RequestMapping("updateGradeName")
    @ResponseBody
    public Map<String, Object> updateGradeName(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("flag", 0);
        try {
            int i = gradeInfoService.updateGradeName(name, id);
            returnMap.put("flag", i);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return returnMap;
    }

    /**
     * 
     * createGrade(创建一个组)
     * 
     * 
     */
    @RequestMapping("createGrade")
    @ResponseBody
    public Map<String, Object> createGrade(HttpServletRequest request, HttpServletResponse response, Model model) {
        String name = request.getParameter("name");
        GradeInfo grade = new GradeInfo();
        grade.setName(name);
        User user = UserUtils.getUser();
        grade.setCreater(user.getId());
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            int i = gradeInfoService.insertGrade(grade);
            returnMap.put("flag", i);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        grade.setCreater(user.getLoginName());
        returnMap.put("data", grade);
        return returnMap;

    }

    /**
     * 
     * delete(删除一个组)
     * 
     */
    @RequestMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {
        String id = request.getParameter("id");
        int i = gradeInfoService.delById(id);
        if (i == 1) {
            addMessage(redirectAttributes, "删除成功");
        }
        return "redirect:" + adminPath + "/operator/grade/gradeList";

    }

    public static void main(String[] args) {
        System.out.println(Global.getConfig("productName"));
    }

}
