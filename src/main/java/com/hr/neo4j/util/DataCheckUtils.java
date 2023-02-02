package com.hr.neo4j.util;

import com.alibaba.excel.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 数据校验工具类
 */
public class DataCheckUtils {
    /**
     * 判断属性中的所有内容为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        // 如果对象为null直接返回true
        if (null == object) {
            return true;
        }
        try {
            // 挨个获取对象属性值
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 如果有一个属性值不为null，且值不是空字符串，就返回false
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
