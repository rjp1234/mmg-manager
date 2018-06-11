package com.thinkgem.jeesite.common.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.thinkgem.jeesite.common.config.QiniuConfig;

public class QiniuUtils {

    /**
     * 判断文件在七牛云中是否存在
     */
    public static boolean isExist(String fileKey) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        QiniuConfig config = QiniuConfig.getInstance();
        String accessKey = config.getAccess_key();
        String secretKey = config.getSecret_key();
        String bucket = config.getBucket();
        String key = fileKey;// "image/fresh/2017/12/23/89a2ead170cf4b59966223f074ffff30xxxx.png";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.stat(bucket, key);
            return true;
        } catch (QiniuException ex) {
            return false;
        }
    }
}
