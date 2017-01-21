package site.hanschen.patterns.proxy;

/**
 * @author HansChen
 */
public class ProxySubject implements Subject {

    private RealSubject mSubject;

    public ProxySubject() {
        mSubject = new RealSubject();
    }

    @Override
    public void request() {
        System.out.println("Log: before");
        mSubject.request();
        System.out.println("Log: after");
    }
}
