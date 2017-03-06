package site.hanschen.proguard;

/**
 * @author HansChen
 */
public class keepclasseswithmembers {

    private static final String helloStr = "Hello, ProGuard !";

    public static void main(String[] args) {
        keepclasseswithmembers keep = new keepclasseswithmembers();
        keep.sayHello();
    }

    public void sayHello() {
        System.out.println(helloStr);
    }
}
