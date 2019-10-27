
public class ReflectTest{
    public static void main(String[] args) throws ClassNotFoundException {
        Class c1 = Class.forName("Employee");
        Class c2= Employee.class;
        Employee e = new Employee();
        Class c3 = e.getClass();
    }
}