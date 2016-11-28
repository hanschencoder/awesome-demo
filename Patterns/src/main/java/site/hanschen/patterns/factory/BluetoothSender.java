package site.hanschen.patterns.factory;

/**
 * Sender的实现类，通过蓝牙发送数据
 *
 * @author HansChen
 */
public class BluetoothSender implements Sender {

    @Override
    public void sendData(byte[] data) {
        System.out.println("Send data by Bluetooth");
    }
}