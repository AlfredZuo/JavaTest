//package com.alfred.zuo.test;

import java.util.Scanner;

/**
 * 题目要求：编写一个程序模拟酒店管理系统：预定房间，退房.....
 *  * 标准间，用户输入s
 *  * 双人间，用户输入d
 *  * 豪华间，用户输入l
 *
 */
public class HotelBookingSystem
{
    public static void main( String[] args ){

        Hotel h = new Hotel();
        String actionInput;
        String typeInput;
        String roomNo;



        for(;;){
            System.out.println("================================================================================================");
            System.out.println("====================================欢迎来到酒店房间管理系统====================================");
            System.out.println("===========================================版本V1.0=============================================");
            System.out.println("================================================================================================");
            Scanner sca = new Scanner(System.in);       //scanner action
            System.out.println("==请输入需要执行的动作，in表示CheckIn， out表示CheckOut， list表示打印房间状态==");
            actionInput = sca.next();
            if ("in".equals(actionInput)){
                System.out.println("==您的输入是in，启动CheckIn流程==");
                Scanner scrt = new Scanner(System.in);  //scanner room type
                System.out.println("==请输房型，s表示标准间， d表示双人房， l表示豪华间==");
                typeInput = scrt.next();
                if ("s".equals(typeInput)){
                    roomNo = h.checkIn(Room.RoomType.STANDARD);
                }else if ("d".equals(typeInput)){
                    roomNo = h.checkIn(Room.RoomType.DOUBLE);
                }else if ("l".equals(typeInput)){
                    roomNo = h.checkIn(Room.RoomType.LUXURY);
                }else{
                    roomNo = "9999";
                    System.out.println("==您的输入有误，请重新输入==");
                }

                if ("9999".equals(roomNo)){
                    //TODO：稍后处理
                }

            }else if("out".equals(actionInput)){
                System.out.println("==您的输入是out，启动CheckOut流程==");
                Scanner scrn = new Scanner(System.in);
                System.out.println("==请输4位房间号==");
                roomNo = scrn.next();
                if (h.checkout(roomNo)){
                    System.out.println("==房间（"+ roomNo +"）CheckOut成功，感谢您的使用，欢迎下次再来==");
                }


            }else if("list".equals(actionInput)) {
                System.out.println("==您的输入是list，启动显示房间状态流程==");
                h.list();
            }else{
                System.out.println("==输入有误，请重新输入==");
            }
        }
    }
}
