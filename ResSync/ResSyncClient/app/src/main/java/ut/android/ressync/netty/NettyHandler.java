package ut.android.ressync.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import ut.android.ressync.protobuf.ProtoBean;

/**
 * Created by chenhang on 2016/11/3.
 */
@ChannelHandler.Sharable
public abstract class NettyHandler extends SimpleChannelInboundHandler<ProtoBean.Packet> {

}
