package ut.android.ressync;

/**
 * Created by chenhang on 2016/11/3.
 */
public interface ResSyncServerApi {

    void keepAlive();

    void reportMobileInfo(String mode, String device, String product, String manufacturer, String release, int sdk);

    void reportSucceed(String filePath);

}
