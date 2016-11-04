package ut.android.ressync.client;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public interface ResSyncClientApi {
    void sendFileData(byte[] data, String operation, String filePath);
    void registerReceiverListener(ResSyncClientListener listener);
}
