package com.avalon.shiro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.codec.Base64;

import com.avalon.common.util.StringUtil;

public class SerializationUtils {
    private static final Logger logger = LogManager.getLogger(SerializationUtils.class);

    public static Object deserialize(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        if (StringUtil.isBlank(str)) {
            return null;
        }
        try {
            bis = new ByteArrayInputStream(Base64.decode(str));
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("反序列化失败！", e);
            return null;
        } finally {
            try {
                ois.close();
                bis.close();
            } catch (IOException e) {
                logger.error("反序列化失败！", e);
            }

        }
    }

    public static String serialize(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        if (obj == null) {
            return "";
        }
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            logger.error("序列化失败！", e);
            return "";
        } finally {
            try {
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}