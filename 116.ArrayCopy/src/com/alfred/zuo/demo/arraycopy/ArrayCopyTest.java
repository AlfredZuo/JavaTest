package com.alfred.zuo.demo.arraycopy;
import com.alfred.zuo.demo.arraycopy.ArrayCopy;

public class ArrayCopyTest {
    public static void main(String[] args) {
        int[] a1 = {1,2,3,5,6,7,8};
        String[] a2 ={"123","","45","6","789","abcdefg","zzz","AlfredZuo"};
        char[] a3 = {'a','b','c','e','f','z'};
        int[] b1= new int[20];
        String[] b2= new String[15];
        char[] b3= new char[10];
        ArrayCopy ac = new ArrayCopy();
        ArrayCopyTest act = new ArrayCopyTest();

        int length1 = ac.objectArrayCopy(a1, b1);
        int length2 = ac.objectArrayCopy(a2, b2);
        //int length3 = ac.objectArrayCopy(a3, b3);

/*        System.out.println(new int[5].getClass());
        System.out.println(new int[5].getClass().getSuperclass());
        System.out.println(new Integer[5].getClass());
        System.out.println(new Integer[5].getClass().getSuperclass());
        System.out.println(new Object[5].getClass());
        System.out.println(new Object[5].getClass().getSuperclass());
        System.out.println(b2.getClass());
        System.out.println(b2.getClass().getSuperclass());*/

        System.out.println("================================================");
        System.out.println("Method ArrayCopy has copied "+ length1 +" elements in the array.");
        act.arrayPrint(a1);
        act.arrayPrint(b1);

        System.out.println("================================================");
        System.out.println("Method ArrayCopy has copied "+ length2 +" elements in the array.");
        act.arrayPrint(a2);
        act.arrayPrint(b2);

/*        System.out.println("================================================");
        System.out.println("Method ArrayCopy has copied "+ length3 +" elements in the array.");
        act.arrayPrint(a3);
        act.arrayPrint(b3);*/

    }

    public void arrayPrint(Object[] oa){

        System.out.println(oa + "'s length is "+ oa.length);
        for(int i=0; i<oa.length;i++){
            System.out.print(oa[i]+", ");
        }
        System.out.println();
    }

    public void arrayPrint(int[] ia){

        System.out.println(ia + "'s length is "+ ia.length);
        for(int i=0; i<ia.length;i++){
            System.out.print(ia[i]+", ");
        }
        System.out.println();
    }
}
