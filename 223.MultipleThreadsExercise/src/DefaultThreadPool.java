import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一些背景知识：
 * 1）关于Deque双端队列
 * Deque支持两端元素插入和移除的线性集合。
 * 名称deque是“双端队列”的缩写，通常发音为“deck”。
 * 大多数Deque实现对它们可能包含的元素的数量没有固定的限制，但是该接口支持容量限制的deques以及没有固定大小限制的deques。
 * 该界面定义了访问deque两端元素的方法。 提供了插入，移除和检查元素的方法。
 * 这些方法中的每一种存在两种形式：如果操作失败，则会抛出异常，另一种方法返回一个特殊值（ null或false ，具体取决于操作）。
 * 插入操作的后一种形式专门设计用于容量限制的Deque实现; 在大多数实现中，插入操作不能失败。
 *
 *
 */

public class DefaultThreadPool implements ThreadPool {

    private final int capacity;
    private List<Worker> initWorkers;
    private Deque<Worker> availableWorkers;
    private Deque<Worker> busyWorkers;
    private final Object nextLock = new Object();

    public DefaultThreadPool(int capacity) {
        this.capacity = capacity;
        init(capacity);
    }

    private void init(int capacity) {
        initWorkers = new ArrayList<>(capacity);
        availableWorkers = new LinkedList<>();
        busyWorkers = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            worker.setName("Worker-" + (i + 1));
            worker.setDaemon(true);//将work对象设置为守护线程，目的？
            initWorkers.add(worker);
        }
        for (Worker w : initWorkers) {
            w.start();
            availableWorkers.add(w);
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (null == runnable) {
            return;
        }
        synchronized (nextLock) {
            while (availableWorkers.size() < 1) {
                try {
                    nextLock.wait(500);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
            Worker worker = availableWorkers.removeFirst();
            busyWorkers.add(worker);
            worker.run(runnable);
            nextLock.notifyAll();
        }
    }

    private void makeAvailable(Worker worker) {
        synchronized (nextLock) {
            availableWorkers.add(worker);
            busyWorkers.remove(worker);
            nextLock.notifyAll();
        }
    }

    private class Worker extends Thread {

        private final Object lock = new Object();
        private Runnable runnable;
        private AtomicBoolean run = new AtomicBoolean(true);

        private void run(Runnable runnable) {
            synchronized (lock) {
                if (null != this.runnable) {
                    throw new IllegalStateException("Already running a Runnable!");
                }
                this.runnable = runnable;
                lock.notifyAll();
            }
        }

        @Override
        public void run() {
            boolean ran = false;
            while (run.get()) {
                try {
                    synchronized (lock) {
                        while (runnable == null && run.get()) {
                            lock.wait(500);
                        }

                        if (runnable != null) {
                            ran = true;
                            runnable.run();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    synchronized (lock) {
                        runnable = null;
                    }
                    if (ran) {
                        ran = false;
                        makeAvailable(this);
                    }
                }
            }
        }
    }
}