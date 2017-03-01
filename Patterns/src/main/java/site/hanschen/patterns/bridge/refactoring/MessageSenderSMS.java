package site.hanschen.patterns.bridge.refactoring;

/**
 * 以站内短消息的方式发送消息
 *
 * @author HansChen
 */
public class MessageSenderSMS implements MessageSender {

    @Override
    public void send(String message, String toUser) {
        System.out.println("使用站内短消息的方式，发送消息'" + message + "'给" + toUser);
    }
}
