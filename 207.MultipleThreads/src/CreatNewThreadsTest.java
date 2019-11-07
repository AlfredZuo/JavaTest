import javax.swing.plaf.TableHeaderUI;

public class CreatNewThreadsTest {
    public static void main(String[] args) {

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

        for(int k = 0 ; k < 20; k++){
            System.out.println("print from "+ Thread.currentThread().getName() +", k is "+k);
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
