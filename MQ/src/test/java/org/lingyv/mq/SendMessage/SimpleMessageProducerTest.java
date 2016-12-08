package org.lingyv.mq.SendMessage;

import base.YvTest;
import org.junit.Test;
import org.lingyv.mq.models.User;

import javax.annotation.Resource;

/**
 * Created by lingyv on 2016/12/8.
 */
public class SimpleMessageProducerTest extends YvTest {
    @Resource
    private SimpleMessageProducer simpleMessageProducer;

    @Test
    public void sendMessages() throws Exception {
        User user = new User();
        user.setAge(23);
        user.setName("Max");
        simpleMessageProducer.sendMessages(user);
    }

}