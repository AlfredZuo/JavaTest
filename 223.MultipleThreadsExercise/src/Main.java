import java.util.stream.IntStream;

/**
 * 场景入口类：
 */

public class Main {

    public static void main(String[] args) throws Exception {

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
         * 举例如下：
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
        new Thread(r, "thread-1").start();
        new Thread(r, "thread-2").start();

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
        Thread.sleep(Integer.MAX_VALUE);
    }
}