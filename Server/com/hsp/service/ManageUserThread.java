package com.hsp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/3 13:18
 * 用于管理和客户端通信的线程
 */
public class ManageUserThread {
    private static HashMap<String, ServerConnectUserThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnectUserThread> getHm() {
        return hm;
    }

    //添加线程对象到集合
    public static void addServerConnectUserThread(String userId, ServerConnectUserThread serverConnectUserThread) {
        hm.put(userId, serverConnectUserThread);
    }

    //根据userid 返回线程
    public static ServerConnectUserThread getServerConnectUserThread(String userId) {
        return hm.get(userId);
    }

    //获取在线的用户信息
    public static String getUsersOnline() {
        StringBuffer sb = new StringBuffer();
        if(hm.keySet().size()>0) {
            for (String key : hm.keySet()) {
                sb.append(key);
                sb.append(" ");
            }
            return sb.toString();
        }else {
            return " ";
        }
    }

    //从集合中移除线程
    public static void removeThread(String uid) {
        hm.remove(uid);
    }

}
