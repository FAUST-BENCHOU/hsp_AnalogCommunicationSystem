package com.hsp.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/4 19:50
 */
public class ManageMessage {
    private static HashMap<String, ArrayList<String>> tempMessage;
    static {
        tempMessage = new HashMap<>();
    }

    public static void addMessage(String sender,String getter,String content){
        ArrayList<String> arrayList = tempMessage.get(getter);
        if (arrayList!=null){
            arrayList.add(sender);
            arrayList.add(content);
        }else {
            ArrayList<String> array = new ArrayList<>();
            array.add(sender);
            array.add(content);
            tempMessage.put(getter,array);
        }
    }

    public static String getMessage(String userId) {
        ArrayList<String> arrayList = tempMessage.get(userId);
        StringBuffer sb = null;
        if (arrayList!=null&&arrayList.size()>0){
            sb=new StringBuffer();
            for (int i = 0; i < arrayList.size(); i += 2) {
                String sender = arrayList.get(i);
                String content = arrayList.get(i + 1);

                sb.append(sender + " 对你说 ");
                sb.append(": ");
                sb.append(content);
                sb.append("\n");
            }
            return sb.toString();
        }else {
            return "";
        }
    }

    public static void removeMessage(String userId){
        ArrayList<String> strings = tempMessage.get(userId);
        strings = null;
        tempMessage.remove(userId);
    }

    public static HashMap<String, ArrayList<String>> getTempMessage(){
        return tempMessage;
    }

}
