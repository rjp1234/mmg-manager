/**    
 * 文件名：LessionController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月11日    
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
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * 项目名称：mmg-manager 类名称：LessionController 类描述： 创建人：Administrator 创建时间：2018年6月11日
 * 上午9:44:55 修改人：Administrator 修改时间：2018年6月11日 上午9:44:55 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/lession")
public class LessionController extends BaseController {
    @Autowired
    LessionInfoService lessionService;

    @Autowired
    TextBookService textBookService;

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
     * lessionList(课程列表)
     * 
     * 
     */
    @RequestMapping("lessionList")
    public String lessionList(HttpServletRequest request, HttpServletResponse response, LessionInfo lessionInfo,
            RedirectAttributes redirectAttributes, Model model) {
        List<TextBookInfo> textList = textBookService.getAll();
        Page<LessionInfo> page = new Page<LessionInfo>(request, response);
        page = lessionService.findPage(page, lessionInfo);
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
        return "modules/mmy/lession/lessionList";
    }

    /**
     * 
     * lessionForm(课文创建页面)
     * 
     * 
     */
    @RequestMapping("lessionForm")
    public String lessionForm(HttpServletRequest request, HttpServletResponse response, LessionInfo lessionInfo,
            RedirectAttributes redirectAttributes, Model model) {
        List<TextBookInfo> textList = textBookService.getAll();

        model.addAttribute("textList", textList);
        return "modules/mmy/lession/lessionForm";
    }

    /**
     * 
     * createLession(创建课程)
     * 
     * 
     */
    @RequestMapping("createLession")
    public String createLession(HttpServletRequest request, HttpServletResponse response, LessionInfo lessionInfo,
            RedirectAttributes redirectAttributes, Model model) {
        String name = lessionInfo.getName();
        String id = IdGen.uuid();
        lessionInfo.setId(id);
        if (StringUtils.isBlank(name)) {
            addMessage(redirectAttributes, "课文标题不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        String content = lessionInfo.getContent();
        if (StringUtils.isBlank(content)) {
            addMessage(redirectAttributes, "课文文本不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        String textId = lessionInfo.getTextId();
        if (StringUtils.isBlank(textId)) {
            addMessage(redirectAttributes, "课文所属教材不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        int unit = lessionInfo.getUnit();
        if (unit == 0) {
            addMessage(redirectAttributes, "课文所属单元不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }

        /**
         * 上传课文示范录音校验 start
         */

        String exampleUrl = lessionInfo.getExampleUrl();
        if (StringUtils.isBlank(exampleUrl)) {
            addMessage(redirectAttributes, "请上传课文对应的示范录音不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        File file = null;
        try {
            file = CkfinderUtils.getFileFromCkpath(exampleUrl);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (file == null) {
            addMessage(redirectAttributes, "向服务器上传示范录音失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }

        try {
            String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_STUDIO_TEA, file.getPath(), id);
            lessionInfo.setExampleUrl(path);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (StringUtils.isBlank(lessionInfo.getExampleUrl())) {

            addMessage(redirectAttributes, "向七牛云上传示范录音失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";

        }

        /**
         * 上传课文示范录音校验 end
         */
        /**
         * 上传课文封面校验start
         */
        String image = lessionInfo.getImage();
        if (StringUtils.isBlank(image)) {
            addMessage(redirectAttributes, "上传课文封面路径不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";

        }
        file = null;
        try {
            file = CkfinderUtils.getFileFromCkpath(image);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (file == null) {
            addMessage(redirectAttributes, "向服务器上传封面失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        try {
            String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_IMAGE_TEA, file.getPath(), id);
            lessionInfo.setImage(path);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (StringUtils.isBlank(lessionInfo.getImage())) {

            addMessage(redirectAttributes, "向七牛云上传封面失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";

        }
        /**
         * 上传课文封面end
         */

        /**
         * 上传教师录音start
         */
        String tStudioUrl = lessionInfo.gettStudioUrl();
        if (StringUtils.isBlank(tStudioUrl)) {
            addMessage(redirectAttributes, "请上传课文对应的教师录音不得为空");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        file = null;
        try {
            file = CkfinderUtils.getFileFromCkpath(tStudioUrl);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (file == null) {
            addMessage(redirectAttributes, "向服务器上传教师录音失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";
        }
        try {
            String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_STUDIO_TEA, file.getPath(), id);
            lessionInfo.settStudioUrl(path);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (StringUtils.isBlank(lessionInfo.gettStudioUrl())) {

            addMessage(redirectAttributes, "向七牛云上传教师录音失败，请联系管理员");
            return "redirect:" + adminPath + "/operator/lession/lessionForm";

        }

        lessionInfo.setCreater(UserUtils.getUser().getId());
        lessionInfo.setCreateTime(TimeUtils.formateNowDay2());
        lessionService.insertLession(lessionInfo);
        addMessage(redirectAttributes, "插入课文成功");
        return "redirect:" + adminPath + "/operator/lession/lessionList";

    }

    @RequestMapping("checkLessionName")
    @ResponseBody
    public Map<String, Object> checkLessionName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("flag", false);
        if (StringUtils.isBlank(name)) {
            returnMap.put("msg", "<span style='color:red'>课程名不得为空</span>");
            return returnMap;
        }
        int i = lessionService.countLessionByName(name);

        returnMap.put("msg", "<span style='color:red'>该课程名称已经存在</span>");
        if (i == 0) {
            returnMap.put("flag", true);
            returnMap.put("msg", "<span style='color:green'>该名称可以使用</span>");
        }

        return returnMap;

    }

    /**
     * 
     * lessionIssue(课程下发)
     * 
     * 
     */
    @RequestMapping("lessionIssue")
    @ResponseBody
    public Map<String, Object> lessionIssue(HttpServletRequest request, HttpServletResponse response) {
        String classId = request.getParameter("classId");

        return null;

    }

}
