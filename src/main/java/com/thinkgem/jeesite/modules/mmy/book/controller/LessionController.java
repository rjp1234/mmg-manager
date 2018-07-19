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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionClassBindInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionClassBindService;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
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

    @RequestMapping("detailsForm")
    public String detailsForm(HttpServletRequest request, HttpServletResponse response, LessionInfo lessionInfo,
            RedirectAttributes redirectAttributes, Model model) {
        List<TextBookInfo> textList = textBookService.getAll();
        model.addAttribute("textList", textList);
        try {
            lessionInfo.setTextId(textBookService.getById(lessionInfo.getTextId()).getName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String unit = "第" + lessionInfo.getUnit() + "单元";
        model.addAttribute("unit", unit);

        return "modules/mmy/lession/lessionDetailForm";
    }

    @RequestMapping("modifyLession")
    public synchronized String modifyLession(HttpServletRequest request, HttpServletResponse response,
            LessionInfo lessionInfo, RedirectAttributes redirectAttributes, Model model) {
        String lessionId = lessionInfo.getId();
        LessionInfo lessionChange = new LessionInfo();
        lessionChange.setId(lessionId);
        TextBookInfo text = textBookService.getById(lessionInfo.getTextId());
        LessionInfo originLession = lessionService.getById(lessionId);
        if (originLession == null) {
            addMessage(redirectAttributes, "课文不存在，无法修改");
            return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;
        }
        if (StringUtils.isNotBlank(lessionInfo.getName())
                && !StringUtils.equals(lessionInfo.getName(), originLession.getName())) {
            logger.info("名称更新");
            int i = lessionService.countLessionByName(lessionInfo.getName());
            if (i > 0) {
                addMessage(redirectAttributes, "存在重名课文，请修改");
                return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;
            }
            lessionChange.setName(lessionInfo.getName());

        }
        if (StringUtils.isNotBlank(lessionInfo.getTextId())
                && !StringUtils.equals(lessionInfo.getTextId(), originLession.getTextId())) {
            logger.info("更新所属教材");

            if (text == null) {
                addMessage(redirectAttributes, "教材不存在，请重新选择");
                return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;
            }
            lessionChange.setTextId(text.getId());

        }

        if (lessionInfo.getUnit() != 0 && originLession.getUnit() != lessionInfo.getUnit()) {
            logger.info("单元更新");
            if (text.getUnitNum() < lessionInfo.getUnit()) {
                addMessage(redirectAttributes, "单元错误，请重新选择");
                return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;

            }
            lessionChange.setUnit(lessionInfo.getUnit());

        }
        if (StringUtils.isNotBlank(lessionInfo.getContent())
                && !StringUtils.equals(originLession.getContent(), lessionInfo.getContent())) {
            logger.info("文本更新");
            lessionChange.setContent(lessionInfo.getContent());

        }

        if (StringUtils.isNotBlank(lessionInfo.getExampleUrl())
                && !StringUtils.equals(lessionInfo.getExampleUrl(), originLession.getExampleUrl())) {
            logger.info("更新示范录音");
            File file = null;
            try {
                file = CkfinderUtils.getFileFromCkpath(lessionInfo.getExampleUrl());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (file == null) {
                addMessage(redirectAttributes, "示范录音路径错误，请重新选择");
                return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;
            }
            try {
                String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_STUDIO_TEA, file.getPath(),
                        IdGen.uuid() + ".mp3");
                lessionChange.setExampleUrl(path);
            } catch (Exception e) {
                addMessage(redirectAttributes, "示范录音路径错误，请重新选择");
                logger.error(e.getMessage(), e);
            }

        }
        if (StringUtils.isNotBlank(lessionInfo.getImage())
                && !StringUtils.equals(lessionInfo.getImage(), originLession.getImage())) {
            logger.info("更新封面");
            File file = null;
            try {
                file = CkfinderUtils.getFileFromCkpath(lessionInfo.getImage());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (file == null) {
                addMessage(redirectAttributes, "封面路径错误，请重新选择");
                return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;
            }
            try {
                String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_IMAGE_TEA, file.getPath(),
                        IdGen.uuid() + ".jpg");
                lessionChange.setImage(path);
            } catch (Exception e) {
                addMessage(redirectAttributes, "封面路径错误，请重新选择");
                logger.error(e.getMessage(), e);
            }

        }
        if (StringUtils.isNotBlank(lessionInfo.gettStudioUrl())
                && !StringUtils.equals(lessionInfo.gettStudioUrl(), originLession.gettStudioUrl())) {
            logger.info("更新教师的话（录音）");

            File file = null;
            try {
                file = CkfinderUtils.getFileFromCkpath(lessionInfo.gettStudioUrl());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (file == null) {
                addMessage(redirectAttributes, "教师录音路径错误，请重新选择");
                return "redirect:" + adminPath + "/operator/lession/lessionForm?id=" + lessionId;
            }
            try {
                String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_IMAGE_TEA, file.getPath(),
                        IdGen.uuid() + ".mp3");
                lessionChange.settStudioUrl(path);
            } catch (Exception e) {
                addMessage(redirectAttributes, "教师录音路径错误，请重新选择");
                logger.error(e.getMessage(), e);
            }

        }
        if (StringUtils.isNotBlank(lessionInfo.gettContent())
                && !StringUtils.equals(lessionInfo.gettContent(), originLession.gettContent())) {
            logger.info("更新教师的话（文本）");
            lessionChange.settContent(lessionInfo.gettContent());
        }
        int i = lessionService.modifyLession(lessionChange);
        if (i == 1) {
            addMessage(redirectAttributes, "修改成功");
        }

        return "redirect:" + adminPath + "/operator/lession/lessionList";

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
            String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_STUDIO_TEA, file.getPath(),
                    IdGen.uuid() + ".mp3");
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
            String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_IMAGE_TEA, file.getPath(),
                    IdGen.uuid() + ".jpg");
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
            String path = FileLoadUtils.QIniuupload(FileLoadUtils.SOURCE_TYPE_STUDIO_TEA, file.getPath(),
                    IdGen.uuid() + ".mp3");
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

    @RequestMapping("getClassInfoListByLession")
    public String getClassInfoListByLession(HttpServletRequest request, HttpServletResponse response,
            LessionInfo lessionInfo, RedirectAttributes redirectAttributes, Model model) {
        List<LessionClassBindInfo> bindList = lessionClassBindService.getByLessionId(lessionInfo.getId());
        List<ClassInfo> classList = new ArrayList<ClassInfo>();
        for (LessionClassBindInfo bind : bindList) {
            ClassInfo classInfo = classService.getById(bind.getClassId());
            classInfo.setGradeId(gradeService.getByIdBuffer(classInfo.getGradeId()).getName());
            classInfo.setCreater(UserUtils.get(classInfo.getCreater()).getLoginName());
            classList.add(classInfo);
        }
        model.addAttribute("lessionInfo", lessionInfo);
        model.addAttribute("classList", classList);
        addMessage(model, "当前正在查看课文:" + lessionInfo.getName() + "的下发班级列表");
        return "modules/mmy/lession/lessionBindClassList";

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

    @RequestMapping("lessionIssueClassList")
    @ResponseBody
    public Map<String, Object> lessionIssueClassList(HttpServletRequest request, HttpServletResponse response) {
        String lessionId = request.getParameter("lessionId");
        LessionInfo lession = lessionService.getById(lessionId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", false);
        map.put("msg", "获取班级信息失败");
        if (lession == null) {
            return map;
        }
        TextBookInfo text = textBookService.getById(lession.getTextId());
        if (text == null) {
            return map;
        }
        GradeInfo grade = gradeService.getById(text.getGradeId());
        if (grade == null) {
            return map;
        }
        try {
            // 获得即将下发的班级
            List<ClassInfo> classList = classService.getListByGrade(grade.getId());
            // 移除已经下发过的班级
            // 获取全部下发过该课程的班级
            List<LessionClassBindInfo> bindList = lessionClassBindService.getByLessionId(lessionId);
            Set<String> classBindId = new HashSet<String>();
            // 将班级的id提取
            for (LessionClassBindInfo bind : bindList) {
                classBindId.add(bind.getClassId());
            }

            Iterator<ClassInfo> iterator = classList.iterator();
            while (iterator.hasNext()) {
                ClassInfo c = iterator.next();
                // 该班级已经在绑定列表中，则移除，不需要重复绑定
                if (classBindId.contains(c.getId())) {
                    iterator.remove();
                }
            }

            map.put("flag", true);
            map.put("data", classList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return map;

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

        // String issueType = request.getParameter("issueType");
        String lessionId = request.getParameter("lessionId");
        String classId = request.getParameter("classId");
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("flag", false);
        map.put("msg", "下发失败，请联系管理员");
        if (StringUtils.isBlank(classId) || StringUtils.isBlank(lessionId)) {
            return map;
        }
        try {
            int i = lessionClassBindService.lessionBindClass(lessionId, classId);
            if (i == -1) {
                map.put("msg", "该课文已经对该班级进行过下发，不必重复提交");
                return map;
            }
            if (i == 1) {
                ClassInfo classInfo = classService.getById(classId);
                LessionInfo lession = lessionService.getById(lessionId);
                map.put("msg", "课文:<span style='color:green'>" + lession.getName()
                        + "</span>下发到班级:<span style='color:green'>" + classInfo.getName() + "</span>成功");
                map.put("flag", true);
                return map;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    @RequestMapping("cancelIssue")
    @ResponseBody
    public Map<String, Object> cancelIssue(HttpServletRequest request, HttpServletResponse response) {
        String lessionId = request.getParameter("lessionId");
        String classId = request.getParameter("classId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", false);
        map.put("msg", "解除下发失败");
        if (StringUtils.isBlank(lessionId) || StringUtils.isBlank(classId)) {
            return map;
        }
        try {
            int i = lessionClassBindService.delByClassIdAndLessionId(classId, lessionId);
            if (i == 1) {
                map.put("flag", true);
                map.put("msg", "解除下发成功");
                return map;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    @RequestMapping("delById")
    @ResponseBody
    public synchronized Map<String, Object> delById(HttpServletRequest request, HttpServletResponse response) {
        String lessionId = request.getParameter("lessionId");
        List<LessionClassBindInfo> bindList = lessionClassBindService.getByLessionId(lessionId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", false);
        map.put("msg", "删除失败");
        // 删除该课文与全部班级之间关联
        for (LessionClassBindInfo bind : bindList) {
            try {
                lessionClassBindService.delById(bind.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        int i = lessionService.delById(lessionId);
        if (i > 0) {
            map.put("flag", true);
            map.put("msg", "删除成功");
        }

        return map;
    }

}
