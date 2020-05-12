package com.example.demo.common.util;

import com.example.demo.common.exception.CommonException;
import org.springframework.beans.BeanUtils;

public class BeanMapperUtils {

    public static <T> T createAndCopyProperties(Object source, Class<T> clazz) {
        try {
            T dest = clazz.newInstance();
            BeanUtils.copyProperties(source, dest);
            return dest;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommonException("the target object of the createAndCopyProperties" +
                    "must provided a default constructor");
        }
    }
}
