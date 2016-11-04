package ut.android.ressync;


/**
 * 处理服务端发送过来的消息，注意，回调会运行在主线程
 */
public interface ResSyncServerListener {

    void onFileReceived(byte[] data, String operation, String savePath);

}
