package ut.android.ressync.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ut.android.ressync.protobuf.ProtoBean;
import ut.android.ressync.server.ReceiverDataListener;

/**
 * Created by jianjianhong on 2016/11/3.
 */
@ChannelHandler.Sharable
public class ResSyncClientHandler extends SimpleChannelInboundHandler<ProtoBean.Packet> {

    private ResSyncClientListener syncClientListener;
    private SocketListener socketListener;

    public ResSyncClientHandler(ResSyncClientListener syncClientListener, SocketListener socketListener) {
        this.syncClientListener = syncClientListener;
        this.socketListener = socketListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBean.Packet packet) throws Exception {
        switch (packet.getMessageType()) {
            case REPORT_MOBILE_INFO:
                ProtoBean.MobileInfo mobileInfo = packet.getMobileInfo();
                syncClientListener.onMobileInfo(mobileInfo.getMode(), mobileInfo.getDevice(), mobileInfo.getProduct(),
                        mobileInfo.getManufacturer(), mobileInfo.getRelease(), mobileInfo.getSdk());
                break;
            case REPORT_SUCCEED:
                syncClientListener.onSucceed(packet.getFilePath());
                break;
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        socketListener.onUnConnect();
    }
}
