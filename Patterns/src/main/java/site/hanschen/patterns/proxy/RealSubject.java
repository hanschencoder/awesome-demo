package site.hanschen.patterns.proxy;

/**
 * @author HansChen
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("do real request");
    }
}
