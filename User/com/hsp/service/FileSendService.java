package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/4 9:23
 */
public class FileSendService {
    public void sendFileToOne(String FileSender,String FileGetter,String src,String dest) throws IOException {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_SEND_FILE);
        message.setSender(FileSender);
        message.setGetter(FileGetter);
        message.setSrc(src);
        message.setDest(dest);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss E");
        message.setDate(sdf.format(new Date()).toString());//发送当前时间
        int fileLen = (int) new File(src).length();
        byte[] fileBytes = new byte[fileLen];
        FileInputStream fis = null;
        fis= new FileInputStream(src);//将内容读到数组中
        fis.read(fileBytes);
        message.setFileBytes(fileBytes);
        message.setFileLen(fileLen);

        if (fis!=null){
            fis.close();
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                ManageThread.getUserConnectServerThread(message.getSender())
                .getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
    }

}
