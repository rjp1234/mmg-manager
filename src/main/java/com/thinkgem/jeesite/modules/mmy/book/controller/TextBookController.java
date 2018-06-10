/**    
 * 文件名：TextBookController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月10日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.controller;

import java.io.UnsupportedEncodingException;
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

import com.thinkgem.jeesite.common.utils.CkfinderUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;

/**
 * 
 * 项目名称：mmg-manager 类名称：TextBookController 类描述： 创建人：Administrator
 * 创建时间：2018年6月10日 下午3:44:21 修改人：Administrator 修改时间：2018年6月10日 下午3:44:21 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/textbook")
public class TextBookController extends BaseController {
    @Autowired
    TextBookService textBookService;

    @Autowired
    GradeInfoService gradeService;

    @ModelAttribute
    public TextBookInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return textBookService.getById(id);
        } else {
            return new TextBookInfo();
        }
    }

    @RequestMapping("insertTextBook")
    public String insertTextBook(HttpServletRequest request, HttpServletResponse response, TextBookInfo textBookInfo,
            RedirectAttributes redirectAttributes) {
        try {
            CkfinderUtils.getFileFromCkpath(textBookInfo.getImage());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return adminPath;

    }

    @RequestMapping("textBookList")
    public String textBookList(HttpServletRequest request, HttpServletResponse response, TextBookInfo textBookInfo,
            RedirectAttributes redirectAttributes) {
        return adminPath;

    }

    @RequestMapping("textBookForm")
    public String textBookForm(HttpServletRequest request, HttpServletResponse response, TextBookInfo textBookInfo,
            RedirectAttributes redirectAttributes, Model model) {
        List<GradeInfo> gradeList = gradeService.getAll();
        model.addAttribute("gradeList", gradeList);
        return "modules/mmy/textbook/textBookForm";
    }
}
