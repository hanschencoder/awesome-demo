package site.hanschen.patterns.factory;

/**
 * Sender的实现类，通过Wi-Fi发送数据
 *
 * @author HansChen
 */
public class WiFiSender implements Sender {

    @Override
    public void sendData(byte[] data) {
        System.out.println("Send data by Wi-Fi");
    }
}
