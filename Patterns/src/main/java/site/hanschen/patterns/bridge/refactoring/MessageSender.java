package site.hanschen.patterns.bridge.refactoring;

/**
 * 消息发送器
 *
 * @author HansChen
 */
public interface MessageSender {

    /**
     * 发送消息
     *
     * @param message 要发送的消息内容
     * @param toUser  消息发送的目的人员
     */
    void send(String message, String toUser);
}
