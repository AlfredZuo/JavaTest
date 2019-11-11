import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 任务一：学习两两种线程的启动方法，以及sleep方法
 * 任务二：学习使用lock，wait，notifyAll方法，来源于互联网案例
 * 任务三：设置两个线程t3和t4，使得两个线程每隔1秒交替输出整数1-20.
 */

public class CreatNewThreadsTest {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.MAX_PRIORITY);
        System.out.println(Thread.MIN_PRIORITY);
        System.out.println(Thread.NORM_PRIORITY);

        Thread t = Thread.currentThread();
        System.out.println(t.getName());
        System.out.println("========================================================");
        System.out.println("=========================task1==========================");
        System.out.println("=============two ways for initial a thread==============");
        System.out.println("========================================================");
        System.out.println("========================================================");

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
        System.out.println("=========================task2==========================");
        System.out.println("===================lock，wait，notify===================");
        System.out.println("========================================================");
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
        /**
         * @LINk https://www.throwable.club/2019/04/30/java-object-wait-notify/#%E9%98%BB%E5%A1%9E%E7%AD%89%E5%BE%85-wait
         *
         * 关于wait(long timeoutMillis, int nanos)的说明
         * 1、当前线程阻塞等待直到被唤醒，唤醒的情况一般有三种：notify(All)被调用、线程被中断或者在指定了超时阻塞的情况下超过了指定的阻塞时间。
         * 2、当前线程必须获取此对象的监视器锁(monitor lock)，也就是调用阻塞等待方法之前一个线程必须成为此对象的监视器锁的拥有者。
         * 3、调用了wait()方法之后，当前线程会把自身放到当前对象的等待集合(wait-set)，然后释放所有在此对象上的同步声明(then to relinquish any nd all synchronization claims on this object)，谨记只有当前对象上的同步声明会被释放，当前线程在其他对象上的同步锁只有在调用其wait()方法之后才会释放。
         * 4、Warning：线程被唤醒之后(notify()或者中断)就会从等待集合(wait-set)中移除并且重新允许被线程调度器调度。通常情况下，这个被唤醒的线程会与其他线程竞争对象上的同步权(锁)，一旦线程重新控制了对象(regained control of the object)，它对对象的所有同步声明都恢复到以前的状态，即恢复到调用wait()方法时(笔者认为，其实准确来说，是调用wait()方法前)的状态。
         * 5、如果任意线程在它调用了wait()之前，或者调用过wait()方法之后处于阻塞等待状态，一旦线程调用了Thread#interrupt()，线程就会中断并且抛出InterruptedException异常，线程的中断状态会被清除。InterruptedException异常会延迟到在第4点提到”它对对象的所有同步声明都恢复到以前的状态”的时候抛出。
         * (*)一个线程必须成为此对象的监视器锁的拥有者才能正常调用wait()系列方法，也就是wait()系列方法必须在同步代码块(synchronized代码块)中调用，否则会抛出IllegalMonitorStateException异常，这一点是初学者或者不了解wait()的机制的开发者经常会犯的问题。
         *
         * 关于notify()的说明
         * 1、唤醒一个阻塞等待在此对象监视器上的线程，(如果存在多个阻塞线程)至于选择哪一个线程进行唤醒是任意的，取决于具体的现实，一个线程通过调用wait()方法才能阻塞在对象监视器上。
         * 2、被唤醒的线程并不会马上继续执行，直到当前线程(也就是当前调用了notify()方法的线程)释放对象上的锁。被唤醒的线程会与其他线程竞争在对象上进行同步(换言之只有获得对象的同步控制权才能继续执行)，在成为下一个锁定此对象的线程时，被唤醒的线程没有可靠的特权或劣势。
         * 3、此方法只有在一个线程获取了此对象监视器的所有权(the owner)的时候才能调用，具体就是：同步方法中、同步代码块中或者静态同步方法中。否则，会抛出IllegalMonitorStateException异常。
         *
         * 关于notifyAll()的说明
         * 唤醒所有阻塞等待在此对象监视器上的线程，一个线程通过调用wait()方法才能阻塞在对象监视器上。
         *
         * 经验总结：
         * 1、在线程进入synchronized方法或者代码块，相当于获取监视器锁成功，如果此时成功调用wait()系列方法，那么它会立即释放监视器锁，并且添加到等待集合(Wait Set)中进行阻塞等待。
         * 2、由于已经有线程释放了监视器锁，那么在另一个线程进入synchronized方法或者代码块之后，它可以调用notify(All)方法唤醒等待集合中正在阻塞的线程，但是这个唤醒操作并不是调用notify(All)方法后立即生效，而是在该线程退出synchronized方法或者代码块之后才生效。
         * 3、从wait()方法阻塞过程中被唤醒的线程会竞争监视器目标对象的控制权，一旦重新控制了对象，那么线程的同步状态就会恢复到步入synchronized方法或者代码块时候的状态(也就是成功获取到对象监视器锁时候的状态)，这个时候线程才能够继续执行。
         *
         */

        final Lock lock = new Lock();
        WaitRunnable wr1 = new WaitRunnable(lock);
        WaitRunnable wr2 = new WaitRunnable(lock);
        NotifyRunnable nr = new NotifyRunnable(lock);

        new Thread(wr1, "WaitThread-1").start();
        new Thread(wr2, "WaitThread-2").start();
        Thread.sleep(5000);
        new Thread(nr, "NotifyThread").start();

        Thread.sleep(1000*60);//让main线程一直睡觉，直到task2全部执行完毕




        System.out.println("========================================================");
        System.out.println("=========================task3==========================");
        System.out.println("===================two threads print====================");
        System.out.println("========================================================");
        System.out.println("========================================================");

        ThreadPrint tp3 = new ThreadPrint(lock);
        ThreadPrint tp4 = new ThreadPrint(lock);
        Thread t3 = new Thread(tp3);
        Thread t4 = new Thread(tp4);
        t3.setName("t3");
        t4.setName("t4");

        t3.start();
        Thread.sleep(10);
        t4.start();

        System.out.println(String.format("[%s]-线程[%s]时间到，【exit】", F.format(LocalDateTime.now()), Thread.currentThread().getName()));

    }







    //内部类WaitRunnable
    private static class WaitRunnable implements Runnable {

        private final Lock lock;
        private int i = 1;

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(String.format("[%s]-线程[%s]获取锁成功,准备执行wait方法", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                while (i<=10) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(String.format("[%s]-线程[%s]执行wait方法", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                        /*执行wait后会释放监控锁，释放对Lock的锁定*/
                        lock.wait();
                        /*线程一旦被Notify恢复，会从此处继续执行*/
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    System.out.println(String.format("[%s]-线程[%s]从wait中唤醒,第[%s]次执行任务后sleep(500)", F.format(LocalDateTime.now()), Thread.currentThread().getName(),i));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    //break;
                    i++;
                }
                System.out.println(String.format("[%s]-线程[%s]【exit】", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                return;
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
        private int i = 1;

        @Override
        public void run() {
            synchronized (lock) {
                while (i<=10){
                    System.out.println(String.format("[%s]-线程[%s]获取锁成功,执行notifyAll方法，唤起所有wait的线程", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                    lock.notifyAll();

                    try {
                        //Thread.sleep(3000);
                        System.out.println(String.format("[%s]-线程[%s]先使用wait(5000)释放锁，休眠5000ms再获取", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
                        /*一旦进入wait状态，synchronized的代码权就会交给被唤醒的其他两个进程，并由他们竞争获取*/
                        lock.wait(5000);
                        System.out.println(String.format("[%s]-线程[%s]第[%s]次从wait中返回了\r\n", F.format(LocalDateTime.now()), Thread.currentThread().getName(),i));
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    i++;
                }
                System.out.println(String.format("[%s]-线程[%s]【exit】\r\n", F.format(LocalDateTime.now()), Thread.currentThread().getName()));
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
