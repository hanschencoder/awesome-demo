package ut.android.ressync.netty;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
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
 * Created by chenhang on 2016/11/3.
 */

public class NettyServer {

    private Channel        mChannel;
    private EventLoopGroup mBossGroup;
    private EventLoopGroup mWorkerGroup;
    private volatile List<ChannelHandlerContext> mActiveHandlers = new ArrayList<>();

    public NettyServer() {
    }

    public synchronized void bind(final int port, final ChannelHandler handler) throws InterruptedException {
        if (!isActive()) {
            Log.d("Hans", "try bind " + port);
            mBossGroup = new NioEventLoopGroup();
            mWorkerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(mBossGroup, mWorkerGroup)
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
            ChannelFuture future = bootstrap.bind(port).sync();
            mChannel = future.channel();
        }
    }

    public synchronized void unbind() {
        if (mChannel != null) {
            mChannel.close();
            mBossGroup.shutdownGracefully();
            mWorkerGroup.shutdownGracefully();
        }
        mChannel = null;
        mBossGroup = null;
        mWorkerGroup = null;
        mActiveHandlers.clear();
    }

    public synchronized void handlerAdded(ChannelHandlerContext ctx) {
        if (!mActiveHandlers.contains(ctx)) {
            mActiveHandlers.add(ctx);
        }
    }

    public synchronized void handlerRemoved(ChannelHandlerContext ctx) {
        if (mActiveHandlers.contains(ctx)) {
            mActiveHandlers.remove(ctx);
        }
    }

    public int getActiveNumber() {
        return mActiveHandlers.size();
    }

    public List<ChannelHandlerContext> getActiveHandlers() {
        return mActiveHandlers;
    }

    public void sendPacket(ProtoBean.Packet packet) {
        for (ChannelHandlerContext context : getActiveHandlers()) {
            context.writeAndFlush(packet);
        }
    }

    public synchronized boolean isActive() {
        return mChannel != null && mChannel.isActive();
    }
}
