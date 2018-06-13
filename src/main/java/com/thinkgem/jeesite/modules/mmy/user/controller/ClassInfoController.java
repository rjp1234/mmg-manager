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

import java.util.ArrayList;
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
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionClassBindInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionClassBindService;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.UserInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.UserInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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

    @Autowired
    LessionClassBindService bindService;

    @Autowired
    LessionInfoService lessionService;

    @Autowired
    TextBookService textService;

    @Autowired
    UserInfoService userInfoService;

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
     * getByGradeId(根据组名获取旗下班级)
     */
    @RequestMapping("getByGradeId")
    @ResponseBody
    public Map<String, Object> getByGradeId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String gradeId = request.getParameter("gradeId");
        returnMap.put("flag", false);
        try {
            List<ClassInfo> listByGrade = classService.getListByGrade(gradeId);
            returnMap.put("data", listByGrade);
            returnMap.put("flag", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return returnMap;

    }

    /**
     * 
     * classForm(跳转至班级页面)
     * 
     */

    @RequestMapping("classForm")
    public String classForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        List<GradeInfo> all = gradeService.getAll();
        model.addAttribute("gradeList", all);
        if (StringUtils.isNotBlank(id)) {
            return "modules/mmy/class/classModifyForm";
        }
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
        if (StringUtils.isBlank(classInfo.getName())) {
            addMessage(redirectAttributes, "班级名不得为空");
            return "redirect:" + adminPath + "/operator/class/classForm";
        }
        int i = classService.createClass(gradeInfo, classInfo.getName());
        if (i == 1) {
            addMessage(redirectAttributes, "班级名" + classInfo.getName() + "创建成功");
        } else {
            addMessage(redirectAttributes, "班级名" + classInfo.getName() + "创建失败");
            return "redirect:" + adminPath + "/operator/class/classForm";
        }
        return "redirect:" + adminPath + "/operator/class/classList";
    }

    @RequestMapping("modifyClass")
    public String modifyClassName(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo,
            Model model, RedirectAttributes redirectAttributes) {
        ClassInfo originClass = classService.getById(classInfo.getId());
        String className1 = classInfo.getName();// 将要改变的
        String className2 = originClass.getName();// 改变之前的
        if (StringUtils.isNotBlank(className1) && (!className1.equals(className2))) {
            // 需要更新名称
            // 更新的名称不能在班级列表中出现
            int i = classService.updateClassName(originClass.getId(), className1);
            if (i < 0) {
                addMessage(redirectAttributes, "班级名称重名");
                return "redirect:" + adminPath + "/operator/class/classForm?id=" + originClass.getId();
            }
        }

        /**
         * 更新组名后原课程绑定记录需清空
         */
        List<LessionClassBindInfo> bindList = bindService.getByClassId(originClass.getId());
        for (LessionClassBindInfo bind : bindList) {
            try {
                bindService.delById(bind.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        /**
         * 更新组名
         */
        GradeInfo grade = gradeService.getById(classInfo.getGradeId());
        try {
            classService.updateGradeId(grade, classInfo.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            addMessage(redirectAttributes, "组名更新失败");
            return "redirect:" + adminPath + "/operator/class/classForm?id=" + originClass.getId();
        }

        return "redirect:" + adminPath + "/operator/class/classList";

    }

    /**
     * 
     * classList(班级列表)
     * 
     * 
     */
    @RequestMapping("classList")
    public String classList(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo,
            Model model) {
        Page<ClassInfo> page = new Page<ClassInfo>(request, response);
        page = classService.findPage(page, classInfo);
        List<ClassInfo> classList = page.getList();
        for (ClassInfo cl : classList) {
            // 数据库存储的creater是创建人的id，这里要将他转换成创建人名
            String createId = cl.getCreater();
            String createName = UserUtils.get(createId).getLoginName();
            cl.setCreater(createName);
            // 数据库存储的gradeId，这里将他换成gradeName
            String gradeId = cl.getGradeId();
            GradeInfo grade = gradeService.getByIdBuffer(gradeId);
            if (grade != null) {
                cl.setGradeId(grade.getName());
            } else {
                cl.setGradeId("<span style='color:red'>该班级未和正确的组关联，请点击编辑进行设置</span>");
            }
        }
        List<GradeInfo> gradeList = new ArrayList<GradeInfo>();
        try {
            gradeList = gradeService.getAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        model.addAttribute("gradeList", gradeList);
        model.addAttribute("page", page);
        return "modules/mmy/class/classList";

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

    @RequestMapping("delClassById")
    public String delClassById(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo,
            Model model, RedirectAttributes redirectAttributes) {
        // 班级下学生删除
        List<UserInfo> userList = userInfoService.getListByClassId(classInfo.getId());

        for (UserInfo userInfo : userList) {
            try {
                int j = userInfoService.delById(userInfo.getId());
                System.out.println(j);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        // 班级下课程关联删除
        List<LessionClassBindInfo> bindList = bindService.getByClassId(classInfo.getId());
        for (LessionClassBindInfo bind : bindList) {
            try {
                int j = bindService.delById(bind.getId());
                System.out.println(j);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        int i = classService.delById(classInfo.getId());

        if (i == 1) {
            addMessage(redirectAttributes, "删除成功");
        } else {
            addMessage(redirectAttributes, "删除失败");
        }

        return "redirect:" + adminPath + "/operator/class/classList";

    }

    @RequestMapping("getLessionByClassId")
    public String getLessionByClassId(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo,
            Model model, RedirectAttributes redirectAttributes) {
        String classId = classInfo.getId();
        List<LessionClassBindInfo> bindList = bindService.getByClassId(classId);
        List<LessionInfo> lessionList = new ArrayList<LessionInfo>();
        for (LessionClassBindInfo bind : bindList) {
            LessionInfo lessionInfo = lessionService.getById(bind.getLessionId());
            lessionInfo.setTextId(textService.getByIdBuffer(lessionInfo.getTextId()).getName());
            lessionList.add(lessionInfo);
        }
        model.addAttribute("lessionList", lessionList);
        model.addAttribute("classInfo", classInfo);
        addMessage(model, "当前正在查看班级：" + classInfo.getName() + "的下发课文");
        return "modules/mmy/class/classBindLessionList";

    }

    public static void main(String[] args) {
        System.out.println(Global.getAdminPath());
    }
}
