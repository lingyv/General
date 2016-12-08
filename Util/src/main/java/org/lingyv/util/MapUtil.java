package org.lingyv.util;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * MapUtil类
 */
public class MapUtil {

    /**
     * 使用反射机制将实体类对象转成Map对象，转型后的字段值保留原始类型信息
     *
     * @param obj 要转型的对象
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> toMap(Object obj) throws IllegalAccessException {
        Map<String, Object> result = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        result.put("class", obj.getClass().toString());
        for (Field field : declaredFields) {
            field.setAccessible(true);
            result.put(field.getName(), field.get(obj));
        }
        return result;
    }

    /**
     * 使用内省机制将实体类对象转成Map对象
     *
     * @param obj 要转型的对象
     * @return
     * @throws Exception
     */
    public static Map<String, Object> toMapIntrospect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                result.put(pd.getName(), reader.invoke(obj));
        }
        return result;
    }

    /**
     * 将JMS信息转型成实体类对象
     *
     * @param message 要转型成实体类对象的JMS信息。需要是MapMessage类的实例
     * @param c       目标实体类类型
     * @return
     */
    public static Object toEntity(Message message, Class c) {
        if (message instanceof MapMessage) {
            try {
                MapMessage msg = (MapMessage) message;
                SimpleMessageConverter converter = new SimpleMessageConverter();
                Object obj = c.newInstance();
                HashMap map = (HashMap) converter.fromMessage(msg);
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    toSetField(field, map, obj);
                }

                return obj;
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            throw new MessageConversionException("Message is not MapMessage");
        }

        return null;
    }

    /**
     * 根据Map中的value设置目标对象相应的属性的值
     * Map中的key为属性名
     * 与key对应的value应保留有原始类型信息
     *
     * @param field 要设置的目标属性
     * @param map   源map
     * @param obj   目标对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws ParseException
     * @throws ParseException
     */
    private static void toSetField(Field field, Map map, Object obj) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ParseException, ParseException {
        String fieldName = field.getName();
        if (map.get(fieldName) != null) {
            field.setAccessible(true);
            field.set(obj, map.get(fieldName));
        }
    }
}
