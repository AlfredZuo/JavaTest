/**
 * 定义一个线程
 * 方式1
 */

public class NewThreadMethod1st extends Thread{

    //重写run方法
    @Override
    public void run() {
        //super.run();
        for(int i = 0 ; i < 20; i++){
            System.out.println("print from "+ Thread.currentThread().getName() +", i is "+i);
            try{
                if(i==10){
                    Thread.sleep(1);
                }
            }catch (Exception err){
                err.printStackTrace();
            }
        }
    }
}
