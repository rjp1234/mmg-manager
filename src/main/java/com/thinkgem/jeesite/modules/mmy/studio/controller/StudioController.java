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
import com.thinkgem.jeesite.modules.mmy.studio.entity.StudioInfo;
import com.thinkgem.jeesite.modules.mmy.studio.service.StudioInfoService;

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

    @ModelAttribute
    public StudioInfo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return studioService.get(id);
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
        Page<StudioInfo> page = new Page<StudioInfo>(request, response);
        page = studioService.getPage(page, studioInfo);
        model.addAttribute("page", page);
        return "modules/mmy/studio/studioList";

    }

    @RequestMapping("studioForm")
    public String studioForm(HttpServletRequest request, HttpServletResponse response, StudioInfo studioInfo,
            RedirectAttributes redirectAttributes, Model model) {
        return adminPath;

    }

}
