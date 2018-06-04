/**    
 * 文件名：DataWatch.java    
 *    
 * 版本信息：    
 * 日期：2018年4月22日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.thinkgem.jeesite.common.utils.zookeeper.watch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.config.Global;

/**
 * 
 * 项目名称：JeeSite 类名称：DataWatch 类描述： 创建人：renjianping 创建时间：2018年4月22日 下午6:05:25
 * 修改人：Administrator 修改时间：2018年4月22日 下午6:05:25 修改备注：
 * 
 * @version
 * 
 */
public class DataWatcher implements Watcher, Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String hostPort = Global.getConfig("zookeeper.hostport");

    byte zoo_data[] = null;

    ZooKeeper zk;

    private String zooDataPath;

    public DataWatcher(ZooKeeper zk, String zooDataPath) {
        if (zk != null) {
            try {
                // Create the znode if it doesn't exist, with the following
                // code:nnn
                if (zk.exists(zooDataPath, this) == null) {
                    zk.create(zooDataPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printData() throws InterruptedException, KeeperException {
        zoo_data = zk.getData(zooDataPath, this, null);
        String zString = new String(zoo_data);
        // The following code prints the current content of the znode to the
        // console:
        logger.info("\nCurrent Data @ ZK Path %s: %s", zooDataPath, zString);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.printf("\nEvent Received: %s", event.toString());
        // We will process only events of type NodeDataChanged
        if (event.getType() == Event.EventType.NodeDataChanged) {
            try {
                printData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, KeeperException {
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}