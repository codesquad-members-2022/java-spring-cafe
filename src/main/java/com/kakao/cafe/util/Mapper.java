package com.kakao.cafe.util;

import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Mapper {

    public static <T> T map(Object obj, Class<T> type) {
        if (obj == null || type == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        try {
            Map<String, Object> sourceMap = sourceToMap(obj);
            return mapToTarget(sourceMap, type);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    // source fields -> map
    private static Map<String, Object> sourceToMap(Object obj) throws IllegalAccessException {
        Field[] sourceFields = obj.getClass().getDeclaredFields();
        Map<String, Object> sourceMap = new HashMap<>();

        for (Field field : sourceFields) {
            field.setAccessible(true);
            sourceMap.put(field.getName(), field.get(obj));
        }
        return sourceMap;
    }

    // map -> target fields
    private static <T> T mapToTarget(Map<String, Object> sourceMap, Class<T> type)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();

        Field[] targetFields = type.getDeclaredFields();

        for (Field field : targetFields) {
            field.setAccessible(true);
            field.set(instance, sourceMap.get(field.getName()));
        }
        return instance;
    }

}
