public class Employee {
    //Field
    private  String name;

    //Constructor
    public Employee(String name) {
        this.name = name;
    }

    public Employee() {
        System.out.println("Employee的无参数构造方法执行");
    }

    //Method
    public void work(){
        System.out.println(name+"在工作");
    }
}
