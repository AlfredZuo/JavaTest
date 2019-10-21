//package com.alfred.zuo.test;
/**
* 预制给出的房间类
**/
public class Room {
    String no;
    RoomType type;        //"标准间"、“双人间”、“豪华间”
    boolean isUse;      //true表示占用，false表示空闲

    public enum RoomType{
        STANDARD,
        DOUBLE,
        LUXURY,
        UNKNOWN,
    }

}
