package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.service.URIService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2016/6/10.
 */
@Service
public class URIServiceImpl implements URIService {
    public String getCategoryURI(String one) {
        return getCategoryURI(one, null, null);
    }

    public String getCategoryURI(String one, String two) {
        return getCategoryURI(one, two, null);
    }

    public String getCategoryURI(String one, String two, String kind) {
        if (!StringUtils.isEmpty(one)) {
            if (StringUtils.isEmpty(two)) {
                return "/d/" + one;
            } else {
                if (StringUtils.isEmpty(kind)) {
                    return "/d/" + one + "/" + two;
                } else {
                    return "/d/" + one + "/" + two + "/" + kind;
                }
            }

        }
        return "/d";
    }

    @Override
    public String getNewURI(Long id) {
        return "/v/news/" + id + ".html";
    }
}
