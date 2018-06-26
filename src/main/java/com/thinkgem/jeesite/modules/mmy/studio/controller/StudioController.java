/**    
 * 文件名：StudioController.java    
 *    
 * 版本信息：    
 * 日期：2018年6月25日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.studio.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.thinkgem.jeesite.modules.mmy.book.entity.LessionInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.service.LessionInfoService;
import com.thinkgem.jeesite.modules.mmy.book.service.TextBookService;
import com.thinkgem.jeesite.modules.mmy.studio.entity.StudioInfo;
import com.thinkgem.jeesite.modules.mmy.studio.service.StudioInfoService;
import com.thinkgem.jeesite.modules.mmy.user.entity.ClassInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.GradeInfo;
import com.thinkgem.jeesite.modules.mmy.user.entity.UserInfo;
import com.thinkgem.jeesite.modules.mmy.user.service.ClassInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.GradeInfoService;
import com.thinkgem.jeesite.modules.mmy.user.service.UserInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * 项目名称：mmg-manager 类名称：StudioController 类描述： 创建人：Administrator 创建时间：2018年6月25日
 * 下午4:57:38 修改人：Administrator 修改时间：2018年6月25日 下午4:57:38 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("${adminPath}/operator/studio")
public class StudioController extends BaseController {
    @Autowired
    StudioInfoService studioService;

    @Autowired
    ClassInfoService classService;

    @Autowired
    LessionInfoService lessionService;

    @Autowired
    TextBookService textService;

    @Autowired
    UserInfoService userService;

    @Autowired
    GradeInfoService gradeInfoService;

    public static final List<String> COMMON_COMMENT = new ArrayList<String>(
            Arrays.asList(Global.getConfig("common_comment").split(";")));

    @ModelAttribute
    public StudioInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            System.out.println(studioService.getById(id));
            return studioService.getById(id);
        } else {
            return new StudioInfo();
        }
    }

    /**
     * 
     * studioList(返回录音待评分或已评分列表)
     * 
     * @param studioInfo.isPointed==
     *            0 未评分 1 已评分
     * 
     * 
     */
    @RequestMapping("studioList")
    public String studioList(HttpServletRequest request, HttpServletResponse response, StudioInfo studioInfo,
            RedirectAttributes redirectAttributes, Model model) {
        String lessionId = request.getParameter("lessionId");
        // 根据课文的id获取其下录音列表
        if (StringUtils.isNotBlank(lessionId)) {
            studioInfo.setLessionId(lessionId);
        }
        if (StringUtils.isBlank(studioInfo.getIsPointed())) {
            // 第一次查询，默认搜索未打分的对象
            studioInfo.setIsPointed(StudioInfo.POINT_IS_NOT_POINTED);
        }
        String searchName = studioInfo.getUserId();
        // 返回的userId不为空，说明用户进行了名称查询，此时userID代表用户真实姓名
        if (StringUtils.isNotBlank(studioInfo.getUserId())) {
            UserInfo user = userService.getByRealName(studioInfo.getUserId());
            studioInfo.setUserId(user == null ? searchName : user.getId());
        }
        Page<StudioInfo> page = new Page<StudioInfo>(request, response);
        page = studioService.getPage(page, studioInfo);
        // 根据课文id获取课文对应教材
        LessionInfo lession = lessionService.getById(lessionId);
        // 根据教材id获取所属教材
        TextBookInfo textBook = textService.getById(lession.getTextId());
        // 根据所属教材获取使用该教材的组别,根据组别id获取对应班级列表
        List<ClassInfo> classList = classService.getListByGrade(textBook.getGradeId());
        Map<String, ClassInfo> classMap = new HashMap<>();
        // 组装id-班级 map 方便组装返回对象时进行班级查询
        for (ClassInfo classInfo : classList) {
            classMap.put(classInfo.getId(), classInfo);
        }

        /**
         * 组装返回对象
         */
        List<StudioInfo> studioList = page.getList();
        // false说明当前返回的是未打分的列表
        boolean searchPoint = false;
        for (StudioInfo st : studioList) {
            try {

                // 列表包含字段 学生名-班级-上传时间 -评分（已评分列表有 ）
                // 获取学生姓名
                UserInfo user = userService.getById(st.getUserId());
                st.setUserId(user.getRealname());
                // 获取班级名
                st.setClassId(classMap.get(user.getClassId()).getName());
                if (StringUtils.isNotBlank(st.getPointer())) {
                    // 当前搜索的是已打分的列表
                    searchPoint = true;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        studioInfo.setUserId(searchName);
        // 搜索标记，指示搜索的是已评分还是未评分
        model.addAttribute("searchPoint", searchPoint);
        // 课文下发的年级
        model.addAttribute("gradeInfo", gradeInfoService.getById(textBook.getGradeId()));
        // 课文对应的班级列表
        model.addAttribute("classList", classList);
        // 录音分页
        model.addAttribute("page", page);

        return "modules/mmy/studio/studioList";

    }

    @RequestMapping("studioPointForm")
    public String studioPointForm(HttpServletRequest request, HttpServletResponse response, StudioInfo studioInfo,
            RedirectAttributes redirectAttributes, Model model) {
        String classIdSearch = request.getParameter("classIdSearch");
        UserInfo userInfo = userService.getById(studioInfo.getUserId());
        ClassInfo classInfo = classService.getById(userInfo.getClassId());
        GradeInfo gradeInfo = gradeInfoService.getById(classInfo.getGradeId());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("classInfo", classInfo);
        model.addAttribute("gradeInfo", gradeInfo);
        model.addAttribute("classIdSearch", classIdSearch);
        model.addAttribute("commonComment", COMMON_COMMENT);
        return "modules/mmy/studio/studioPointForm";

    }

    @RequestMapping("studioInfoPoint")
    @ResponseBody
    public Map<String, Object> studioInfoPoint(HttpServletRequest request, HttpServletResponse response,
            StudioInfo studioInfo) {
        String point = request.getParameter("point");
        String comment = request.getParameter("comment");
        String classId = request.getParameter("classId");
        User user = UserUtils.getUser();
        String pointer = user.getId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", false);
        map.put("msg", "批改失败，请联系系统管理员");
        /**
         * 参数校验
         */
        if (StringUtils.isNumeric(point)) {
            map.put("msg", "分数必须为整数数");
            return map;
        }
        if (Integer.parseInt(point) > 10) {
            map.put("msg", "分数满分为10分");
            return map;
        }
        int i = studioService.pointStudio(studioInfo.getId(), comment, Integer.parseInt(point), pointer);
        if (i == 1) {
            map.put("flag", true);
            map.put("msg", "批改成功");
            StudioInfo nextStudio = studioService.getNextUnpointStudio(studioInfo.getLessionId(), classId);
            map.put("nextStudio", nextStudio);
            return map;
        } else {
            return map;
        }

    }

}
