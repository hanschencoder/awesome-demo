package site.hanschen.patterns.bridge.ugly;

/**
 * @author HansChen
 */
public class CommonMessageEmail implements Message {

    @Override
    public void send(String message, String toUser) {
        System.out.println("使用Email的方式，发送消息'" + message + "'给" + toUser);
    }
}
