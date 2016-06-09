package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by lgh on 2016/5/5.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    public String getURI(String one) {
        return getURI(one, null, null);
    }

    public String getURI(String one, String two) {
        return getURI(one, two, null);
    }

    public String getURI(String one, String two, String kind) {
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
}
