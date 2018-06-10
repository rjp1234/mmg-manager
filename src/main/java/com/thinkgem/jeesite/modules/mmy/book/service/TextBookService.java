/**    
 * 文件名：TextBookService.java    
 *    
 * 版本信息：    
 * 日期：2018年6月10日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.mmy.book.dao.TextBookDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;

/**
 * 
 * 项目名称：mmg-manager 类名称：TextBookService 类描述： 创建人：Administrator 创建时间：2018年6月10日
 * 下午3:43:43 修改人：Administrator 修改时间：2018年6月10日 下午3:43:43 修改备注：
 * 
 * @version
 * 
 */
@Service
public class TextBookService extends CrudService<TextBookDao, TextBookInfo> {
    @Autowired
    TextBookDao textBookDao;

    public int insertTextBook(TextBookInfo book) {
        book.setCreateTime(TimeUtils.formateNowDay2());
        return textBookDao.insert(book);

    }

    /**
     * 
     * getById(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public TextBookInfo getById(String id) {
        TextBookInfo book = new TextBookInfo();
        return textBookDao.getById(book);
    }

}
