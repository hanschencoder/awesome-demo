package ut.android.ressync.client;

import com.google.protobuf.ByteString;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import ut.android.ressync.protobuf.ProtoBean;
import ut.android.ressync.server.ReceiverDataListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jianjianhong on 2016/11/4.
 */
public class ResSyncClient implements ResSyncClientApi, SocketManager {

    private Channel mChannel;
    private ResSyncClientHandler handler;
    private SocketListener socketListener;
    private ResSyncClientListener receiverDataListener;
    private static ResSyncClient instance = new ResSyncClient();
    private ResSyncClient() {}

    public static ResSyncClient getInstance() {
        return instance;
    }

    public void registerSocketListener(SocketListener listener) {
        this.socketListener = listener;
    }

    public void registerReceiverListener(ResSyncClientListener listener) {
        this.receiverDataListener = listener;
    }

    public synchronized void connect(String ip, int port){
        try {
            if(socketListener == null) {
                throw new IllegalStateException("SocketListener is not initialized, call registerSocketListener(..) method first.");
            }else if(receiverDataListener == null) {
                throw new IllegalStateException("ReceiverDataListener is not initialized, call registerReceiverListener(..) method first.");
            }
            if (!isActive()) {
                NioEventLoopGroup group = new NioEventLoopGroup();
                Bootstrap bootstrap = new Bootstrap();
                handler = new ResSyncClientHandler(receiverDataListener, socketListener);
                bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) TimeUnit.SECONDS.toMillis(10))
                        .group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new ProtobufVarint32FrameDecoder())
                                        .addLast(new ProtobufDecoder(ProtoBean.Packet.getDefaultInstance()))
                                        .addLast(new ProtobufVarint32LengthFieldPrepender())
                                        .addLast(new ProtobufEncoder())
                                        .addLast(handler);
                            }
                        });
                ChannelFuture future = bootstrap.connect(ip, port).sync();
                mChannel = future.channel();
                socketListener.onConnectSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            socketListener.onConnectFail();
        }
    }

    public synchronized void connect() {
        try {
            Runtime.getRuntime().exec("adb shell am broadcast -a NotifyServiceStop");
            Runtime.getRuntime().exec("adb forward tcp:9000 tcp:10000");
            Runtime.getRuntime().exec("adb shell am broadcast -a NotifyServiceStart");
            connect("127.0.0.1", 9000);
        } catch (IOException e) {
            e.printStackTrace();
            socketListener.onConnectFail();
        }
    }

    public void unConnect() {
        if(isActive()) {
            mChannel.close();
        }
    }

    public boolean isActive() {
        return mChannel != null && mChannel.isActive();
    }

    public void sendFileData(byte[] data, String operation, String filePath) {
        if (isActive()) {
            ProtoBean.FileInfo fileInfo = ProtoBean.FileInfo.newBuilder()
                    .setData(ByteString.copyFrom(data))
                    .setOperation(operation)
                    .setSavePath(filePath)
                    .build();
            ProtoBean.Packet packet = ProtoBean.Packet.newBuilder()
                    .setMessageType(ProtoBean.MessageType.FILE_SYNC)
                    .setFileInfo(fileInfo)
                    .build();
            mChannel.writeAndFlush(packet);
        }else {
            throw new IllegalStateException("Client Channel is close");
        }

    }
}
