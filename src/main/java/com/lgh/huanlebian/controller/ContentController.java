package com.lgh.huanlebian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 内容
 * Created by lgh on 2016/5/10.
 */
@Controller
@RequestMapping("/v")
public class ContentController {
    @RequestMapping(value = "/news/{id}.html", method = RequestMethod.GET)
    public String getNewsDetail(@PathVariable("id") Long id) {
        return "news/detail";
    }
}
