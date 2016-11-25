package site.hanschen.patterns.factory;

/**
 * @author HansChen
 */
public class SimpleFactory {

    public static Sender createSender(String type) {
        switch (type) {
            case "Wi-Fi":
                return new WiFiSender();
            case "Bluetooth":
                return new BluetoothSender();
            default:
                throw new IllegalArgumentException("illegal type: " + type);
        }
    }
}
