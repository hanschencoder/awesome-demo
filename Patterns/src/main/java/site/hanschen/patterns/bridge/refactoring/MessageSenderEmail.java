package site.hanschen.patterns.bridge.refactoring;

/**
 * 以Email的方式发送消息
 *
 * @author HansChen
 */
public class MessageSenderEmail implements MessageSender {

    @Override
    public void send(String message, String toUser) {
        System.out.println("使用Email的方式，发送消息'" + message + "'给" + toUser);
    }
}
