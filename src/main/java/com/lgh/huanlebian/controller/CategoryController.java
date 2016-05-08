package com.lgh.huanlebian.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/5/8.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    private static Log log = LogFactory.getLog(CategoryController.class);

    @RequestMapping(value = "/{mainCategory}", method = RequestMethod.GET)
    public String index(@PathVariable("mainCategory") String mainCategory) {
        log.info(mainCategory);

        return "category/index";
    }
}
