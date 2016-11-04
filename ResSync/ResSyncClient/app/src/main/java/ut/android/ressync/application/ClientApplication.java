package ut.android.ressync.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import ut.android.ressync.ProtocolManager;

/**
 * Created by chenhang on 2016/11/3.
 */
public class ClientApplication extends Application {

    private static ClientApplication sInstance;
    private        ProtocolManager   mProtocolManager;

    public static ClientApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = ClientApplication.this;
        bindProtocolManagerService();
    }

    private final ServiceConnection mProtoConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof ProtocolManager.ProtocolManagerBinder) {
                mProtocolManager = ((ProtocolManager.ProtocolManagerBinder) service).getProtocolManager();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mProtocolManager = null;
        }
    };

    private void bindProtocolManagerService() {
        ProtocolManager.bind(ClientApplication.this, mProtoConn);
    }

    private void unbindProtocolManagerService() {
        ProtocolManager.unbind(ClientApplication.this, mProtoConn);
        mProtocolManager = null;
    }

    public ProtocolManager getServerManager() {
        return mProtocolManager;
    }
}
