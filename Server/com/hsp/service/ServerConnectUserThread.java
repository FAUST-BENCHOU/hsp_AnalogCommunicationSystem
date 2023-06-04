package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/3 12:59
 * 得到来自客户端的消息
 */
public class ServerConnectUserThread extends Thread {

    private Socket socket;
    private String userName;

    public ServerConnectUserThread(String userName, Socket socket) {
        this.socket = socket;
        this.userName = userName;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(" 服务端和 "+userName+" 保持联系 ");
            try {
                //接收处理来自客户端的消息
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message=(Message) objectInputStream.readObject();
                if (message.getMessageType().equals(MessageType.MESSAGE_GET_ALL_USERS)){
                    message.setContent(ManageUserThread.getUsersOnline());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);

                }else if (message.getMessageType().equals(MessageType.MESSAGE_SEND_TO_ALL)){
                    String users=ManageUserThread.getUsersOnline();
                    String[] onlineUsers=users.split(" ");
                    if (onlineUsers.length>0){
                        for (String onlineUser :onlineUsers) {
                            if (!onlineUser.equals(userName)) {
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageUserThread
                                        .getServerConnectUserThread(onlineUser).getSocket()
                                        .getOutputStream());
                                System.out.println(message.toString());
                                objectOutputStream.writeObject(message);
                            }
                        }
                    }
                }else if (message.getMessageType().equals(MessageType.MESSAGE_SEND_TO_ONE)){

                    if (ManageUserThread.getServerConnectUserThread(message.getGetter())==null){
                        ManageMessage.addMessage(message.getSender(),message.getGetter(),message.getContent());
                    }else {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageUserThread
                                .getServerConnectUserThread(message.getGetter()).getSocket()
                                .getOutputStream());
                        System.out.println(message.toString());
                        objectOutputStream.writeObject(message);
                    }
                }else if (message.getMessageType().equals(MessageType.MESSAGE_SEND_FILE)){

                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageUserThread
                                .getServerConnectUserThread(message.getGetter()).getSocket()
                                .getOutputStream());
                        System.out.println(message.toString());
                        objectOutputStream.writeObject(message);

                }else if (message.getMessageType().equals(MessageType.MESSAGE_USER_EXIT)){

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageUserThread
                            .getServerConnectUserThread(message.getSender()).getSocket()
                            .getOutputStream());
                    System.out.println(message.toString());
                    objectOutputStream.writeObject(message);
                    System.out.println(message.getSender()+"下线");
                    ManageUserThread.removeThread(userName);
                    socket.close();
                    break;
                }else if (message.getMessageType().equals(MessageType.MESSAGE_REMOVE_MESSAGE)){
                    ManageMessage.removeMessage(message.getSender());
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }
}
