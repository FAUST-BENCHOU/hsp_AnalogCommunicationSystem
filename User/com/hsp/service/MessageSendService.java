package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/3 19:38
 */
public class MessageSendService {
    public void sendMessage(String content,String sender) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss E");

        Message message = new Message();
        message.setDate(sdf.format(new Date()).toString());//发送当前时间
        message.setContent(content);
        message.setMessageType(MessageType.MESSAGE_SEND_TO_ALL);
        message.setGetter(" all ");
        message.setSender(sender);
        ObjectOutputStream objectOutputStream=null;
        objectOutputStream=new ObjectOutputStream(ManageThread
                .getUserConnectServerThread(sender).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
    }

    public void sendMessage(String content,String sender,String getter) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss E");

        Message message = new Message();
        message.setContent(content);
        message.setDate(sdf.format(new Date()).toString());//发送当前时间
        message.setMessageType(MessageType.MESSAGE_SEND_TO_ONE);
        message.setGetter(getter);
        message.setSender(sender);
        ObjectOutputStream objectOutputStream=null;
        objectOutputStream=new ObjectOutputStream(ManageThread
                .getUserConnectServerThread(sender).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
    }
    public void removeMessage(String userId){
        try {
            Message message = new Message();
            message.setSender(userId);
            message.setMessageType(MessageType.MESSAGE_REMOVE_MESSAGE);
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getUserConnectServerThread(userId).getSocket().getOutputStream());
            oos.writeObject(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
