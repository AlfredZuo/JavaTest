import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * IO+Properties;
 * @Author mygodzj
 *
 */

public class IPTest {
    public static void main(String[] args) throws IOException {
        //1、创建属性对象
        Properties p = new Properties();//和Map一样，只不过key和value只能存储字符串。key不能重复，如果key重复，则value覆盖。
        //2、创建输入流
        FileInputStream fis = new FileInputStream("E:\\Java\\JavaTest\\228.IO&Properities\\src\\dbinfo.properties");
        //3、将fis流中所有数据加载到属性对象中
        p.load(fis);//所以现在属性对象中有(key=username，value=scott)
        //4、关闭流
        fis.close();

        //通过key获取value
        String v1 = p.getProperty("driver");
        String v2 = p.getProperty("url");
        String v3 = p.getProperty("username");
        String v4 = p.getProperty("password");


        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);
        System.out.println(v4);



    }
}
