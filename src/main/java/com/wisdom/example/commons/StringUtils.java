package com.wisdom.example.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * <b>业务说明</b>:<br>
 * String工具类
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: May 14, 2008<br>
 * <b>建立时间</b>: 2:56:32 PM<br>
 * <b>项目名称</b>: financial<br>
 * <b>包及类名</b>: com.wisdom.financial.commons.StringUtils<br>
 */
@SuppressWarnings("unchecked")
public abstract class StringUtils extends org.springframework.util.StringUtils {

	/**
     * 将数组转换为逗号分隔的字符串
     */
    public static String arrayToCommaDelimitedString(byte[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    /**
     * 将数组转换为分隔符delim分隔的字符串
     */
    public static String arrayToDelimitedString(byte[] arr, String delim) {
        if (arr == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    /**
     * 将任何对象（包括数组）转换为字符串（如果是数组，则用逗号分隔）。
     */
    public static String anythingToString(Object anything) {
        if (anything instanceof Object[]) {
            return StringUtils.arrayToCommaDelimitedString((Object[]) anything);
        }
        if (anything instanceof byte[]) {
            return StringUtils.arrayToCommaDelimitedString((byte[]) anything);
        }
        return anything.toString();
    }

    /**
     * 将任何对象（包括数组）转换为字符串（如果是数组，则用delim分隔）。
     */
    public static String anythingToString(Object anything, String delim) {
        if (anything instanceof Object[]) {
            return StringUtils.arrayToDelimitedString((Object[]) anything,
                    delim);
        }
        if (anything instanceof byte[]) {
            return StringUtils.arrayToDelimitedString((byte[]) anything, delim);
        }
        return anything.toString();
    }
    
    /**
     * 将Map中的值对转换为字符串。参考ListOrderedMap。
     */
    public static String mapToKeyValueString(Map map) {
        if (map==null) {
            return "null";
        }
        if (map.isEmpty()) {
            return "{}";
        }
        StringBuffer buf = new StringBuffer();
        buf.append('{');
        boolean first = true;
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (first) {
                first = false;
            } else {
                buf.append(", ");
            }
            buf.append(key == map ? "(this Map)" : key);
            buf.append('=');
            buf.append(value == map ? "(this Map)" : value);
        }
        buf.append('}');
        return buf.toString();
    }

    //toString ... END ======================================================

    //text ... BEGIN ==========================================================
    /**
     * 如果字符串中没有文本内容，则返回true。
     */
    public static boolean hasNotText(String text) {
        return !hasText(text);
    }

    //text ... END ==========================================================

    //~ encode =================================================================
    static BitSet dontNeedEncoding;
    static {
        dontNeedEncoding = new BitSet(256);
        for (int i = 97; i <= 122; i++)
            dontNeedEncoding.set(i);

        for (int j = 65; j <= 90; j++)
            dontNeedEncoding.set(j);

        for (int k = 48; k <= 57; k++)
            dontNeedEncoding.set(k);

        dontNeedEncoding.set(32);
        dontNeedEncoding.set(45);
        dontNeedEncoding.set(95);
        dontNeedEncoding.set(46);
        dontNeedEncoding.set(42);
    }
    
    public static String encode(String source, String encoding) throws UnsupportedEncodingException {
        if (encoding == null || "".equalsIgnoreCase(encoding)) {
            return URLEncoder.encode(source, "GB2312");
        } else {
            byte[] rawBytes = source.getBytes(encoding);
            StringBuffer result = new StringBuffer(3 * rawBytes.length);
            for (int i = 0; i < rawBytes.length; i++) {
                int candidate = rawBytes[i] & 0xff;
                if (candidate == ' ') {
                    result.append('+');
                } else if (dontNeedEncoding.get(candidate)) {
                    result.append((char) rawBytes[i]);
                } else if (candidate < 16) {
                    result.append("%0").append(
                            Integer.toHexString(candidate).toUpperCase());
                } else {
                    result.append('%').append(
                            Integer.toHexString(candidate).toUpperCase());
                }
            }
            return result.toString();
        }
    }



}
