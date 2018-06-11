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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.ThreadPool;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.mmy.book.dao.TextBookDao;
import com.thinkgem.jeesite.modules.mmy.book.entity.TextBookInfo;
import com.thinkgem.jeesite.modules.mmy.book.entity.UnitInfo;

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

    /**
     * 
     * insertTextBook(这里用一句话描述这个方法的作用) 插入教材，返回-1说明同名教材已经存在
     * 
     */
    @Transactional(readOnly = false)
    public synchronized int insertTextBook(TextBookInfo book) {
        if (countByName(book.getName()) > 0) {
            return -1;
        }
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
        book.setId(id);
        return textBookDao.getById(book);
    }

    private static final Map<String, TextBookInfo> textBufferMap = new ConcurrentHashMap<String, TextBookInfo>();

    private static long lastModifyTime = System.currentTimeMillis();

    public TextBookInfo getByIdBuffer(String id) {
        TextBookInfo text = textBufferMap.get(id);
        if (text == null) {
            text = getById(id);
            textBufferMap.put(id, text);
        } else {
            long differ = System.currentTimeMillis() - lastModifyTime;
            if (differ > 10000) {
                try {
                    textBufferMap.clear();
                    ThreadPool.getInstance().execute(() -> textBufferMap.put(id, getById(id)));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return text;

    }

    /**
     * 
     * countByName(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    public int countByName(String name) {
        TextBookInfo textBookInfo = new TextBookInfo();
        textBookInfo.setName(name);

        return textBookDao.countByName(textBookInfo);
    }

    /**
     * 
     * delById(这里用一句话描述这个方法的作用)
     * 
     * 
     */
    @Transactional(readOnly = false)
    public int delById(String id) {
        TextBookInfo textBookInfo = new TextBookInfo();
        textBookInfo.setId(id);
        return textBookDao.delById(textBookInfo);
    }

    /**
     * 
     * getAll(这里用一句话描述这个方法的作用)
     * 
     */
    public List<TextBookInfo> getAll() {
        return dao.getAll(new TextBookInfo());
    }

    /**
     * 
     * getUnitInfoList(获取对应教材下的全部单元列表)
     * 
     */
    public List<UnitInfo> getUnitInfoList(String textId) {
        TextBookInfo text = getById(textId);
        if (text == null) {
            return null;
        }
        List<UnitInfo> unitList = new ArrayList<UnitInfo>();
        int unitNum = text.getUnitNum();
        for (int i = 1; i <= unitNum; i++) {
            unitList.add(new UnitInfo(i));
        }

        return unitList;

    }

}
