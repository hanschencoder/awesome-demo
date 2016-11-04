package ut.android.ressync.client;


/**
 * Created by chenhang on 2016/11/3.
 */

public interface SocketManager {

    boolean isActive();

    void connect(String ip, int port);

    void connect();

    void unConnect();

    void registerSocketListener(SocketListener listener);
}
