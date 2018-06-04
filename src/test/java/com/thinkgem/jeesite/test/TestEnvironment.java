package com.thinkgem.jeesite.test;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import com.thinkgem.jeesite.base.BaseTest;
import com.thinkgem.jeesite.common.utils.JedisUtils;

/**
 * 
 * 项目名称：JeeSite 类名称：TestEnvironment 类描述： 基础运行环境测试 创建人：Administrator
 * 创建时间：2018年4月21日 下午7:23:08 修改人：Administrator 修改时间：2018年4月21日 下午7:23:08 修改备注：
 * 
 * @version
 * 
 */

public class TestEnvironment extends BaseTest {

    @org.junit.Test
    public void mysqlTest() throws Exception {
        String url = "jdbc:mysql://192.168.40.129:3306/mengmengyuan?useUnicode=true&characterEncoding=utf-8"; // 连接数据库（kucun是数据库名）

        String name = "root";// 连接mysql的用户名

        String pwd = "123";// 连接mysql的密码
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection conn = DriverManager.getConnection(url, name, pwd);
        System.out.println(conn);
    }

    @Test
    public void JedisTest() {
        JedisUtils.set("key", "value", 0);
        System.out.println(JedisUtils.get("key"));
    }

    @Test
    public void zookeeperTest() throws IOException, KeeperException, InterruptedException {
        String hostPort = "192.168.40.129:2181";
        String zpath = "/";
        List<String> zooChildren = new ArrayList<String>();
        ZooKeeper zk = new ZooKeeper(hostPort, 2000, null);
        if (zk != null) {
            try {
                zooChildren = zk.getChildren(zpath, false);
                for (String child : zooChildren) {
                    // print the children
                    System.out.println(child);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("renjianping".getBytes());
        byte[] encode = Base64.getEncoder().encode("renjianping".getBytes());
        System.out.println(encode);

    }

}
