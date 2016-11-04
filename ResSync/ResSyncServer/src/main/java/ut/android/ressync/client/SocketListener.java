package ut.android.ressync.client;

import java.io.IOException;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public interface SocketListener {
    void onConnectSuccess();

    void onConnectFail();

    void onUnConnect();

}
