package site.hanschen.patterns.factory;

/**
 * @author HansChen
 */
public class BluetoothSenderFactory implements SenderFactory {

    @Override
    public Sender createSender() {
        return new BluetoothSender();
    }
}
