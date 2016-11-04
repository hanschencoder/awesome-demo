package ut.android.ressync;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ut.android.ressync.netty.NettyHandler;
import ut.android.ressync.netty.NettyServer;
import ut.android.ressync.protobuf.ProtoBean;

/**
 * Created by chenhang on 2016/11/3.
 */
public class ProtocolManager extends Service implements ResSyncServerApi, SocketManager {

    public static void bind(Context context, ServiceConnection conn) {
        Intent intent = new Intent(context, ProtocolManager.class);
        context.bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public static void unbind(Context context, ServiceConnection conn) {
        context.unbindService(conn);
    }

    private          Context        mContext;
    private          SocketListener mListener;
    private          NettyServer    mNettyServer;
    private volatile boolean        mIsBound;
    private          ReceiveHandler mReceiveHandler;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = ProtocolManager.this;
        mReceiveHandler = new ReceiveHandler(mContext.getApplicationContext());
        mNettyServer = new NettyServer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProtocolManagerBinder();
    }

    public final class ProtocolManagerBinder extends Binder {

        public ProtocolManager getProtocolManager() {
            return ProtocolManager.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean isBound() {
        return mIsBound && mNettyServer.isActive();
    }

    @Override
    public void bind(final int port) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("Hans", "onBindStart");
                if (mListener != null) {
                    mListener.onBindStart();
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mNettyServer.bind(port, mNettyHandler);
                    mIsBound = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Hans", "onBindSuccess");
                            if (mListener != null) {
                                mListener.onBindSuccess();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Hans", "onBindFail");
                            if (mListener != null) {
                                mListener.onBindFail();
                            }
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void unbind() {
        mNettyServer.unbind();
        mIsBound = false;
        Log.d("Hans", "onUnbind");
        if (mListener != null) {
            mListener.onUnbind();
        }
    }

    @Override
    public void registerSocketListener(@NonNull final SocketListener listener) {
        mListener = listener;
    }

    @Override
    public void unregisterSocketListener(@NonNull final SocketListener listener) {
        if (listener.equals(mListener)) {
            mListener = null;
        }
    }

    @Override
    public void keepAlive() {

    }

    @Override
    public void reportMobileInfo(String mode, String device, String product, String manufacturer, String release, int sdk) {
        ProtoBean.MobileInfo mobileInfo = ProtoBean.MobileInfo.newBuilder()
                                                              .setMode(mode)
                                                              .setDevice(device)
                                                              .setProduct(product)
                                                              .setManufacturer(manufacturer)
                                                              .setRelease(release)
                                                              .setSdk(sdk)
                                                              .build();
        ProtoBean.Packet packet = ProtoBean.Packet.newBuilder()
                                                  .setMessageType(ProtoBean.MessageType.REPORT_MOBILE_INFO)
                                                  .setMobileInfo(mobileInfo)
                                                  .build();
        mNettyServer.sendPacket(packet);
    }

    @Override
    public void reportSucceed(String filePath) {
        ProtoBean.Packet packet = ProtoBean.Packet.newBuilder()
                                                  .setMessageType(ProtoBean.MessageType.REPORT_SUCCEED)
                                                  .setFilePath(filePath)
                                                  .build();
        mNettyServer.sendPacket(packet);
    }

    private SimpleChannelInboundHandler<ProtoBean.Packet> mNettyHandler = new NettyHandler() {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
            mNettyServer.handlerAdded(ctx);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Hans", "onClientAdd");
                    if (mListener != null) {
                        mListener.onClientAdd(mNettyServer.getActiveNumber());
                    }
                }
            });
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            super.handlerRemoved(ctx);
            mNettyServer.handlerRemoved(ctx);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Hans", "onClientRemove");
                    if (mListener != null) {
                        mListener.onClientRemove(mNettyServer.getActiveNumber());
                    }
                }
            });
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            Log.d("Hans", "exceptionCaught");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, final ProtoBean.Packet msg) throws Exception {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Hans", msg.toString());
                    Toast.makeText(mContext, ("收到消息： " + msg.toString()), Toast.LENGTH_SHORT).show();
                    switch (msg.getMessageType()) {
                        case FILE_SYNC:
                            ProtoBean.FileInfo fileInfo = msg.getFileInfo();
                            mReceiveHandler.onFileReceived(fileInfo.getData().toByteArray(),
                                                           fileInfo.getOperation(),
                                                           fileInfo.getSavePath());
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    };

    private void runOnUiThread(Runnable runnable) {
        mMainHandler.post(runnable);
    }
}
