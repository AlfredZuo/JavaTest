package com.alfred.zuo.demo.arraycopy;/*课后作业：自己编写一个程序，完成数组的拷贝 */

public class ArrayCopy {
    //Object[] objectArray = new Object[255];

    int shorterLength;

    public int objectArrayCopy (Object src[], Object dst[]){

        if(src.length > dst.length){
            shorterLength = dst.length;
        }
        else{
            shorterLength = src.length;
        }

        for (int i=0;i<shorterLength;i++){
            dst[i] = src[i];
        }

        return shorterLength;
    }

    /*本来想通过上面的函数通过向上转型来实现数组的通用拷贝
    * 经过研究发现，数组无法实现整个数组级别的向上转型
    * 现阶段唯一的办法是使用overload来实现通用性，比较挫
    * 后续找到其他方法后再做改进
    * 目前只写了通用的Object类型和int两种类型的，Object类型的可以实现Sting等非基本数据类型的数组copy
    * */
    public int objectArrayCopy (int src[], int dst[]){

        if(src.length > dst.length){
            shorterLength = dst.length;
        }
        else{
            shorterLength = src.length;
        }

        for (int i=0;i<shorterLength;i++){
            dst[i] = src[i];
        }

        return shorterLength;
    }

    public ArrayCopy() {
    }

/*    public ArrayCopy(Object[] objectArray) {
        this.objectArray = objectArray;
    }*/
}
