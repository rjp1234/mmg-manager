/**    
 * 文件名：LessionOperationController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.controller;

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
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionClassBindService;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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
public class LessionOperationController extends BaseController {
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

    @ModelAttribute
    public LessionInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return lessionService.getById(id);
        } else {
            return new LessionInfo();
        }
    }

    /**
     * 
     * lessionList(返回可供批改作业的课文列表)
     * 
     */
    @RequestMapping("lessionList")
    public String lessionList(HttpServletRequest request, HttpServletResponse response, LessionInfo lessionInfo,
            RedirectAttributes redirectAttributes, Model model) {
        Page<LessionInfo> page = new Page<LessionInfo>(request, response);
        page = lessionService.findPage(page, lessionInfo);
        List<TextBookInfo> textList = textBookService.getAll();
        for (LessionInfo lession : page.getList()) {
            try {
                lession.setTextId(textBookService.getByIdBuffer(lession.getTextId()).getName());

                lession.setCreater(UserUtils.get(lession.getCreater()).getLoginName());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (StringUtils.isBlank(lession.getTextId())) {
                lession.setTextId("<span style='color:red'>该教材不存在，请编辑重新绑定</span>");
            }

        }
        model.addAttribute("page", page);
        model.addAttribute("textList", textList);
        return adminPath;

    }

}
