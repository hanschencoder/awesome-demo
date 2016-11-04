package ut.android.ressync.server;

import com.google.protobuf.ByteString;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import ut.android.ressync.protobuf.ProtoBean;


/**
 * Created by jianjianhong on 2016/11/3.
 */
public class ResSyncServer implements ResSyncServerApi{

    private Channel channel;
    private ResSyncServerHandler handler;
    private static ReceiverDataListener listener;
    private static ResSyncServer instance;
    private ResSyncServer() {}

    public static ResSyncServer getInstance() {
        if (null == instance) {
            throw new IllegalStateException(ResSyncServer.class.getSimpleName()
                    + " is not initialized, call initialize(..) method first.");
        }
        return instance;
    }

    public static void initialize(ReceiverDataListener receiverDataListener) {
        if (null == instance) {
            instance = new ResSyncServer();
        }
        listener = receiverDataListener;
    }

    public void bind(int port) {
        if(channel == null || !channel.isActive()) {
            //配置服务端NIO线程组
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try{
                ServerBootstrap b = new ServerBootstrap();
                handler = new ResSyncServerHandler(listener);
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {

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
                //绑定端口，同步等待成功
                ChannelFuture f = b.bind(port).sync();
                //等待服务端监听端口关闭
                channel = f.channel();
                channel.closeFuture().sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                //退出时释放资源
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }
    }

    public void sendFileData(byte[] data, String operation, String filePath) {
        if(handler == null) {
            throw new NullPointerException("handler is null");
        }else if(handler.getActiveContextList().size() == 0) {
            throw new NullPointerException("not client connected");
        }
        ProtoBean.FileInfo fileInfo = ProtoBean.FileInfo.newBuilder()
                .setData(ByteString.copyFrom(data))
                .setOperation(operation)
                .setSavePath(filePath)
                .build();
        ProtoBean.Packet packet = ProtoBean.Packet.newBuilder()
                .setMessageType(ProtoBean.MessageType.FILE_SYNC)
                .setFileInfo(fileInfo)
                .build();
        for(ChannelHandlerContext context : handler.getActiveContextList()) {
            context.writeAndFlush(packet);
        }
    }

}
