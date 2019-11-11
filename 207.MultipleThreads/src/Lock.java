public class Lock {

    private final Object lock = new Object();
    private int num = 1;

    public Object getLock() {
        return lock;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }
}
