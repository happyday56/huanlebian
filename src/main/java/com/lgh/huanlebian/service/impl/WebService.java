package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.service.CommonConfigService;
import com.lgh.huanlebian.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

/**
 * Created by lgh on 2016/7/31.
 */

@Service
public class WebService {

    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    private CommonConfigService commonConfigService;

    /**
     * 处理图片不存在情况
     * @param url
     * @return
     */
    public String handlePicture(String url) {
        try {
            return staticResourceService.getResource(url).toString();
        } catch (Exception ex) {
        }

        return commonConfigService.getResourcesUri()  + "newdefault.png";
    }
}
