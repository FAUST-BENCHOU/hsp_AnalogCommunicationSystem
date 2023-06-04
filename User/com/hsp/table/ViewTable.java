package com.hsp.table;

import com.hsp.service.FileSendService;
import com.hsp.service.MessageSendService;
import com.hsp.service.UserService;
import com.hsp.utils.Utility;
import com.sun.corba.se.impl.logging.UtilSystemException;

import java.io.IOException;

/**
 * @author  FAUST
 * @version 1.0
 */
public class ViewTable {
    String key=" ";
    boolean loop=true;
boolean loop2=true;
    public void mainMenu() throws IOException, ClassNotFoundException, InterruptedException {
        while (loop){
            System.out.println("===============欢迎来到网络通讯系统===============");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("9.退出系统");
            key= Utility.readString();
            switch (key){
                case "1":
                    System.out.println("请输入用户名：");
                    String userName=Utility.readString();
                    System.out.println("输入密码");
                    String password=Utility.readString();

                    if (new UserService().checkUser(userName,password)){
                        new MessageSendService().removeMessage(userName);
                        while (loop2) {
                            //进入到二级菜单
                            System.out.println("============网络通信系统二级菜单（用户" + userName + "）============");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString();
                            switch (key){
                                case "1":
                                    new UserService().getAllUsers(userName);
                                    Thread.sleep(500);
                                    break;
                                case "2":
                                    System.out.println("请输入你想对all说的内容");
                                    String content_all= Utility.readString();
                                    new MessageSendService().sendMessage(content_all,userName);
                                    Thread.sleep(500);
                                    break;
                                case "3":
                                    System.out.println("请输入你想要私聊的对象");
                                    String getter= Utility.readString();
                                    System.out.println("请输入你说的内容");
                                    String content_one= Utility.readString();
                                    new MessageSendService().sendMessage(content_one,userName,getter);
                                    Thread.sleep(500);
                                    break;
                                case "4":
                                    System.out.println("请输入你想发送文件的对象：");
                                    String fileGetter=Utility.readString();
                                    System.out.println("请输入你想发送的文件的路径(形式如 d:\\\\xx.jpg)：");
                                    String src=Utility.readString();
                                    System.out.println("请输入你想发送到对方的路径(形式如 d:\\\\xx.jpg)：");
                                    String dest=Utility.readString();
                                    new FileSendService().sendFileToOne(userName,fileGetter,src,dest);
                                    Thread.sleep(500);
                                    break;
                                case "9":
                                    new UserService().exit(userName);
                                    loop2=false;
                                    Thread.sleep(500);
                                    break;
                                default:
                                    System.out.println("输入有误");
                                    break;
                            }
                        }
                    }
                    loop2=true;
                    break;
                case "2":
                    System.out.println("请输入注册用户名：");
                    String newUserName=Utility.readString();
                    System.out.println("输入注册密码");
                    String newPassword=Utility.readString();
                    System.out.println("再次确认输入注册密码");
                    String confirmNewPassword=Utility.readString();
                    new UserService().registerUser(newUserName,newPassword,confirmNewPassword);
                    break;
                case "9":
                    loop=false;
                    System.out.println("退出用户端");
                    System.exit(0);
                    break;
                default:
                    System.out.println("输入有误");
                    break;
            }

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        new ViewTable().mainMenu();
    }

}
