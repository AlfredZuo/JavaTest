public class Lock {

    private final Object lock = new Object();

    public Object getLock() {
        return lock;
    }
}
