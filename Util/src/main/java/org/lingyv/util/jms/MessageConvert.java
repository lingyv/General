package org.lingyv.util.jms;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.*;
import java.util.HashMap;

/**
 * Created by lingyv on 2016/12/8.
 */
public class MessageConvert {
    private static final Log log = LogFactory.getLog(MessageConvert.class);

    public Message toMessage(Object obj, Session session) throws JMSException {
        if (log.isDebugEnabled()) {
            log.debug("toMessage(Object, Session) - start");
        }

        // check Type
        ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) session.createObjectMessage();
        HashMap<String, byte[]> map = new HashMap<String, byte[]>();
        try {
            // POJO must implements Seralizable
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            map.put("POJO", bos.toByteArray());
            objMsg.setObjectProperty("Map", map);

        } catch (IOException e) {
            log.error("toMessage(Object, Session)", e);
        }
        return objMsg;
    }

    public Object fromMessage(Message msg) throws JMSException {
        if (log.isDebugEnabled()) {
            log.debug("fromMessage(Message) - start");
        }

        if (msg instanceof ObjectMessage) {
            @SuppressWarnings("unchecked")
            HashMap<String, byte[]> map = (HashMap<String, byte[]>) ((ObjectMessage) msg).getObjectProperty("Map");
            try {
                // POJO must implements Seralizable
                ByteArrayInputStream bis = new ByteArrayInputStream(map.get("POJO"));
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object returnObject = ois.readObject();
                return returnObject;
            } catch (IOException e) {
                log.error("fromMessage(Message)", e);

            } catch (ClassNotFoundException e) {
                log.error("fromMessage(Message)", e);
            }

            return null;
        } else {
            throw new JMSException("Msg:[" + msg + "] is not Map");
        }
    }
}
