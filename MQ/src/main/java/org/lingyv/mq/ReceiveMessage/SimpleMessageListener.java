package org.lingyv.mq.ReceiveMessage;

import org.lingyv.mq.models.User;
import org.lingyv.util.MapUtil;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by lingyv on 2016/11/18.
 */
public class SimpleMessageListener implements MessageListener {

    public void onMessage(Message message) {
        User user = (User) MapUtil.toEntity(message, User.class);
        System.out.println("收到消息:  用户姓名: " + user.getName() + "   用户年龄:" + user.getAge());
    }
}
