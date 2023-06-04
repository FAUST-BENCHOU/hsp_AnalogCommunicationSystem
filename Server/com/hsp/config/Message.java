package com.hsp.config;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/2 21:20
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String MessageType;
    private String sender;
    private String getter;
    private String date;
    private String content;

    private String src;
    private String dest;
    private byte[] fileBytes;
    private int fileLen=0;

    @Override
    public String toString() {
        return "Message{" +
                "MessageType='" + MessageType + '\'' +
                ", sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                ", fileBytes=" + Arrays.toString(fileBytes) +
                ", fileLen=" + fileLen +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
