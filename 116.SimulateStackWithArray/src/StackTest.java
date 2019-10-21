/*采用字符串作为需要压栈和出栈的对象*/

public class StackTest {
    public static void main(String[] args) {
        String[] origArray = {"123","abc","345","def","100","zzz","@@@123","@@@abc","@@@345","@@@def","@@@100","@@@zzz"};
        StackWithArray swa = new StackWithArray();

        System.out.println("=============NOW begin to Push=============");
        swa.push(origArray);
        System.out.println("=============NOW begin to Pop=============");
        swa.pop();
    }
}
