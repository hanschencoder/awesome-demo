package site.hanschen.patterns.bridge.refactoring;

/**
 * @author HansChen
 */
public class CommonMessageController extends AbstractMessageController {

    public CommonMessageController(MessageSender impl) {
        super(impl);
    }

    @Override
    public void sendMessage(String message, String toUser) {
        //对于普通消息，什么都不干，直接调父类的方法，把消息发送出去就可以了
        super.sendMessage(message, toUser);
    }
}
