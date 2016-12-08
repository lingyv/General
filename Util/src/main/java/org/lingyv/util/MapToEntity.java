package org.lingyv.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lingyv on 2016/9/26.
 * 利用反射，从Map中取出String类型的value并转成实体类集合
 */
public class MapToEntity<T> {

    /**
     * 传入List和需要转换的实体类
     * List中的key要与T中的属性名保持一致
     * 一次只能转换一个类
     * <p>
     * 实体类中属性的类型需有参数为String类型的含参构造器
     * <p>
     * Date类型格式为“yyyy-MM-dd”
     *
     * @param list
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public List<T> toEntity(List list, Class<?> c) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ParseException {
        Field[] field = c.getDeclaredFields();//获取类中的所有属性
        List<T> entitys = new ArrayList<T>();
        if (list != null) {
            for (Object l : list) { //遍历List
                Map<String, String> map = (Map<String, String>) l;
                T t = (T) c.newInstance();//新建T的对象，T需要有无参构造器
                for (Field f : field) {
                    toSetField(f, map, t);//对该属性赋值
                }
                entitys.add(t);
            }
        }
        return entitys;
    }


    /**
     * 传入map和需要转换的实体类
     * map中的key要与T中的属性名保持一致
     * 一次只能转换一个类
     * <p>
     * 实体类中属性的类型需有参数为String类型的含参构造器
     *
     * @param map
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public T toEntity(Map map, Class<?> c) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ParseException {
        Field[] field = c.getDeclaredFields();//获取类中的所有属性
        T t = (T) c.newInstance();//新建T的对象，T需要有无参构造器
        for (Field f : field) {
            toSetField(f, map, t);//对该属性赋值
        }

        return t;
    }

    private void toSetField(Field f, Map map, T t) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ParseException {
        String fieldName = f.getName();//获取属性名
        String value = (String) map.get(fieldName);//通过key(属性名)获取Map中的value
        if (value != null) {
            Class mType = f.getType();//获取属性类型

            String type = mType.getName();//获取属性类型名
            Object val = null;
            if (type != null && "java.util.Date".equals(type)) {    //如果变量是Date类型
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化Date
                val = sdf.parse(value);
            } else if (type != null && "int".equals(type)) {    //如果变量是int类型
                val = Integer.parseInt(value);
            } else {
                Constructor con = mType.getConstructor(String.class);//获取该类型-->参数为String类型的含参构造器
                val = con.newInstance(value);//调用构造器创建需要类型的对象
            }

            f.setAccessible(true);//取消默认 Java 语言访问控制检查的能力
            f.set(t, val);//对该属性赋值
        }
    }
}
