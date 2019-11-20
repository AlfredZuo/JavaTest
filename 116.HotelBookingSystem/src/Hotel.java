//package com.alfred.zuo.test;

//import com.sun.tools.javac.code.Attribute;

/**
 * 预制给出的酒店类
 * 规定酒店：5层，每层10个房间
 * 1,2层是标准间，用户输入s
 * 3,4层是双人间，用户输入d
 * 5层是豪华间，用户输入l
 * rooms[0][0]表示1楼1房
 * rooms[4][9]表示5楼10房
 **/
public class Hotel {
    private Room[][] rooms;


    //无参数的构造方法
    public Hotel() {
        this(5,10);
    }

    //有参数的构造方法
    private Hotel(int rows, int cols) {
        rooms = new Room[rows][cols];
        //按照题目要求初始化房间状态
        for(int i = 0; i < rows; i++){
            //rooms[i] = new Room[];
            for(int j = 0; j < cols ; j++){
                rooms[i][j] = new Room();
                if (i < 2){
                    rooms[i][j].type = Room.RoomType.STANDARD;
                }else if (i<4){
                    rooms[i][j].type = Room.RoomType.DOUBLE;
                }else if (i == 4){
                    rooms[i][j].type = Room.RoomType.LUXURY;
                }else{
                    rooms[i][j].type = Room.RoomType.UNKNOWN;
                }
                rooms[i][j].no = String.format("%02d",i) + String.format("%02d",j);
                rooms[i][j].isUse = false;
            }
        }
    }

    String checkIn(Room.RoomType type){
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[i].length ; j++){
                if(!rooms[i][j].isUse){
                    if(type == rooms[i][j].type){
                        rooms[i][j].isUse = true;
                        System.out.println("房间"+rooms[i][j].no+"空闲，分配给客人");
                        return rooms[i][j].no;
                    }
                }
            }
        }
        System.out.println("没有匹配的房间或者房间已满，请重新输入房间型号");
        return "9999";
    }

    boolean checkout(String no){
        int rows = Integer.parseInt(no) / 100;
        int cols = Integer.parseInt(no) % 100;
        if(rooms[rows][cols].isUse){
            rooms[rows][cols].isUse = false;
            System.out.println("房间"+rooms[rows][cols].no+"状态被设置为空闲");
            return true;
        }else{
            System.out.println("状态错误，房间"+rooms[rows][cols].no+"状态是空闲，无法CheckOut，请重新输入");
            return false;
        }
    }

    void list(){
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[i].length; j++){
                if(i==0&&j==0){
                    System.out.println("======================================标准间房间状态======================================");
                }else if(i==2&&j==0){
                    System.out.println("======================================双人间房间状态======================================");
                }else if(i==4&&j==0){
                    System.out.println("======================================豪华间房间状态======================================");
                }
                System.out.print("房间("+rooms[i][j].no+")"+(rooms[i][j].isUse?"!!使用； ":"~~空闲； "));
            }
            System.out.println();
        }
    }

}
