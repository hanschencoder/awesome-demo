package site.hanschen.proguard;

/**
 * @author HansChen
 */
public class keepnames {

    private static final String helloStr = "Hello, ProGuard !";

    public static void main(String[] args) {
        keepnames keep = new keepnames();
        keep.sayHello();
    }

    public void sayHello() {
        System.out.println(helloStr);
    }
}
