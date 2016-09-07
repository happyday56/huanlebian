package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.entity.Wiki;
import com.lgh.huanlebian.entity.WikiCategory;
import com.lgh.huanlebian.model.*;
import com.lgh.huanlebian.repository.NewsRepository;
import com.lgh.huanlebian.repository.WikiCategoryRepository;
import com.lgh.huanlebian.repository.WikiRepository;
import com.lgh.huanlebian.service.NewsService;
import com.lgh.huanlebian.service.StaticResourceService;
import com.lgh.huanlebian.service.URIService;
import com.lgh.huanlebian.service.WikiCategoryService;
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
    private NewsRepository newsRepository;

    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private URIService uriService;

    @Autowired
    private WikiRepository wikiRepository;

    @Autowired
    private WikiCategoryRepository wikiCategoryRepository;

    @Autowired
    private WikiCategoryService wikiCategoryService;

    @RequestMapping(value = "/news/{id}.html", method = RequestMethod.GET)
    public String getNewsDetail(@PathVariable("id") Long id, Model model) throws URISyntaxException {
        WebNewsPageModel webNewsPageModel = new WebNewsPageModel();
        News news = newsRepository.findOne(id);
        webNewsPageModel.setTitle(news.getTitle());
        webNewsPageModel.setKeywords(news.getKeywords());
        webNewsPageModel.setDescription(news.getDescription());

        webNewsPageModel.setHeadName(news.getCategory().getParent().getTitle());
        webNewsPageModel.setHeadUrl(uriService.getCategoryURI(news.getCategory().getParent().getPath()));
        webNewsPageModel.setData(newsService.toWebNewsModel(news));
        webNewsPageModel.setTopNav(newsService.getFullPath(news.getCategory(), news.getKind()));

        model.addAttribute("page", webNewsPageModel);
        return "news/detail";
    }

    @RequestMapping("/baikelist/{id}.html")
    public String category(@PathVariable(value = "id") Long id, Model model) {
        WikiCategory wikiCategory = wikiCategoryRepository.findOne(id);
        WebBaikeListPageModel webBaikeListPageModel = new WebBaikeListPageModel();
        webBaikeListPageModel.setTitle(wikiCategory.getTitle());
        webBaikeListPageModel.setKeywords(wikiCategory.getKeywords());
        webBaikeListPageModel.setDescription(wikiCategory.getDescription());

        webBaikeListPageModel.setName(wikiCategory.getTitle());

        WikiCategory wikiCategoryMain = wikiCategoryService.getMainCategory(wikiCategory);
        if (wikiCategoryMain != null) {
            webBaikeListPageModel.setCategoryTtitle(wikiCategoryMain.getTitle());
            webBaikeListPageModel.setCategoryUrl(uriService.getWikiCategoryURI(wikiCategoryMain.getPath()));
        }

        model.addAttribute("page", webBaikeListPageModel);
        return "baike/list";
    }

    @RequestMapping(value = "/baike/{id}.html", method = RequestMethod.GET)
    public String getWikiDetail(@PathVariable("id") Long id, Model model) {

        Wiki wiki = wikiRepository.findOne(id);

        WebBaikeDetailPageModel webBaikeDetailPageModel = new WebBaikeDetailPageModel();
        webBaikeDetailPageModel.setTitle(wiki.getTitle());
        webBaikeDetailPageModel.setKeywords(wiki.getKeywords());
        webBaikeDetailPageModel.setDescription(wiki.getDescription());


        model.addAttribute("page", webBaikeDetailPageModel);
        return "baike/detail";
    }
}
