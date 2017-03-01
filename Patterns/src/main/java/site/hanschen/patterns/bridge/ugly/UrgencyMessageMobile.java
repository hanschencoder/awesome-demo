package site.hanschen.patterns.bridge.ugly;

/**
 * @author HansChen
 */
public class UrgencyMessageMobile implements UrgencyMessage {

    @Override
    public void send(String message, String toUser) {
        message = "加急：" + message;
        System.out.println("使用手机发送消息的方式，发送消息'" + message + "'给" + toUser);
    }

    @Override
    public Object watch(String messageId) {
        //获取相应的数据，组织成监控的数据对象，然后返回
        return null;
    }
}
