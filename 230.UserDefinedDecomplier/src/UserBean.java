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
}
