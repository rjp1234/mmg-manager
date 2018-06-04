/**    
 * 文件名：ZookeeperUtil.java    
 *    
 * 版本信息：    
 * 日期：2018年4月22日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.common.utils.zookeeper.util;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.config.Global;

/**
 * 
 * 项目名称：JeeSite 类名称：ZookeeperUtil 类描述： 创建人：renjianping 创建时间：2018年4月22日 下午6:06:35
 * 修改人：Administrator 修改时间：2018年4月22日 下午6:06:35 修改备注：
 * 
 * @version
 * 
 */
public class ZookeeperUtil {
    private ZooKeeper zookeeper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ZooKeeper getInstance(Watcher watcher) {
        try {
            zookeeper = new ZooKeeper(Global.getConfig("zookeeper.hostport"),
                    Integer.parseInt(Global.getConfig("zookeeper.timeout")), watcher);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return zookeeper;
    }

    public ZooKeeper getZK() {
        return zookeeper;
    }

    /**
     * 创建znode结点
     * 
     * @param path
     *            结点路径
     * @param data
     *            结点数据
     * @return true 创建结点成功 false表示结点存在
     * @throws Exception
     */
    public boolean addZnodeData(String path, String data, CreateMode mode) {
        try {
            if (zookeeper.exists(path, true) == null) {
                zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, mode);
                return true;
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("znode" + path + "结点已存在");
        return false;
    }

    /**
     * 创建永久znode结点
     * 
     * @param path
     *            结点路径
     * @param data
     *            结点数据
     * @return true 创建结点成功 false表示结点存在
     * @throws Exception
     */
    public boolean addPZnode(String path, String data) {
        return addZnodeData(path, data, CreateMode.PERSISTENT);
    }

    /**
     * 创建临时znode结点
     * 
     * @param path
     *            结点路径
     * @param data
     *            结点数据
     * @return true 创建结点成功 false表示结点存在
     * @throws Exception
     */
    public boolean addZEnode(String path, String data) {
        return addZnodeData(path, data, CreateMode.EPHEMERAL);
    }

    /**
     * 修改znode
     * 
     * @param path
     *            结点路径
     * @param data
     *            结点数据
     * @return 修改结点成功 false表示结点不存在
     */
    public boolean updateZnode(String path, String data) {
        Stat stat = null;
        try {
            if ((stat = zookeeper.exists(path, true)) != null) {
                zookeeper.setData(path, data.getBytes(), stat.getVersion());
                return true;
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 
     * 删除结点
     * 
     * @param path
     *            结点
     * @return true 删除键结点成功 false表示结点不存在
     */
    public boolean deleteZnode(String path) {
        Stat stat = null;
        try {
            if ((stat = zookeeper.exists(path, true)) != null) {
                List<String> subPaths = zookeeper.getChildren(path, false);
                if (subPaths.isEmpty()) {
                    zookeeper.delete(path, stat.getVersion());
                    return true;
                } else {
                    for (String subPath : subPaths) {
                        deleteZnode(path + "/" + subPath);
                    }
                }
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 取到结点数据
     * 
     * @param path
     *            结点路径
     * @return null表示结点不存在 否则返回结点数据
     */
    public String getZnodeData(String path) {
        String data = null;
        Stat stat = null;
        try {
            if ((stat = zookeeper.exists(path, true)) != null) {
                data = new String(zookeeper.getData(path, true, stat));
            } else {
                logger.info("znode:" + path + ",不存在");
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return data;
    }
}