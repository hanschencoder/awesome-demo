package site.hanschen.patterns.factory;

/**
 * @author HansChen
 */
public class WiFiSender implements Sender {

    @Override
    public void sendData(byte[] data) {
        System.out.println("Send data by Wi-Fi");
    }
}
