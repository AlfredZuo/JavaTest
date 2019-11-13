import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * 练习一：阻塞队列，场景入口类，参考如下链接
 * @LINK https://www.throwable.club/2019/04/30/java-object-wait-notify/#%E9%98%BB%E5%A1%9E%E9%98%9F%E5%88%97%E5%AE%9E%E7%8E%B0
 * 这个例子实际就是简单的单生产者-多消费者的模型
 *
 * 练习二：初始化固定数量的活跃线程，阻塞直到有可用的线程用于提交任务
 * TODO: 后续再仔细分析
 *
 */

public class Main {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {


        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("=======================练习一：阻塞队列=======================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");

        //适当的调整如下数据，队列会有不同的处理方式
        final int consumerTime = 1000;      //消费需要的耗时，默认值1000，单位ms
        final int producerTime = 200;       //生产需要的耗时，默认值200，单位ms
        final int queueCapacity = 5;        //队列长度，默认5
        final int productQuantity = 10;     //生产数据的数量，默认10

        DefaultBlockingQueue<String> queue = new DefaultBlockingQueue<>(queueCapacity);
        /**
         * Java 8 的两个接口 Runnable 和 Callable 都添加了 @FunctionalInterface 注解。
         * 因此，我们可以直接使用 Lambda 表达式来实现 run() 和 call() 方法。
         * 这意味这我们可以使用一个 Lambda 表达式来创建一个 Runnable 实例。
         * 举例如下：@LINK https://www.twle.cn/t/372
         *
         * Runnable r = () -> System.out.println("Hello World!");
         * Thread th = new Thread(r);
         * th.start();
         *
         * 上面三行代码，等同于不使用 Lambda 表达式的下面的代码
         *
         * Runnable r = new Runnable() {
         *    @Override
         *    public void run() {
         *     System.out.println("Hello World!");
         *    }
         * };
         * Thread th = new Thread(r);
         * th.start();
         */
        Runnable r = () -> {
            while (true) {
                try {
                    String take = queue.take();
                    System.out.println(String.format("线程[%s]~~消费~~消息-[%s],花费[%s]ms",
                            Thread.currentThread().getName(),
                            take,
                            consumerTime));
                    Thread.sleep(consumerTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(r, "thread-1");
        Thread t2 = new Thread(r, "thread-2");
        t1.start();
        t2.start();

        //forEachOrdered表示按照顺序进行处理
        IntStream.range(0, productQuantity).forEachOrdered(i -> {
            try {
                queue.put(String.valueOf(i));
                System.out.println(String.format("线程[  %s  ]~~生产~~消息-[%s],花费[%s]ms",
                        Thread.currentThread().getName(),
                        i,
                        producerTime));
                Thread.sleep(producerTime);
            } catch (InterruptedException e) {
                //ignore
            }
        });

        System.out.println(String.format("线程[  %s  ]暂停生产，原因：无原料，进入睡眠", Thread.currentThread().getName()));
        Thread.sleep(1000*15);

        System.out.println("【练习一：阻塞队列】演示结束，遗留当前2消费线程");


        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("===================练习二：固定容量的线程池===================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");



        ThreadPool threadPool = new DefaultThreadPool(2);
        threadPool.execute(() -> {
            try {
                System.out.println(String.format("[%s]-任务一开始执行持续3秒...", LocalDateTime.now().format(F)));
                Thread.sleep(3000);
                System.out.println(String.format("[%s]-任务一执行结束...", LocalDateTime.now().format(F)));
            }catch (Exception e){
                //ignore
            }
        });
        threadPool.execute(() -> {
            try {
                System.out.println(String.format("[%s]-任务二开始执行持续4秒...", LocalDateTime.now().format(F)));
                Thread.sleep(4000);
                System.out.println(String.format("[%s]-任务二执行结束...", LocalDateTime.now().format(F)));
            }catch (Exception e){
                //ignore
            }
        });
        threadPool.execute(() -> {
            try {
                System.out.println(String.format("[%s]-任务三开始执行持续5秒...", LocalDateTime.now().format(F)));
                Thread.sleep(5000);
                System.out.println(String.format("[%s]-任务三执行结束...", LocalDateTime.now().format(F)));
            }catch (Exception e){
                //ignore
            }
        });
        Thread.sleep(Integer.MAX_VALUE);





    }
}