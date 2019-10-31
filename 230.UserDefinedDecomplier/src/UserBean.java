public class UserBean {
    public String name;
    private int age;
    protected String addr;
    boolean sex;

    //登录
    public boolean login(String username, String pwd){
        if("admin".equals(name)&&"123".equals(pwd)){
            System.out.println("欢迎回来");
            return true;
        }
        return false;
    }

    //退出
    public void logout(){
        System.out.println("系统已经安全退出");
    }

    //constructor
    public UserBean() {
    }

    public UserBean(String name, int age, String addr, boolean sex) {
        this.name = name;
        this.age = age;
        this.addr = addr;
        this.sex = sex;
    }

    //getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddr() {
        return addr;
    }

    public boolean isSex() {
        return sex;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }


}
