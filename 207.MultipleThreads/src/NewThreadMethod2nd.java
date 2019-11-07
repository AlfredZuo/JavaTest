/**
 * 定义一个线程
 * 方式2
 */

public class NewThreadMethod2nd implements Runnable{
    @Override
    public void run() {
        for(int j = 0 ; j < 20; j++){
            System.out.println("print from "+ Thread.currentThread().getName() +", j is "+j);
        }
    }
}
