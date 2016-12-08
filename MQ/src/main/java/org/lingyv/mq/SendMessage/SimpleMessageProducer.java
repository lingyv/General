package org.lingyv.mq.SendMessage;

import org.lingyv.util.MapUtil;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lingyv on 2016/11/21.
 */

public class SimpleMessageProducer {
    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessages(Object message) throws IllegalAccessException {
        Map<String, Object> mapMessage = MapUtil.toMap(message);
        jmsTemplate.convertAndSend("Test", mapMessage);
    }
}
