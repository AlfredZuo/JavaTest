/*
* 本类用于模拟栈的先进先出原则，仅允许使用一位数组实现
* 输入是一个字符串
* 压栈和弹栈都使用打印输出
* */
public class StackWithArray {

    private String[] stackSimulated; //用于模拟栈的数组
    private int frame; //栈帧始终指向顶部

    /*stackSimulated[0]为栈底*/

    public void push(String[] inStringArray){
        for(int i = 0; i < inStringArray.length; i++){
            stackSimulated[i] = inStringArray[i];
            System.out.println("Stack("+String.format("%03d",frame)+")'s content is "+ stackSimulated[i]);
            /*String.format("%03d",frame)这是针对int类型的frame输出格式前补3个零*/
            frame++;
        }
    }

    public void pop(){
        for (int i = frame-1; i >= 0; i--){
            System.out.println("Stack("+String.format("%03d",frame)+")'s content is "+ stackSimulated[i]);
            stackSimulated[i] = "";
            frame--;
        }
    }

    public StackWithArray() {
        this(255);//栈最大空间255,暂不考虑异常抛出流程
    }

    public StackWithArray(int maxLength) {
        this.stackSimulated =  new String[maxLength];
        this.frame = 0;
    }
}
