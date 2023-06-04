package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;
import com.hsp.config.User;
import com.hsp.utils.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/3 13:42
 */
public class UserConnectServerThread extends Thread {

    //该线程要持有socket
    private Socket socket;
private User user;
    public Socket getSocket() {
        return socket;
    }

    //构造器接受Socket
    public UserConnectServerThread(Socket socket,User user) {
        this.socket = socket;
        this.user=user;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                if (message.getMessageType().equals(MessageType.MESSAGE_GET_ALL_USERS)){
                    System.out.println(message.getContent());
                }else if (message.getMessageType().equals(MessageType.MESSAGE_SEND_TO_ALL)){
                    System.out.println(message.getSender() +" 在 "+message.getDate()+" 对all说 "+message.getContent());
                }else if (message.getMessageType().equals(MessageType.MESSAGE_SEND_TO_ONE)){
                    System.out.println(message.getSender() +" 在 "+message.getDate()+" 对你说 "+message.getContent());
                }else if (message.getMessageType().equals(MessageType.MESSAGE_SEND_FILE)){
                    System.out.println("\n"+message.getSender() +" 在 "+message.getDate()+ " 给 " + message.getGetter()
                            + " 发送了 "+message.getSrc()+" 文件,到我的电脑的 "+message.getDest());
                    File destFile = new File(message.getDest());
                    if (destFile.exists()) {
                        FileOutputStream fos = new FileOutputStream(destFile);
                        fos.write(message.getFileBytes());
                        fos.close();
                        System.out.println("文件保存成功！");
                    } else {
                        System.out.println("路径错误！");
                    }


                }else if (message.getMessageType().equals(MessageType.MESSAGE_USER_EXIT)){
                    System.out.println(user.getUserName()+"退出");
                    socket.close();
                    break;
                }else if (message.getMessageType().equals(MessageType.MESSAGE_SERVER_TO_ALL)){
                    System.out.println(message.getSender()+" 在 "+message.getDate()+" 对所有人说 "+message.getContent());
                }



            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
