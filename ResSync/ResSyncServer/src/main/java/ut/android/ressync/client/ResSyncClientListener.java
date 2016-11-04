package ut.android.ressync.client;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public interface ResSyncClientListener {
    void onMobileInfo(String mode, String device, String product, String manufacturer, String release, int sdk);

    void onSucceed(String message);
}
