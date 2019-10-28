import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class RIOP {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1、创建属性对象
        Properties p = new Properties();
        //2、创建流
        FileReader fr = new FileReader(".\\src\\classInfo.properties");
        //3、加载
        p.load(fr);
        //4、关闭流
        fr.close();
        //通过key获取value
        String className = p.getProperty("className");

        System.out.println(className);
        //通过反射机制创建对象
        Class c = Class.forName(className);
        //创建对象
        Date d = (Date) c.newInstance();

        System.out.println(d);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(d));

    }
}
