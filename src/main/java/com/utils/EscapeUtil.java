package com.utils;


import com.Filter.HTMLFilter;

/**
 * 转义和反转义工具类
 *
 * @author ruoyi
 */
public class EscapeUtil {

    /**
     * 清除所有HTML标签，但是不删除标签内的内容
     *
     * @param content 文本
     * @return 清除标签后的文本
     */
    public static String clean(String content) {
        return new HTMLFilter().filter(content);
    }


}
