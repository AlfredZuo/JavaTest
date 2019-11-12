/**
 * 其中put(T value)会阻塞直到队列中有可用的容量，而take()方法会阻塞直到有元素投放到队列中。
 * 队列采用先进先出方式，第一个进入的首先出队列
 * 队列采用泛型作为存储对象
 * 实现如下：
 */

public class DefaultBlockingQueue<T> implements BlockingQueue<T> {

    private Object[] elements;                          //一个元素队列
    private final Object notEmpty = new Object();       //一个用于检查队列是否为空的锁
    private final Object notFull = new Object();        //一个用于检查队列是否已满的锁
    private int count;                                  //一个用于检查队列中元素数量的变量
    private int takeIndex;                              //从队列中哪个位置继续取出数据
    private int putIndex;                               //从队列中哪个位置继续存放

    //构造函数根据输入capacity初始化元素队列长度
    public DefaultBlockingQueue(int capacity) {
        this.elements = new Object[capacity];
    }

    @Override
    public void put(T value) throws InterruptedException {
        synchronized (notFull) {
            while (count == elements.length) {
                System.out.println("线程[  "+Thread.currentThread().getName()+"  ]暂停生产。原因：Queue已满，执行wait");
                notFull.wait();
            }
        }
        final Object[] items = this.elements;
        items[putIndex] = value;
        /**
         * items的长度实际就是this.elements的长度，是在构造器中创建Queue对象时指定的，默认值是5
         * 一开始是按照数组顺序存储的，如果存到队列的最后一位，就要从队列的第0位开始继续存储，
         * 不用担心第0位还有数据，因为在一开始的while循环中，已经对count的数量进行了判断
         */
        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
        //因为上面已经存储了数据，这个时候就需要通知消费队列消费
        synchronized (notEmpty) {
            notEmpty.notify();
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (notEmpty) {
            while (0 == count) {
                System.out.println("线程["+Thread.currentThread().getName()+"]暂停消费。原因：Queue已清空，执行wait");
                notEmpty.wait();
            }
        }
        final Object[] items = this.elements;
        T value = (T) items[takeIndex];
        items[takeIndex] = null;    //取出（消费）后就将队列中指向的对象清0
        /**
         * items的长度实际就是this.elements的长度，是在构造器中创建Queue对象时指定的，默认值是5
         * 一开始是按照数组顺序消费的，如果取到队列的最后一位，就要从队列的第0位开始继续消费，
         * 不用担心第0位没有数据，因为在一开始的while循环中，已经对count的数量进行了判断
         */
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        //上面已经消费了数据，队列中空了一位，可以通知生产者继续生产
        synchronized (notFull) {
            notFull.notify();
        }
        return value;
    }
}