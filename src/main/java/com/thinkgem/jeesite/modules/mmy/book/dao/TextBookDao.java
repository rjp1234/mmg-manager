package com.thinkgem.jeesite.modules.mmy.book.dao;

import java.util.List;

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

    /**
     * 
     * countByName(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    int countByName(TextBookInfo textBookInfo);

    /**
     * 
     * delById(这里用一句话描述这个方法的作用)
     * 
     * TODO(这里描述这个方法适用条件 – 可选)
     * 
     * TODO(这里描述这个方法的执行流程 – 可选)
     * 
     * TODO(这里描述这个方法的使用方法 – 可选)
     * 
     * TODO(这里描述这个方法的注意事项 – 可选)
     * 
     * @param name
     * 
     * @param @return
     *            设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     * 
     */
    int delById(TextBookInfo textBookInfo);

    /**
     * 
     * getAll(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    List<TextBookInfo> getAll(TextBookInfo textBookInfo);

}
