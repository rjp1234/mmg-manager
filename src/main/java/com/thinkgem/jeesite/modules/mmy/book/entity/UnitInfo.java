/**    
 * 文件名：UnitInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年6月11日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.modules.mmy.book.entity;

/**
 * 
 * 项目名称：mmg-manager 类名称：UnitInfo 类描述： 创建人：Administrator 创建时间：2018年6月11日
 * 下午3:34:44 修改人：Administrator 修改时间：2018年6月11日 下午3:34:44 修改备注：
 * 
 * @version
 * 
 */
public class UnitInfo {
    private String name;

    private int unit;

    public UnitInfo(int unit) {
        if (unit == 0) {
            throw new RuntimeException("no such unit");
        }
        this.unit = unit;
        name = "第" + unit + "单元";

    }

    public String getName() {
        return name;
    }

    public int getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "UnitInfo [name=" + name + ", unit=" + unit + "]";
    }

}