package com.hsp.service;

import java.util.HashMap;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/3 14:04
 */
public class ManageThread {
    private static HashMap<String,UserConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addUserConnectServerThread(String userName,UserConnectServerThread userThread){
        hm.put(userName,userThread);
        System.out.println(userName+"线程启动");
    }

    //通过userName 获取对应线程
    public static UserConnectServerThread getUserConnectServerThread(String userName){
        return hm.get(userName);
    }
    public static void removeThread(String uid) {
        hm.remove(uid);
    }


}
