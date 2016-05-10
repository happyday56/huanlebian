package com.lgh.huanlebian.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/5/8.
 */
@Controller
public class IndexController {

    private static Log log = LogFactory.getLog(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        log.info("index");
        return "category/index";
    }
}
