package site.hanschen.proguard;

/**
 * @author HansChen
 */
public class Keepclassmembers {

    private static final String helloStr = "Hello, ProGuard !";

    public static void main(String[] args) {
        Keepclassmembers keep = new Keepclassmembers();
        keep.sayHello();
    }

    public void sayHello() {
        System.out.println(helloStr);
    }
}
