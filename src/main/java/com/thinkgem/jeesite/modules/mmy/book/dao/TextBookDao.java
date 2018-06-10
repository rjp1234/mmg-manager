package com.thinkgem.jeesite.modules.mmy.book.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;

@MyBatisDao
public interface TextBookDao extends CrudDao<TextBookInfo> {

    /**
     * 
     * getById(这里用一句话描述这个方法的作用)
     * 
     */
    TextBookInfo getById(TextBookInfo book);

}
