package site.hanschen.patterns.factory;

/**
 * @author HansChen
 */
public class Test {

    private String mode; //Wi-Fi|Bluetooth

    public void onClick() {
        byte[] data = {0x00, 0x01};

        SenderFactory factory;
        if ("Wi-Fi".equals(mode)) {
            factory = new WiFiSenderFactory();
        } else {
            factory = new BluetoothSenderFactory();
        }
        Sender sender = factory.createSender();
        sender.sendData(data);
    }
}
