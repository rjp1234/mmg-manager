package com.thinkgem.jeesite.common.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.thinkgem.jeesite.common.ThreadPool;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.config.QiniuConfig;

/**
 * 后台文件上传
 * 
 * @author renjianping
 */
public class FileLoadUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileLoadUtils.class);

    /**
     * fileUpload 文件上传至七牛
     * 
     * @author renjianping
     */
    public static void fileUpload(String localFilePath, String key) {

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        // 获取七牛秘钥配置与空间配置
        QiniuConfig config = QiniuConfig.getInstance();
        Auth auth = Auth.create(config.getAccess_key(), config.getSecret_key());
        // 获取上传凭证
        String upToken = auth.uploadToken(config.getBucket());
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            if (response.statusCode != 200) {
                logger.error("fileUpload failure,statusCode is" + response.statusCode);
            }
        } catch (QiniuException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * streamUpload 文件流上传至七牛
     * 
     * @author renjianping
     */
    public static void streamUpload(InputStream inputStream, String key) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        // 获取七牛秘钥配置与空间配置
        QiniuConfig config = QiniuConfig.getInstance();
        Auth auth = Auth.create(config.getAccess_key(), config.getSecret_key());
        // 获取上传凭证
        String upToken = auth.uploadToken(config.getBucket());
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            if (response.statusCode != 200) {
                logger.error("fileUpload failure,statusCode is" + response.statusCode);
            }
        } catch (QiniuException e) {
            logger.error(e.response.toString() + e.getMessage(), e);
        }
    }

    /**
     * SOURCE_TYPE_IMAGE_STU:TODO（用于存放学生上传的图片）
     * 
     * @since Ver 1.1
     */
    public static final String SOURCE_TYPE_IMAGE_STU = "student/image";

    /**
     * SOURCE_TYPE_IMAGE_TEA:TODO（用于存放教师上传的图片）
     * 
     * @since Ver 1.1
     */
    public static final String SOURCE_TYPE_IMAGE_TEA = "teacher/image";

    /**
     * SOURCE_TYPE_STUDIO_STU:TODO（用于上传学生的录音）
     * 
     * @since Ver 1.1
     */
    public static final String SOURCE_TYPE_STUDIO_STU = "student/studio";

    /**
     * SOURCE_TYPE_STUDIO_TEA:TODO（用于上传教师的录音）
     * 
     * @since Ver 1.1
     */
    public static final String SOURCE_TYPE_STUDIO_TEA = "teacher/studio";

    /**
     * 
     * QIniuupload(上传七牛)
     * 
     * @param sourceType:资源类型，决定资源存放位置
     * @param sourcePath：资源本地路径
     * @param sourceName：资源名称（建议唯一命名）
     * 
     */
    public static String QIniuupload(String sourceType, String sourcePath, String sourceName)
            throws UnsupportedEncodingException {
        sourcePath = java.net.URLDecoder.decode(sourcePath, "UTF-8");
        String source = Global.getConfig("domain");
        if (!sourcePath.startsWith("http")) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            // 封装资源名称
            final String name = sourceType + "/" + year + "/" + month + "/" + day + "/" + sourceName;
            source = "http://" + source + "/" + name;
            // 将图片上传至七牛
            final String path = sourcePath;
            ThreadPool.getInstance().execute(() -> FileLoadUtils.fileUpload(path, name));
        }
        return source;

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(QIniuupload(SOURCE_TYPE_STUDIO_TEA,
                "D:\\userfiles\\userfiles\\1\\studio\\files\\2018\\06\\王瑞淇 - 指缝的微光.mp3", "测试"));

    }
}
