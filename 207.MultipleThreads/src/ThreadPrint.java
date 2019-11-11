import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThreadPrint implements Runnable{
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final Lock lock;

    @Override
    public void run() {
        synchronized (lock){
            do{
                //下面这段代码写的很冗余，后续优化
                if(("t3" == Thread.currentThread().getName())&&(lock.getNum()%2==1)){
                    System.out.println(String.format("[%s]-线程[%s]获取锁成功，打印数字[%s]", F.format(LocalDateTime.now()), Thread.currentThread().getName(),lock.getNum()));
                    try {
                        Thread.sleep(1000);//模拟需要执行40ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(("t4" == Thread.currentThread().getName())&&(lock.getNum()%2==0)){
                    System.out.println(String.format("[%s]-线程[%s]获取锁成功，打印数字[%s]", F.format(LocalDateTime.now()), Thread.currentThread().getName(),lock.getNum()));
                    try {
                        Thread.sleep(1000);//模拟需要执行60ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(String.format("[%s]出现异常，线程为[%s]", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                    lock.setNum(lock.getNum()+1);
                }

                /*线程工作完毕后，需要首先公共计数器自加1，唤醒另外一个线程，自身再进入wait状态*/
                System.out.println(String.format("[%s]-线程[%s]工作完毕，唤醒其他进程，自身进入wait状态", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                lock.setNum(lock.getNum()+1);
                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*当数量为21时，经过上面的自加1，说明执行完毕*/
                if(lock.getNum() == 21){
                    System.out.println(String.format("[%s]-线程[%s]执行完毕，唤醒其他线程，自身【exit】", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                    /*一个进程退出前，需要唤醒另外一个进程，否则会一直处于wait状态*/
                    lock.notify();
                    return;
                }

            }while (true);
        }

    }

    //constructor
    public ThreadPrint(Lock lock) {
        this.lock = lock;
    }
}
