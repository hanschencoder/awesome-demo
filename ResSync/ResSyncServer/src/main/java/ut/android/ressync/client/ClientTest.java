package ut.android.ressync.client;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public class ClientTest {
    public static void main(String[] args) throws Exception{

        ResSyncClientListener listener = new ResSyncClientListener() {
            public void onMobileInfo(String mode, String device, String product, String manufacturer, String release, int sdk) {
                System.out.println("mode:" + mode + ", device:" + device + ", product:"
                        + product + ", manufacturer:" + manufacturer + ", release: " + release + ", sdk:" +sdk);
            }

            public void onSucceed(String message) {
                System.out.println(message);
            }
        };

        SocketListener listener1 = new SocketListener() {
            public void onConnectSuccess() {
                System.out.println("onConnectSuccess");
            }

            public void onConnectFail() {
                System.out.println("onConnectFail");
            }

            public void onUnConnect() {
                System.out.println("onUnConnect");
            }
        };

        ResSyncClient.getInstance().registerReceiverListener(listener);
        ResSyncClient.getInstance().registerSocketListener(listener1);
        ResSyncClient.getInstance().connect("192.168.216.79", 10000);
        //ResSyncClient.getInstance().connect();
    }
}
