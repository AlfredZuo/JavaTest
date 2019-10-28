import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UserDefinedDeComplier {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c = Class.forName("UserBean");//这里实际是要把UserBean.class放在目录下就可以了，不需要UserBean.java文件
        //获取属性Field+属性的修饰符

        //这里仅仅是一个测试,如果只是getFields就只能获取public方式的属性type
        //Field[] fs = c.getFields();
        Field[] fs = c.getDeclaredFields();
        System.out.println(fs.length);
        System.out.println(fs[0].getName());
        System.out.println("==========================================");

        //以下已经输出了主要的内容，但是需要调整输出形式
        for(Field field:fs){
            System.out.println(field.getModifiers());
            System.out.println(Modifier.toString(field.getModifiers()));//修饰符可以通过toString将int类型进行转换
            System.out.println(field.getType().getSimpleName());
            System.out.println(field.getName());
        }

        System.out.println("==========================调整反编译输出形式==========================");
        System.out.println("==========================调整反编译输出形式==========================");
        System.out.println("==========================调整反编译输出形式==========================");
        System.out.println("==========================调整反编译输出形式==========================");

        /*
                public class UserBean {
                    public String name;
                    private int age;
                    protected String addr;
                    boolean sex;
                }
        */

        StringBuffer sb = new StringBuffer();
        sb.append(Modifier.toString(c.getModifiers())+" class "+ c.getSimpleName()+ " {\r\n");
        for(Field field:fs){
            sb.append("\t"+ Modifier.toString(field.getModifiers()))
                    .append(Modifier.toString(field.getModifiers())==""? "":" ")                  //如果修饰符列表是缺省的，则不需要补充空格
                    .append(field.getType().getSimpleName()+" ")
                    .append(field.getName()+";\r\n");
        }
        sb.append("}\r\n");
        System.out.println(sb);

    }

}
