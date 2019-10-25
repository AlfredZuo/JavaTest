public class IntegerTest {
    public static void main(String[] args) {
        System.out.println("int最大值为:" + Integer.MAX_VALUE);
        System.out.println("int最小值为:" + Integer.MIN_VALUE);

        int i0 = 123;
        Integer i1 = new Integer(123);
        Integer i2 = new Integer("234");
        Integer i3 = Integer.valueOf("345");
        Integer i4 = Integer.valueOf(456);
        Integer i5 = Integer.parseInt("567");
        //Integer i6 = Integer.parseInt("abc");
        Integer i7 = 789;//这个是新版Java的功能

        System.out.println("i1为:" + i0);
        System.out.println("i1为:" + i1);
        System.out.println("i2为:" + i2);
        System.out.println("i3为:" + i3);
        System.out.println("i4为:" + i4);
        System.out.println("i5为:" + i5);
        //System.out.println("i6为:"+i6);
        System.out.println("i7为:" + i7);
        System.out.println("i7为:" + i7.getClass());


    }
}
