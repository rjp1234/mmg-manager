/**    
 * 文件名：LessionOperationController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.operation.lession.controller;

import java.util.List;

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
import com.thinkgem.jeesite.modules.mmy.book.service.LessionClassBindService;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.operation.lession.entity.LessionClassOperationInfo;
import com.thinkgem.jeesite.modules.mmy.operation.lession.service.LessionClassOperationService;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionOperationController 类描述： 创建人：Administrator
 * 创建时间：2018年6月27日 下午3:31:15 修改人：Administrator 修改时间：2018年6月27日 下午3:31:15 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/lessionOperation")
public class LessionClassOperationController extends BaseController {
    @Autowired
    LessionInfoService lessionService;

    @Autowired
    TextBookService textBookService;

    @Autowired
    GradeInfoService gradeService;

    @Autowired
    ClassInfoService classService;

    @Autowired
    LessionClassBindService lessionClassBindService;

    @Autowired
    LessionClassOperationService lessionClassOperationService;

    @ModelAttribute
    public LessionClassOperationInfo get() {

        return new LessionClassOperationInfo();

    }

    /**
     * 
     * lessionList(返回可供批改作业的课文列表)
     * 
     */
    @RequestMapping("lessionList")
    public String lessionList(HttpServletRequest request, HttpServletResponse response,
            LessionClassOperationInfo lessionClassOperationInfo, RedirectAttributes redirectAttributes, Model model) {
        System.out.println(lessionClassOperationInfo);
        Page<LessionClassOperationInfo> page = new Page<LessionClassOperationInfo>(request, response);
        page = lessionClassOperationService.findPage(page, lessionClassOperationInfo);
        List<ClassInfo> all = classService.getAll();
        model.addAttribute("classList", all);
        model.addAttribute(page);
        return "modules/mmy/lession/lessionClassOperationList";

    }

}
