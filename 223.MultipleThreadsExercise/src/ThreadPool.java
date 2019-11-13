/**
 * 这里实现一个极度简陋的固定容量的线程池，
 * 功能是：初始化固定数量的活跃线程，阻塞直到有可用的线程用于提交任务。它只有一个接口方法，接口定义如下：
 */

public interface ThreadPool {

    void execute(Runnable runnable);
}