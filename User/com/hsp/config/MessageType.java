package com.hsp.config;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/2 21:20
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCESS = "1";
    String MESSAGE_LOGIN_FAIL="2";
    String MESSAGE_REGISTER_SUCCESS = "3";
    String MESSAGE_REGISTER_FAIL="4";
    String MESSAGE_GET_ALL_USERS="5";
    String MESSAGE_SEND_TO_ONE="6";
    String MESSAGE_SEND_TO_ALL="7";
    String MESSAGE_SEND_FILE="8";
    String MESSAGE_USER_EXIT="9";
    String MESSAGE_SERVER_TO_ALL="10";
    String MESSAGE_REMOVE_MESSAGE="11";
}
