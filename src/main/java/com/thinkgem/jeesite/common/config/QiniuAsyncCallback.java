package com.thinkgem.jeesite.common.config;

import com.qiniu.http.AsyncCallback;
import com.qiniu.http.Response;

/***
 * 七牛回调函数
 * 
 * @author yanpeng
 * 
 */
public class QiniuAsyncCallback implements AsyncCallback {

    @Override
    public void complete(Response r) {
        // TODO Auto-generated method stub
        System.out.println(r);

    }

}
