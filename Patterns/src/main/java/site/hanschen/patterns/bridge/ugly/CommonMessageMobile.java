package site.hanschen.patterns.bridge.ugly;

/**
 * @author HansChen
 */
public class CommonMessageMobile implements Message {

    @Override
    public void send(String message, String toUser) {
        System.out.println("使用手机的方式，发送消息'" + message + "'给" + toUser);
    }
}
