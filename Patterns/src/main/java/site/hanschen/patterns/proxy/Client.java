package site.hanschen.patterns.proxy;


/**
 * @author HansChen
 */
public class Client {

    public static void main(String[] args) {

        Subject subject = new ProxySubject();
        subject.request();
    }
}
