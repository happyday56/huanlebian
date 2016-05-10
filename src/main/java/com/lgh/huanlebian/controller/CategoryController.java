package com.lgh.huanlebian.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 分类
 * Created by Administrator on 2016/5/8.
 */
@Controller
@RequestMapping("/d")
public class CategoryController {

    private static Log log = LogFactory.getLog(CategoryController.class);

    /**
     * 分类首页
     * @param mainCategory
     * @param model
     * @return
     */
    @RequestMapping(value = "/{categoryPath}", method = RequestMethod.GET)
    public String index(@PathVariable("categoryPath") String mainCategory, Model model) {
        model.addAttribute("", "");
        return "category/index";
    }
}
