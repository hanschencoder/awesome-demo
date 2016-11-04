package ut.android.ressync;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ut.android.ressync.application.ClientApplication;

/**
 * Created by chenhang on 2016/11/3.
 */
public class ReceiveHandler implements ResSyncServerListener {

    private Context          mContext;
    private ResSyncServerApi mClientApi;

    public ReceiveHandler(Context appContext) {
        this.mContext = appContext;
        mClientApi = ClientApplication.getInstance().getServerManager();
    }

    @Override
    public void onFileReceived(byte[] data, String operation, String savePath) {
        // TODO: 2016/11/3
        File sdDir = Environment.getExternalStorageDirectory();
        File resDir = new File(savePath);
        if (resDir.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(resDir);
                fileOutputStream.write(data);
                fileOutputStream.close();
                Intent intent = new Intent("");
                intent.putExtra("operation", operation);
                intent.putExtra("filePath", savePath);
                mContext.sendBroadcast(intent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
