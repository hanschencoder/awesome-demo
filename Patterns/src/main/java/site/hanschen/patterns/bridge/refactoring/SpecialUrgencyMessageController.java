package site.hanschen.patterns.bridge.refactoring;

/**
 * @author HansChen
 */
public class SpecialUrgencyMessageController extends AbstractMessageController {

    public SpecialUrgencyMessageController(MessageSender impl) {
        super(impl);
    }

    @Override
    public void sendMessage(String message, String toUser) {
        message = "特急：" + message;
        super.sendMessage(message, toUser);
    }

    public void hurry(String messageId) {
        //执行催促的业务，发出催促的信息
    }
}
