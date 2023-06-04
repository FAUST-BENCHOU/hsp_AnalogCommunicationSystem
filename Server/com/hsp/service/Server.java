package com.hsp.service;

import com.hsp.config.Message;
import com.hsp.config.MessageType;
import com.hsp.config.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/2 21:41
 */
public class Server {
    private ServerSocket serverSocket = null;
    private Message message = new Message();
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    static {
        validUsers.put("123", new User("123", "123"));
        validUsers.put("1", new User("1", "1"));
        validUsers.put("2", new User("2", "2"));
    }

    public boolean checkValidUser(String userName, String password) {
        Iterator<Map.Entry<String, User>> iterator = validUsers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> entry = iterator.next();
            String key = entry.getKey();
            User user = entry.getValue();
            // 在这里处理每个用户
            if (userName.equals(user.getUserName()) && password.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean registerNewUser(String userName, String password) {
        Iterator<Map.Entry<String, User>> iterator = validUsers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> entry = iterator.next();
            String key = entry.getKey();
            User user = entry.getValue();
            // 在这里处理每个用户
            if (userName.equals(user.getUserName())) {
                return false;
            }
        }
        validUsers.put(userName, new User(userName, password));
        return true;
    }

    public Server() throws IOException, ClassNotFoundException {
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("服务器9999在监听");
          new Thread(new SendMessageToAll()).start();

            while (true) {
                System.out.println("准备accept");
                Socket socket = serverSocket.accept();
                System.out.println("accept成功");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                User user = (User) objectInputStream.readObject();
                if (user.getKind().equals("1") && checkValidUser(user.getUserName(), user.getPassword())) {
                    System.out.println(user.getUserName() + " 登录成功！欢迎 ");
                    String msg = ManageMessage.getMessage(user.getUserName());
                    if (!("".equals(msg))){
                        message.setContent(msg);
                    }
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);

                    //登录成功后需要给该用户创造一个单独的线程
                    ServerConnectUserThread serverConnectUserThread = new ServerConnectUserThread(user.getUserName(), socket);
                    serverConnectUserThread.start();
                    ManageUserThread.addServerConnectUserThread(user.getUserName(), serverConnectUserThread);


                } else if (user.getKind().equals("2") && registerNewUser(user.getUserName(), user.getPassword())) {
                    System.out.println(user.getUserName() + " 注册成功 ");
                    message.setMessageType(MessageType.MESSAGE_REGISTER_SUCCESS);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                } else if (user.getKind().equals("2") && !registerNewUser(user.getUserName(), user.getPassword())) {
                    System.out.println(user.getUserName() + " 注册失败 ");
                    message.setMessageType(MessageType.MESSAGE_REGISTER_FAIL);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    validUsers.remove(user.getUserName());
                } else {
                    System.out.println(user.getUserName() + " 尝试登录失败");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    socket.close();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            //如果服务器退出了while,说明服务器端不在监听，因此关闭ServerSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
