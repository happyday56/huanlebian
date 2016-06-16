package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.model.WebCategoryListModel;
import com.lgh.huanlebian.model.WebNewsModel;
import com.lgh.huanlebian.model.WebNewsPageModel;
import com.lgh.huanlebian.repository.NewsRepository;
import com.lgh.huanlebian.service.NewsService;
import com.lgh.huanlebian.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 内容
 * Created by lgh on 2016/5/10.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/v")
public class ContentController {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    StaticResourceService staticResourceService;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/news/{id}.html", method = RequestMethod.GET)
    public String getNewsDetail(@PathVariable("id") Long id, Model model) throws URISyntaxException {
        WebNewsPageModel webNewsPageModel = new WebNewsPageModel();
        News news = newsRepository.findOne(id);
        webNewsPageModel.setTitle(news.getTitle());
        webNewsPageModel.setKeywords(news.getKeywords());
        webNewsPageModel.setDescription(news.getDescription());
        
        webNewsPageModel.setData(newsService.toWebNewsModel(news));

        webNewsPageModel.setTopNav(newsService.getFullPath(news.getCategory(), news.getKind()));

        model.addAttribute("page", webNewsPageModel);
        return "news/detail";
    }
}
