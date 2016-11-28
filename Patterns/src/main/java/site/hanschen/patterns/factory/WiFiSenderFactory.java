package site.hanschen.patterns.factory;

/**
 * @author HansChen
 */
public class WiFiSenderFactory implements SenderFactory {

    @Override
    public Sender createSender() {
        return new WiFiSender();
    }
}
