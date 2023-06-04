package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;
import com.hsp.config.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/2 21:15
 */
public class UserService {

    private User user=new User();
    private Socket socket;

    //用于检查用户是否存在
    public boolean checkUser(String username,String password) throws IOException, ClassNotFoundException {
        user.setKind("1");
        user.setUserName(username);
        user.setPassword(password);
        socket=new Socket(InetAddress.getByName("127.0.0.1"),9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(user);

        //给服务端发过去后需要接受服务器的回复

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = (Message)objectInputStream.readObject();

        if (MessageType.MESSAGE_LOGIN_SUCCESS.equals(message.getMessageType())){
            System.out.println(" 登录成功 ");
            //启动线程
            System.out.println(message.getContent());
            UserConnectServerThread userConnectServerThread = new UserConnectServerThread(socket,user);
            userConnectServerThread.start();
            //放到集合中便于管理
            ManageThread.addUserConnectServerThread(username,userConnectServerThread);
            System.out.println("线程启动");

            return true;
        }else {
            System.out.println(" 登录失败 ");
            socket.close();
            return false;
        }
    }

    //创造新用户
    public boolean registerUser(String newUserName,String newPassword,String confirmNewPassword) throws IOException, ClassNotFoundException {
        user.setKind("2");
        if (!newPassword.equals(confirmNewPassword)){
            System.out.println(" 两次密码不一样，请重试 ");
            return false;
        }
        user.setUserName(newUserName);
        user.setPassword(newPassword);
        socket=new Socket(InetAddress.getLocalHost(),9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(user);

        //给服务端发过去后需要接受服务器的回复

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = (Message)objectInputStream.readObject();
        if (MessageType.MESSAGE_REGISTER_SUCCESS.equals(message.getMessageType())){
            System.out.println(" 用户 "+newUserName+" 注册成功 ,你的密码是："+newPassword);
            return true;
        }else {
            System.out.println(" 用户名存在，注册失败 ");
            return false;
        }
    }

    //显示所有在线的用户

    public void getAllUsers(String userName) throws IOException {
        Message message = new Message();
        message.setSender(userName);
        message.setMessageType(MessageType.MESSAGE_GET_ALL_USERS);

        //发出获取信息请求
        UserConnectServerThread userConnectServerThread = ManageThread.getUserConnectServerThread(userName);
        if (userConnectServerThread!=null) {
            Socket socket = userConnectServerThread.getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
        }
    }

    public void exit(String userName) throws IOException {
        Message message = new Message();
        message.setSender(userName);
        message.setMessageType(MessageType.MESSAGE_USER_EXIT);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageThread.getUserConnectServerThread(userName)
                .getSocket().getOutputStream());
        objectOutputStream.writeObject(message);

    }


}
