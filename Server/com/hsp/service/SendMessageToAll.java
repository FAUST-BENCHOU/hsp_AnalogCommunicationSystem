package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;
import com.hsp.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/4 12:49
 */
public class SendMessageToAll implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("请输入服务器要向all发送的消息/输入exit退出服务器线程");
            String news = Utility.readString();

            if (news.equals("exit")){
                break;
            }
            Message message = new Message();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss E");
            message.setDate(sdf.format(new Date()).toString());//发送当前时间
            message.setMessageType(MessageType.MESSAGE_SERVER_TO_ALL);
            message.setContent(news);
            message.setSender("服务器");
            System.out.println("在 "+message.getDate()+" 服务器推送消息 "+news);
            HashMap<String,ServerConnectUserThread> hashMap=ManageUserThread.getHm();
            Iterator iterator = hashMap.keySet().iterator();
            while (iterator.hasNext()) {
                String onlineUser =  iterator.next().toString();
                ServerConnectUserThread serverConnectUserThread=hashMap.get(onlineUser);
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(serverConnectUserThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
