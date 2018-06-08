/**    
 * 文件名：CkfinderUtils.java    
 *    
 * 版本信息：    
 * 日期：2018年6月8日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.thinkgem.jeesite.common.config.Global;

/**
 * 
 * 项目名称：mmg-manager 类名称：CkfinderUtils 类描述： 创建人：Administrator 创建时间：2018年6月8日
 * 上午9:12:27 修改人：Administrator 修改时间：2018年6月8日 上午9:12:27 修改备注：
 * 
 * @version
 * 
 */
public class CkfinderUtils {

    /**
     * 
     * getFileFromCkpath(将前端传回的未经处理的ckfind路径，转换成file对象)
     * 
     * @throws UnsupportedEncodingException
     * 
     */
    public static File getFileFromCkpath(String ckPath) throws UnsupportedEncodingException {
        ckPath = java.net.URLDecoder.decode(ckPath, "UTF-8");
        String realPath = Global.getUserfilesBaseDir()
                + ckPath.substring(ckPath.indexOf(Global.USERFILES_BASE_URL) + 1);
        File file = new File(realPath);
        if (file.exists()) {
            return file;
        }
        return null;

    }
}
