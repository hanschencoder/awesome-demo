package site.hanschen.proguard;

/**
 * @author HansChen
 */
public class Keep {

    private static final String helloStr = "Hello, ProGuard !";

    public static void main(String[] args) {
        Keep keep = new Keep();
        keep.sayHello();
    }

    public void sayHello() {
        System.out.println(helloStr);
    }
}
