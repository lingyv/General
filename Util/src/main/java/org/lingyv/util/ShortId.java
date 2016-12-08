package org.lingyv.util;

import java.util.Random;

/**
 * 指定位数的ID
 */
public class ShortId {
    static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 获取一个4位数的Id
     *
     * @return
     */
    public static String getId() {
        return getId(4);
    }

    /**
     * 获取一个指定位数的Id
     *
     * @param length
     * @return
     */
    public static String getId(int length) {
        StringBuffer s = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            s.append(chars[random.nextInt(62)]);
        }
        return s.toString();
    }
}
