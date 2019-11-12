/**
 * 实现一个简单固定容量的阻塞队列，接口如下
 */

public interface BlockingQueue<T> {

    void put(T value) throws InterruptedException;

    T take() throws InterruptedException;
}