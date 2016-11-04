package ut.android.ressync.server;

/**
 * Created by jianjianhong on 2016/11/3.
 */
public class ServiceTest {
    public static void main(String[] args) throws Exception{
        ResSyncServer.initialize(new ReceiverDataListener() {
            public void onMobileInfo(String mode, String device, String product, String manufacturer, String release, int sdk) {
                System.out.println("mode:" + mode + ", device:" + device + ", product:"
                        + product + ", manufacturer:" + manufacturer + ", release: " + release + ", sdk:" +sdk);
            }

            public void onSucceed(String message) {
                System.out.print(message);
            }
        });
        ResSyncServer.getInstance().bind(10000);
    }
}
