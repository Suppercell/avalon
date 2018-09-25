package com.avalon.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;

/**
 * JSON转换工具类
 */
public class JsonUtil {
    public static ObjectMapper  mapper;


    static {
        mapper = new ObjectMapper();
        mapper.disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    /**
     * 将object转为json字符串
     * 
     * @param pObj
     *            参数
     * @throws IOException
     *             异常
     * @throws JsonMappingException
     *             异常
     * @throws JsonGenerationException
     *             异常
     * @return String
     */
    public static String writeValue(Object pObj) throws JsonGenerationException,
                                                JsonMappingException, IOException {
        return JsonUtil.mapper.writeValueAsString(pObj);
    }

    /**
     * 将content转为java object
     * 
     * @param <T>
     *            参数
     * @param pContent
     *            参数
     * @param pObj
     *            参数
     * @throws JsonParseException
     *             异常
     * @throws JsonMappingException
     *             异常
     * @throws IOException
     *             异常
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static <T> Object readValue(String pContent, Object pObj) throws JsonParseException,
                                                                    JsonMappingException,
                                                                    IOException {
        return JsonUtil.mapper.readValue(pContent, (Class<T>) pObj);
    }

    /**
     * 对象转换成json字符串,不显式抛出异常
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换成对象,不显式抛出异常
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换成ArrayList,不显式抛出异常
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> fromJSONList(String json, Class<T> clazz) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazz);
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
