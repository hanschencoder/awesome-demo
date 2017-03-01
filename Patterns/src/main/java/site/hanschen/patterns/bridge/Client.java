package site.hanschen.patterns.bridge;

import site.hanschen.patterns.bridge.refactoring.AbstractMessageController;
import site.hanschen.patterns.bridge.refactoring.CommonMessageController;
import site.hanschen.patterns.bridge.refactoring.MessageSender;
import site.hanschen.patterns.bridge.refactoring.MessageSenderMobile;
import site.hanschen.patterns.bridge.refactoring.MessageSenderSMS;
import site.hanschen.patterns.bridge.refactoring.SpecialUrgencyMessageController;
import site.hanschen.patterns.bridge.refactoring.UrgencyMessageController;

/**
 * @author HansChen
 */
public class Client {

    public static void main(String[] args) {
        //创建具体的实现对象
        MessageSender impl = new MessageSenderSMS();

        //创建一个普通消息对象
        AbstractMessageController controller = new CommonMessageController(impl);
        controller.sendMessage("请喝一杯茶", "小李");

        //创建一个紧急消息对象
        controller = new UrgencyMessageController(impl);
        controller.sendMessage("请喝一杯茶", "小李");

        //创建一个特急消息对象
        controller = new SpecialUrgencyMessageController(impl);
        controller.sendMessage("请喝一杯茶", "小李");


        //把实现方式切换成手机短消息，然后再实现一遍
        impl = new MessageSenderMobile();
        controller = new CommonMessageController(impl);
        controller.sendMessage("请喝一杯茶", "小李");

        controller = new UrgencyMessageController(impl);
        controller.sendMessage("请喝一杯茶", "小李");

        controller = new SpecialUrgencyMessageController(impl);
        controller.sendMessage("请喝一杯茶", "小李");
    }
}
