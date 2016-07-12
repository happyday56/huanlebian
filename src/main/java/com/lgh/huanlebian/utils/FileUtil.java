package com.lgh.huanlebian.utils;

import com.lgh.huanlebian.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;


/**
 * xml文件处理
 */
public class FileUtil {


    @Autowired
    private StaticResourceService staticResourceService;

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


    /**
     * 下载图片并保存到本地
     *
     * @param pictureUrl
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String downloadPicture(String pictureUrl) throws IOException, URISyntaxException {
        String suffix = "";
        if (pictureUrl.lastIndexOf(".") >= 0) {
            suffix = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        String newPath = StaticResourceService.news + "/" + newFileName;


        URL url = new URL(pictureUrl);
        URLConnection urlConnection = url.openConnection();
        staticResourceService.uploadResource(newPath, urlConnection.getInputStream());
        return staticResourceService.getResource(newPath).toString();
    }
}
