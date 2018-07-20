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

import java.io.File;
import java.io.UnsupportedEncodingException;
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

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CkfinderUtils;
import com.thinkgem.jeesite.common.utils.FileLoadUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.UnitInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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

    @Autowired
    LessionInfoService lessionService;

    @ModelAttribute
    public TextBookInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return textBookService.getById(id);
        } else {
            return new TextBookInfo();
        }
    }

    /**
     * 
     * insertTextBook(创建教材)
     * 
     * 
     */
    @RequestMapping("insertTextBook")
    public String insertTextBook(HttpServletRequest request, HttpServletResponse response, TextBookInfo textBookInfo,
            RedirectAttributes redirectAttributes) {
        // 教材名称校验
        String name = textBookInfo.getName();
        if (StringUtils.isBlank(name)) {
            addMessage(redirectAttributes, "教材名称不得为空");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }

        int count = textBookService.countByName(name);
        if (count > 0) {
            addMessage(redirectAttributes, "教材名称不得重复");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }
        // 绑定年级检验
        String gradeId = textBookInfo.getGradeId();
        if (StringUtils.isBlank(gradeId)) {
            addMessage(redirectAttributes, "组别不得为空");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }

        GradeInfo gradeInfo = gradeService.getById(gradeId);
        if (gradeInfo == null) {
            addMessage(redirectAttributes, "所绑定的组别不存在");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }

        File file = null;
        try {
            file = CkfinderUtils.getFileFromCkpath(textBookInfo.getImage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            addMessage(redirectAttributes, "封面路径异常异常");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }
        if (file == null) {
            addMessage(redirectAttributes, "封面路径不存在");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }
        String id = IdGen.uuid();
        textBookInfo.setId(id);
        String image = null;
        try {
            image = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_IMAGE_TEA, file.getPath(),
                    IdGen.uuid() + ".jpg");
        } catch (UnsupportedEncodingException e) {
            addMessage(redirectAttributes, "封面上传发生异常");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }
        if (StringUtils.isBlank(image)) {
            addMessage(redirectAttributes, "封面上传发生异常");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }

        textBookInfo.setImage(image);

        textBookInfo.setCreater(UserUtils.getUser().getId());
        textBookInfo.setCreateTime(TimeUtils.formateNowDay2());
        int insertTextBook = textBookService.insertTextBook(textBookInfo);
        if (insertTextBook == 0) {
            addMessage(redirectAttributes, "教材新增失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/textbook/textBookForm";
        }
        addMessage(redirectAttributes, "新增成功");
        return "redirect:" + adminPath + "/operator/textbook/textBookList";

    }

    /**
     * 
     * textBookList(教材列表)
     * 
     * 
     */
    @RequestMapping("textBookList")
    public String textBookList(HttpServletRequest request, HttpServletResponse response, TextBookInfo textBookInfo,
            RedirectAttributes redirectAttributes, Model model) {
        Page<TextBookInfo> page = new Page<TextBookInfo>(request, response);
        textBookInfo.setCreater(UserUtils.getUser().getId());
        page = textBookService.findPage(page, textBookInfo);
        List<TextBookInfo> textList = page.getList();
        for (TextBookInfo text : textList) {
            try {
                text.setGradeId(gradeService.getByIdBuffer(text.getGradeId()).getName());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                text.setGradeId("组名未知，请设定组名");
            }
            text.setCreater(UserUtils.get(text.getCreater()).getLoginName());
        }

        model.addAttribute("page", page);
        return "modules/mmy/textbook/textBookList";

    }

    @RequestMapping("textBookForm")
    public String textBookForm(HttpServletRequest request, HttpServletResponse response, TextBookInfo textBookInfo,
            RedirectAttributes redirectAttributes, Model model) {
        List<GradeInfo> gradeList = gradeService.getAll();
        model.addAttribute("gradeList", gradeList);
        return "modules/mmy/textbook/textBookForm";
    }

    @RequestMapping("delById")
    @ResponseBody
    public Map<String, Object> delById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("flag", false);
        returnMap.put("msg", "删除失败，请联系管理员");
        String id = request.getParameter("id");
        // 先查询教材下是否存在课文
        int i = 0;
        try {
            i = lessionService.countLessionByTextId(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (i > 0) {
            returnMap.put("msg", "删除失败，请先清空教材下的课文");
            return returnMap;
        }

        try {
            i = textBookService.delById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (i > 0) {
            returnMap.put("flag", true);
            returnMap.put("msg", "删除成功");
        }

        return returnMap;

    }

    /**
     * 
     * getUnitInfoList(获取单元信息)
     */
    @RequestMapping("getUnitInfoList")
    @ResponseBody
    public Map<String, Object> getUnitInfoList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("flag", false);
        String id = request.getParameter("id");
        try {
            List<UnitInfo> unitInfoList = textBookService.getUnitInfoList(id);
            returnMap.put("data", unitInfoList);
            returnMap.put("flag", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return returnMap;

    }
}
