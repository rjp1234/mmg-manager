package com.thinkgem.jeesite.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 七牛配置
 * 
 * @author yanpeng
 * 
 */
public class QiniuConfig {

    private static final Logger logger = LoggerFactory.getLogger(QiniuConfig.class);

    private String access_key = "";

    private String secret_key = "";

    private String bucket = "";

    private static volatile QiniuConfig qiniuConfig;

    public static QiniuConfig getInstance() {
        if (qiniuConfig == null) {
            synchronized (QiniuConfig.class) {
                if (qiniuConfig == null) {
                    qiniuConfig = new QiniuConfig();
                }
            }
        }
        return qiniuConfig;
    }

    private QiniuConfig() {
        try {
            this.access_key = Global.getConfig("access_key");
            this.secret_key = Global.getConfig("secret_key");
            this.bucket = Global.getConfig("bucket");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

}
