package com.lgh.huanlebian.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;


/**
 * xml文件处理
 */
public class FileUtil {

    /**
     * xml to bean
     *
     * @param xml
     * @param cls
     * @param <T>
     * @return
     * @throws JAXBException
     */
    public static <T> T ConvertToBean(String xml, Class<T> cls) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T t = (T) unmarshaller.unmarshal(new StringReader(xml));
        return t;
    }

    /**
     * xml to bean
     *
     * @param file
     * @param cls
     * @param <T>
     * @return
     * @throws JAXBException
     */
    public static <T> T ConvertToBean(File file, Class<T> cls) throws Exception {
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T t = (T) unmarshaller.unmarshal(file);

        return t;
    }
}
