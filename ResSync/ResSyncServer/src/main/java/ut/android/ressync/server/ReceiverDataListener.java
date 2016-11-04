package ut.android.ressync.server;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public interface ReceiverDataListener {
    void onMobileInfo(String mode, String device, String product, String manufacturer, String release, int sdk);

    void onSucceed(String message);
}
