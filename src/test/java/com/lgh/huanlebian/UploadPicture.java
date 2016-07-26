package com.lgh.huanlebian;

import com.lgh.huanlebian.boot.MVCConfig;
import com.lgh.huanlebian.boot.RootConfig;
import com.lgh.huanlebian.service.StaticResourceService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * Created by lgh on 2016/7/26.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, MVCConfig.class})
@ActiveProfiles(value = {"development", "container"})
public class UploadPicture {

    @Autowired
    private StaticResourceService staticResourceService;

    @Test
    public void upload() throws IOException, URISyntaxException {
        String pictureUrl = "http://img0.pcbaby.com.cn/pcbaby/1605/18/2933304_580-0518-17.jpg";
        URL url = new URL(pictureUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();

//        IOUtils.toByteArray(inputStream);
        //进行文件读取，读取完，后面上传文件就为空了
//        String md5 = DigestUtils.md5Hex(inputStream);


        //原因md5一次读取流文件，upload又读取一次，第二次其实已经关闭
        //解决办法通过输出到ByteArrayOutputStream 再次组装InputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        String md5 = DigestUtils.md5Hex(byteArrayOutputStream.toByteArray());

        System.out.println("this md5:" + md5);
        System.out.println("outputstream length:" + byteArrayOutputStream.toByteArray().length);

        InputStream inputStream1 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        String suffix = "";
        if (pictureUrl.lastIndexOf(".") >= 0) {
            suffix = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        String newPath = StaticResourceService.news + "/" + newFileName;


        staticResourceService.uploadResource(newPath, inputStream1);

    }
}
