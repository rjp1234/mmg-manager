/**    
 * 文件名：UserInfoController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月5日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.user.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CkfinderUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.MD5Util;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.common.utils.excel.ExcelConditionalInfo;
import com.thinkgem.jeesite.common.utils.excel.ExcelUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.UserInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.UserInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * 项目名称：JeeSite 类名称：UserInfoController 类描述： 创建人：Administrator 创建时间：2018年6月5日
 * 下午1:12:25 修改人：Administrator 修改时间：2018年6月5日 下午1:12:25 修改备注：
 * 
 * @version
 * @模块功能：实现学生用户信息的导入、编辑、查看、删除功能
 */
@Controller
@RequestMapping("${adminPath}/operator/user")
public class UserInfoController extends BaseController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ClassInfoService classInfoService;

    @Autowired
    GradeInfoService gradeInfoService;

    @Autowired
    ClassInfoService classService;

    @ModelAttribute
    public UserInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return userInfoService.getById(id);
        } else {
            return new UserInfo();
        }
    }

    /**
     * 
     * userBatchForm(跳转至用户批量添加页面)
     * 
     * 
     */
    @RequestMapping("userBatchForm")
    public String userBatchForm(HttpServletRequest request, HttpServletResponse response, Model model,
            UserInfo userInfo) {
        List<GradeInfo> all = gradeInfoService.getAll();
        model.addAttribute("gradeList", all);
        return "modules/mmy/user/userBatchForm";

    }

    /**
     * 
     * userBatchCreate(批量添加学生用户)
     * 
     * 
     */
    @RequestMapping("userBatchCreate")
    public synchronized String userBatchCreate(HttpServletRequest request, HttpServletResponse response, Model model,
            UserInfo userInfo, RedirectAttributes redirectAttributes) {
        String classId = userInfo.getClassId();
        if (StringUtils.isBlank(classId)) {
            addMessage(redirectAttributes, "请选择班级");
            return "redirect:" + adminPath + "/operator/user/userBatchForm";
        }
        String excelUrl = request.getParameter("excelUrl");
        if (StringUtils.isBlank(excelUrl)) {
            addMessage(redirectAttributes, "文档路径不得为空");

            return "redirect:" + adminPath + "/operator/user/userBatchForm";
        }
        File file = null;
        try {
            file = CkfinderUtils.getFileFromCkpath(excelUrl);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            addMessage(redirectAttributes, "文件编码格式不支持");
        }
        // 从表格中获取realname , loginname,password,phonenum
        List<UserInfo> userList = analysisStuExcel(file);
        List<UserInfo> errorUserList = new ArrayList<UserInfo>();

        // 对表格存在的重名进行检查
        Set<String> loginnameCheck = new HashSet<String>();
        for (UserInfo user : userList) {
            if (loginnameCheck.contains(user.getLoginname())) {
                errorUserList.add(user);
            } else {
                loginnameCheck.add(user.getLoginname());
            }
        }
        // 将表格中携带的重名用户移除
        userList.removeAll(errorUserList);

        for (UserInfo userInfo2 : userList) {
            // 进行登录名重名检查
            int i = userInfoService.countByLoginname(userInfo2.getLoginname());
            if (i > 0) {
                errorUserList.add(userInfo2);
                continue;
            }
            userInfo2.setClassId(classId);
            userInfo2.setCreater(UserUtils.getUser().getId());
            userInfo2.setId(IdGen.uuid());
            userInfo2.setCreateTime(TimeUtils.formateNowDay2());
            userInfo2.setImage(Global.getConfig("defaultImg"));
        }
        // 删除校验不通过的用户
        userList.removeAll(errorUserList);
        int count = 0;
        try {
            count = userInfoService.insertBatch(userList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        String errorMsg = "";
        if (errorUserList.size() > 0) {
            errorMsg = "，有" + errorUserList.size() + "条记录插入失败。失败记录的登录名为";
            for (UserInfo user : errorUserList) {
                errorMsg = errorMsg + user.getLoginname() + ",";
            }
            // 去除最后的,
            errorMsg = errorMsg.substring(0, errorMsg.lastIndexOf(","));

        }

        addMessage(redirectAttributes, "成功插入" + count + "条记录" + errorMsg);
        return "redirect:" + adminPath + "/operator/user/userBatchForm";
        // return "redirect:" + adminPath + "/operator/user/userList?classId=" +
        // classId;

    }

    /**
     * 
     * analysisStuExcel(将特定格式的表格转换为user对象)
     * 
     */
    public static List<UserInfo> analysisStuExcel(File file) {
        List<UserInfo> userList = new ArrayList<UserInfo>();
        // 创建表单指针对象
        ExcelConditionalInfo excelConditional = new ExcelConditionalInfo();
        ExcelUtils excel = new ExcelUtils();
        excelConditional.setMincol(0);
        excelConditional.setMaxcol(3);
        // 设为默认表名
        excelConditional.setSheetName("Sheet1");
        int i = 1;
        while (true) {
            excelConditional.setLine(i++);
            try {
                List<String> rowList = excel.getFieldStrByExcelConditional(excelConditional, file);
                UserInfo userInfo = new UserInfo();
                userInfo.setRealname(rowList.get(0));
                userInfo.setLoginname(rowList.get(1));
                userInfo.setPassword(MD5Util.MD5(rowList.get(2)));
                userInfo.setPhonenum(rowList.get(3));
                userList.add(userInfo);
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        return userList;

    }

    /**
     * 
     * userForm(跳转至用户添加页面)
     * 
     * 
     */
    @RequestMapping("userForm")
    public String userForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<GradeInfo> all = gradeInfoService.getAll();
        model.addAttribute("gradeList", all);
        return "modules/mmy/user/userForm";
    }

    /**
     * 
     * userList(跳转至用户列表页面)
     * 
     */
    @RequestMapping("userList")
    public String userList(HttpServletRequest request, HttpServletResponse response, Model model, UserInfo userInfo) {
        Page<UserInfo> page = new Page<UserInfo>(request, response);
        page = userInfoService.findPage(page, userInfo);
        String gradeId = request.getParameter("gradeId");
        String classId = userInfo.getClassId();

        Map<String, GradeInfo> gradeBufferMap = new HashMap<String, GradeInfo>();
        List<GradeInfo> gradeList = gradeInfoService.getAll();
        for (GradeInfo gradeInfo : gradeList) {
            gradeBufferMap.put(gradeInfo.getId(), gradeInfo);
        }

        // 根据班级id查询对应的组
        Map<String, ClassInfo> classBufferMap = new HashMap<String, ClassInfo>();
        List<ClassInfo> classList = classService.getAll();
        for (ClassInfo classInfo : classList) {

            classBufferMap.put(classInfo.getId(), classInfo);
        }
        List<UserInfo> userList = page.getList();
        for (UserInfo user : userList) {
            boolean classFaild = false;
            String clasId = user.getClassId();
            ClassInfo classInfo = classBufferMap.get(clasId);
            if (classInfo != null) {
                // 载入班级名
                user.setClassId(classInfo.getName());
                // 载入组别id
                user.setGradeId(classInfo.getGradeId());
            } else {
                classFaild = true;
                user.setClassId("<span style='color:red'>未知班级，请对该学生进行编辑，绑定班级</span>");
            }
            // 根据组别id查询对应的组
            String graId = user.getGradeId();
            GradeInfo grade = gradeBufferMap.get(graId);
            if (grade == null) {
                if (!classFaild) {
                    user.setGradeId("<span style='color:red'>该学生所在班级未和年级组绑定，请对该班级进行编辑，绑定年级组</span>");
                }
            } else {
                user.setGradeId(grade.getName());
            }
            String creater = UserUtils.get(user.getCreater()).getLoginName();
            user.setCreater(creater);

        }
        if (StringUtils.isNotBlank(gradeId)) {
            model.addAttribute("gradeId", gradeId);
        }
        if (StringUtils.isNotBlank(classId)) {
            model.addAttribute("className", classBufferMap.get(classId).getName());

        }
        model.addAttribute("classId", classId);

        model.addAttribute("page", page);
        model.addAttribute("gradeList", gradeList);
        model.addAttribute("classList", classList);
        return "modules/mmy/user/userList";
    }

    @RequestMapping("checkLoginName")
    @ResponseBody
    public Map<String, Object> checkLoginName(HttpServletRequest request, HttpServletResponse response, Model model,
            UserInfo userInfo) {
        String loginname = request.getParameter("loginname");
        int i = userInfoService.countByLoginname(loginname);
        boolean flag = false;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("flag", flag);
        if (i > 0) {
            returnMap.put("msg", "<span style='color:red'>登录名已存在</span>");
        } else {
            returnMap.put("msg", "<span style='color:green'>该登录名可以使用</span>");
        }

        return returnMap;

    }

    @RequestMapping("createUser")
    public String createUser(HttpServletRequest request, HttpServletResponse response, Model model, UserInfo userInfo,
            RedirectAttributes redirectAttributes) {

        // 进行登录名重名检查
        int i = userInfoService.countByLoginname(userInfo.getLoginname());
        if (i != 0) {
            addMessage(redirectAttributes, "创建失败:登录名已存在");
            return "redirect:" + adminPath + "/operator/user/userForm";
        }
        // 班级校验
        if (StringUtils.isBlank(userInfo.getClassId())) {
            addMessage(redirectAttributes, "创建失败:必须绑定班级");
            return "redirect:" + adminPath + "/operator/user/userForm";
        }
        ClassInfo classInfo = classInfoService.getById(userInfo.getClassId());
        if (classInfo == null) {
            addMessage(redirectAttributes, "创建失败:指定的班级不存在");
            return "redirect:" + adminPath + "/operator/user/userForm";
        }
        if (StringUtils.isBlank(userInfo.getPassword()) || userInfo.getPassword().length() < 6) {
            addMessage(redirectAttributes, "创建失败:密码长度不符合要求");
            return "redirect:" + adminPath + "/operator/user/userForm";
        }

        userInfo.setCreater(UserUtils.getUser().getId());
        userInfo.setId(IdGen.uuid());
        userInfo.setCreateTime(TimeUtils.formateNowDay2());
        userInfo.setImage(Global.getConfig("defaultImg"));
        userInfo.setPassword(MD5Util.MD5(userInfo.getPassword()));
        int count = 0;
        try {
            count = userInfoService.insert(userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (count == 0) {
            addMessage(redirectAttributes, "创建失败，请联系管理员");
        }

        return "redirect:" + adminPath + "/operator/user/userList";

    }

    @RequestMapping("delById")
    @ResponseBody
    public Map<String, Object> delById(HttpServletRequest request, HttpServletResponse response, Model model,
            UserInfo userInfo, RedirectAttributes redirectAttributes) {
        String id = request.getParameter("id");
        int i = 0;
        try {
            i = userInfoService.delById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (i > 0) {
            returnMap.put("flag", true);
        } else {
            returnMap.put("flag", false);
        }

        return returnMap;

    }

}
