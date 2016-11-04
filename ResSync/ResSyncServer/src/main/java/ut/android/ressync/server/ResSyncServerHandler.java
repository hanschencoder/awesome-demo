package ut.android.ressync.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import ut.android.ressync.protobuf.ProtoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 2016/11/3.
 */
@ChannelHandler.Sharable
public class ResSyncServerHandler extends SimpleChannelInboundHandler<ProtoBean.Packet> {

    private List<ChannelHandlerContext> activeContextList = new ArrayList<ChannelHandlerContext>();
    private ReceiverDataListener listener;

    public ResSyncServerHandler(ReceiverDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBean.Packet packet) throws Exception {
        switch (packet.getMessageType()) {
            case REPORT_MOBILE_INFO:
                ProtoBean.MobileInfo mobileInfo = packet.getMobileInfo();
                listener.onMobileInfo(mobileInfo.getMode(), mobileInfo.getDevice(), mobileInfo.getProduct(),
                        mobileInfo.getManufacturer(), mobileInfo.getRelease(), mobileInfo.getSdk());
                break;
            case REPORT_SUCCEED:
                listener.onSucceed(packet.getFilePath());
                break;
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        if(!activeContextList.contains(ctx)) {
            activeContextList.add(ctx);
        }
        System.out.println("add: " + ctx.toString() +", ContextList size :"+ activeContextList.size());
     }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(activeContextList.contains(ctx)) {
            activeContextList.remove(ctx);
        }
        System.out.println("removed: " + ctx.toString() +", ContextList size :"+ activeContextList.size());
    }

    public List<ChannelHandlerContext> getActiveContextList() {
        return activeContextList;
    }
}
