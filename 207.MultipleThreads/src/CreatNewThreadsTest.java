import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 设置两个线程t3和t4，使得两个线程每隔1秒交替输出整数1-20.
 */

public class CreatNewThreadsTest {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.MAX_PRIORITY);
        System.out.println(Thread.MIN_PRIORITY);
        System.out.println(Thread.NORM_PRIORITY);

        Thread t = Thread.currentThread();
        System.out.println(t.getName());
        System.out.println("======================================================");

        Thread t1 = new NewThreadMethod1st();
        t1.setName("t1");
        t1.setPriority(1);

        Thread t2 = new Thread(new NewThreadMethod2nd());
        t2.setName("t2");
        t2.setPriority(10);//打印将会有比较明显的区分

        //这段代码瞬间结束，目的是告诉JVM再分配一个新的栈给t1、t2线程
        //run不需要程序员手动调用，系统线程启动之后自动调用run方法
        t1.start();
        t2.start();

        for (int k = 0; k < 20; k++) {
            System.out.println("print from " + Thread.currentThread().getName() + ", k is " + k);
        }


        Thread.sleep(1000);
        System.out.println("========================================================");
        System.out.println("===================lock，wait，notify===================");
        System.out.println("========================================================");
/*

        ThreadPrint tp = new ThreadPrint();
        Process p = new Process(tp);
        Thread t3 = new Thread(p);
        t3.setName("t3");
        Thread t4 = new Thread(p);
        t4.setName("t4");

        t3.start();
        t4.start();
*/




        final Lock lock = new Lock();

        new Thread(new WaitRunnable(lock), "WaitThread-1").start();
        new Thread(new WaitRunnable(lock), "WaitThread-2").start();
        Thread.sleep(5000);
        new Thread(new NotifyRunnable(lock), "NotifyThread").start();
        Thread.sleep(Integer.MAX_VALUE);//让main线程一直睡觉

    }







    //内部类WaitRunnable
    private static class WaitRunnable implements Runnable {

        private final Lock lock;

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(String.format("[%s]-线程[%s]获取锁成功,准备执行wait方法", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(String.format("[%s]-线程[%s]执行wait方法", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                        /*执行wait后会释放监控锁，释放对Lock的锁定*/
                        lock.wait();
                        /*线程一旦被Notify恢复，会从此处继续执行*/
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    System.out.println(String.format("[%s]-线程[%s]从wait中唤醒,执行任务后sleep(500)", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    //break;
                }
            }
        }

        //构造方法
        WaitRunnable(Lock lock) {
            this.lock = lock;
        }
    }

    //内部类NotifyRunnable
    private static class NotifyRunnable implements Runnable {

        private final Lock lock;

        @Override
        public void run() {
            synchronized (lock) {
                while (true){
                    System.out.println(String.format("[%s]-线程[%s]获取锁成功,执行notifyAll方法，唤起所有wait的线程", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                    lock.notifyAll();

                    try {
                        //Thread.sleep(3000);
                        System.out.println(String.format("[%s]-线程[%s]先使用wait(5000)释放锁，休眠5000ms再获取", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                        /*一旦进入wait状态，synchronized的代码权就会交给被唤醒的其他两个进程，并由他们竞争获取*/
                        lock.wait(5000);
                        System.out.println(String.format("[%s]-线程[%s]从wait中返回了\r\n", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    //System.out.println(String.format("[%s]-线程[%s]准备exit", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                }
            }
        }

        //构造方法
        NotifyRunnable(Lock lock) {
            this.lock = lock;
        }
    }




}






/**
 * following is the output, one time:
 *
 * print from NewThreadMethod2nd, j is 0
 * print from NewThreadMethod1st, i is 0
 * print from main, k is 0
 * print from NewThreadMethod1st, i is 1
 * print from NewThreadMethod2nd, j is 1
 * print from NewThreadMethod1st, i is 2
 * print from main, k is 1
 * print from NewThreadMethod1st, i is 3
 * print from NewThreadMethod2nd, j is 2
 * print from NewThreadMethod1st, i is 4
 * print from main, k is 2
 * print from NewThreadMethod1st, i is 5
 * print from NewThreadMethod2nd, j is 3
 * print from NewThreadMethod1st, i is 6
 * print from main, k is 3
 * print from NewThreadMethod1st, i is 7
 * print from NewThreadMethod2nd, j is 4
 * print from NewThreadMethod1st, i is 8
 * print from main, k is 4
 * print from NewThreadMethod1st, i is 9
 * print from NewThreadMethod2nd, j is 5
 * print from NewThreadMethod1st, i is 10
 * print from main, k is 5
 * print from NewThreadMethod1st, i is 11
 * print from NewThreadMethod2nd, j is 6
 * print from NewThreadMethod1st, i is 12
 * print from main, k is 6
 * print from NewThreadMethod1st, i is 13
 * print from NewThreadMethod1st, i is 14
 * print from NewThreadMethod1st, i is 15
 * print from NewThreadMethod1st, i is 16
 * print from NewThreadMethod1st, i is 17
 * print from NewThreadMethod1st, i is 18
 * print from NewThreadMethod1st, i is 19
 * print from NewThreadMethod2nd, j is 7
 * print from NewThreadMethod2nd, j is 8
 * print from NewThreadMethod2nd, j is 9
 * print from NewThreadMethod2nd, j is 10
 * print from NewThreadMethod2nd, j is 11
 * print from NewThreadMethod2nd, j is 12
 * print from NewThreadMethod2nd, j is 13
 * print from NewThreadMethod2nd, j is 14
 * print from NewThreadMethod2nd, j is 15
 * print from NewThreadMethod2nd, j is 16
 * print from NewThreadMethod2nd, j is 17
 * print from NewThreadMethod2nd, j is 18
 * print from NewThreadMethod2nd, j is 19
 * print from main, k is 7
 * print from main, k is 8
 * print from main, k is 9
 * print from main, k is 10
 * print from main, k is 11
 * print from main, k is 12
 * print from main, k is 13
 * print from main, k is 14
 * print from main, k is 15
 * print from main, k is 16
 * print from main, k is 17
 * print from main, k is 18
 * print from main, k is 19
 */
