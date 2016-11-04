package ut.android.ressync.server;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public interface ResSyncServerApi {
    void bind(int port);
    void sendFileData(byte[] data, String operation, String filePath);
}
