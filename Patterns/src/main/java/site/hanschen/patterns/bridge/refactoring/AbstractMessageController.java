package site.hanschen.patterns.bridge.refactoring;

/**
 * 抽象的消息对象
 *
 * @author HansChen
 */
public class AbstractMessageController {

    /**
     * 持有一个实现部分的对象
     */
    MessageSender impl;

    /**
     * 构造方法，传入实现部分的对象
     *
     * @param impl 实现部分的对象
     */
    AbstractMessageController(MessageSender impl) {
        this.impl = impl;
    }

    /**
     * 发送消息，转调实现部分的方法
     *
     * @param message 要发送的消息内容
     * @param toUser  消息发送的目的人员
     */
    public void sendMessage(String message, String toUser) {
        impl.send(message, toUser);
    }
}
