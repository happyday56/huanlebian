package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.*;
import com.lgh.huanlebian.model.xml.*;
import com.lgh.huanlebian.model.xml.Process;
import com.lgh.huanlebian.repository.*;
import com.lgh.huanlebian.service.SpliderService;
import com.lgh.huanlebian.utils.FileUtil;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2015/7/10.
 */

@Service
public class SpliderServiceImpl implements SpliderService {

    private static Log log = LogFactory.getLog(SpliderServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsRepository newsRepository;


    @Autowired
    NewsFilterRepository newsFilterRepository;

    @Autowired
    KindRepository kindRepository;

    @Autowired
    CategoryKindRepository categoryKindRepository;

    @Transactional
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
    public void start() throws Exception {

        Date uploadTime = new Date(System.currentTimeMillis());

        File file = new File(this.getClass().getResource("/").getPath() + "targets.xml");
//        log.info(file.exists());
        if (!file.exists()) {
            log.error("not find targets.xml");
            return;
        }
        //读取项目配置的XML文件
        ProjectRoot root = FileUtil.ConvertToBean(file, ProjectRoot.class);
        Integer totalCount = 0;
        Integer errorCount = 0;
        Integer repeatCount = 0;

//        List<Category> categories = categoryRepository.findAll();

        List<News> blogSpliders = new ArrayList<>();
        List<NewsFilter> blogFilters = new ArrayList<>();
        //用于过滤
        List<String> listTitles = new ArrayList<>();
        log.info("config file loading finished,start spridering");
        List<Project> listProject = root.getProjects();
        for (Project project : listProject) {
            Boolean enabled = project.getEnabled();
            String categoryPath = project.getCategory();
            String kindPath = project.getKind();
            String name = project.getName();

            Category category = categoryRepository.findByPath(categoryPath);
            Kind kind = kindRepository.findByPath(kindPath);

//            CategoryKind categoryKind = categoryKindRepository.findByCategoryAndKind(category, kind);
//            if (categoryKind == null) {
//                categoryKind = new CategoryKind();
//                categoryKind.setCategory(category);
//                categoryKind.setKind(kind);
//                categoryKind.setTitle(name);
//                categoryKindRepository.save(categoryKind);
//            }

            String projectName = project.getName();

            //判断是否进行抓取
            if (enabled) {

                log.info(projectName + " start doTarget....");
                // 获取项目处理目标，分析后，返回需要处理的具体页面
                Target target = project.getTarget();
                List<String> listUrl = null;
                if (target == null)
                    log.error("xml have no target");
                else {
                    try {
                        listUrl = doTarget(target);
                    } catch (Exception exp) {
                        log.error("doTarget error " + exp.getMessage());
                    }
                }

                totalCount += listUrl.size();

                log.info("doTarget finished,url length:" + listUrl.size());

                log.info("start web page");
                List<Process> processes = project.getProcesses();
                for (String doFinalUrl : listUrl) {
                    try {
                        News news = doProcesses(doFinalUrl, processes);
                        //内容存入list中 标题不能重复
                        if (news != null
                                && !StringUtils.isEmpty(news.getTitle())
                                && !StringUtils.isEmpty(news.getContent())
                                && !listTitles.contains(news.getTitle())
                                && newsFilterRepository.findOne(news.getTitle()) == null) {
                            //设置时间间隔

                            uploadTime = new Date(uploadTime.getTime() + 60 * 1000);
                            news.setCategory(category);
                            news.setKind(kind);
                            news.setUploadTime(uploadTime);
                            news.setViews(0L);
                            blogSpliders.add(news);

                            NewsFilter blogFilter = new NewsFilter(news.getTitle());
                            blogFilters.add(blogFilter);

                            listTitles.add(news.getTitle());
                        } else {
                            repeatCount++;
                        }

//                        if (blogSplider != null) {
//                            newsRepository.updateContentByTitle(blogSplider.getContent(),blogSplider.getTitle());
//
//                        }
                    } catch (Exception exp) {
                        log.error("web url:" + listUrl + exp.getMessage());
                        errorCount++;
                    }
                }
                log.info("project " + projectName + " finined");
            } else {
                log.error("project " + projectName + " no open,so no do");
            }
        }

        for (News news : blogSpliders) {
            log.info(news.getTitle());
        }

        //插入数据
        newsRepository.save(blogSpliders);
        newsFilterRepository.save(blogFilters);


        log.info("total：" + totalCount + " success：" + blogSpliders.size() + " error：" + errorCount + " repeat：" + repeatCount);

    }

    /**
     * 处理目标， 获取具体网页列表
     *
     * @param target
     * @return
     * @throws IOException
     */
    private List<String> doTarget(Target target) throws IOException {
        List<String> result = new ArrayList();
        List<String> listSourceUrl = new ArrayList();
        //1.多个具体网址
        List<Single_Url> multi_url = target.getMulti_url();
        if (multi_url != null) {
            for (Single_Url single_url : multi_url) {
                listSourceUrl.add(single_url.getHref());
            }
        }


        //2.处理通配符网址
        Wildcard_Url wildcard_Url = target.getWildcard_url();
        if (wildcard_Url != null) {
            String target_url = wildcard_Url.getHref();

            Integer change = 0;
            Integer start = wildcard_Url.getStartpos();
            Integer end = wildcard_Url.getEndpos();
            //如果开始比结束大则互换
            if (start > end) {
                change = end;
                end = start;
                start = change;
            }

            //获取要解析的网址列表
            for (int i = start; i <= end; i++) {
                String url = target_url.replace("(*)", String.valueOf(i));
                listSourceUrl.add(url);
                // Pattern p = Pattern.compile("(*)");
                // Matcher matcher = p.matcher(target_url);
                // String url = matcher.replaceAll(String.valueOf(i));
            }
        }

        // 3.根据规则进行解析上面提供的网址，获取具体网页地址列表
        Target_Regex target_regex = target.getTarget_regex();
        if (target_regex != null) {
            String root = !StringUtils.isEmpty(target_regex.getRoot()) ? target_regex.getRoot().toLowerCase() : "";

            for (String sourceurl : listSourceUrl) {
                // 下载页面
                URL url = new URL(sourceurl);
                Source source = new Source(url);

                // 获取页面内容
                String strBody = source.getSource().toString();
//                List<Element> body = source
//                        .getAllElements(HTMLElementName.BODY);
//                for (Element e : body) {
//                    strBody = e.getContent().toString();
//                }

                Pattern p = Pattern.compile(target_regex.getValue());
                Matcher matcher = p.matcher(strBody);
                while (matcher.find()) {
                    String m = matcher.group(1);
                    result.add(root + m);
                }
            }
        }

        //4.规则为空的情况下直接采用过滤取URL
        Target_Filter target_filter = target.getTarget_filter();
        if (target_filter != null) {
            String root = !StringUtils.isEmpty(target_filter.getRoot()) ? target_filter.getRoot().toLowerCase() : "";
            String suffix = !StringUtils.isEmpty(target_filter.getSuffix()) ? target_filter.getSuffix() : "";
            for (String sourceurl : listSourceUrl) {
                // 下载页面
                URL url = new URL(sourceurl);
                Source source = new Source(url);
                List<Element> elements = source.getAllElements(target_filter.getKey(), target_filter.getValue(), false);
                for (Element element : elements) {
                    Pattern p = Pattern.compile("href\\=\\\"(.+?)\\\"");
                    Matcher matcher = p.matcher(element.getContent().toString());
                    while (matcher.find()) {
                        String m = matcher.group(1);
                        if (m.lastIndexOf(".") >= 0) {
                            m = m.substring(0, m.lastIndexOf(".")) + suffix + m.substring(m.lastIndexOf("."));
                        }
                        result.add(root + m);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 处理单个最终页面
     *
     * @param doFinalUrl
     * @param listProcess
     * @throws IOException
     */
    private News doProcesses(String doFinalUrl, List<Process> listProcess) throws IOException {
        News result = new News();

        URL url = new URL(doFinalUrl);
        Source source = new Source(url);

//        List<Element> elements = source.getAllElements(HTMLElementName.HTML);
//        for (Element element : elements) {
//            sourceHtml = element.getContent().toString();
//        }

        //处理description和keywords
        // 获取关键字
        List<StartTag> keywords = source.getAllStartTags("name", "keywords", false);
        for (StartTag tag : keywords) {
            result.setKeywords(tag.getAttributeValue("content"));
        }
        // 获取描述
        List<StartTag> description = source.getAllStartTags("name", "description", false);
        for (StartTag tag : description) {
            result.setDescription(tag.getAttributeValue("content"));
        }

        for (Process process : listProcess) {
            doProcess(source, process, result);
        }

        return result;
    }

    /**
     * 处理单个process 一个process对应的一个字段 （标题 内容 图片）
     * 步骤:
     * 1.以正则方式获取内容
     * 2.以标签方式获取
     * 3.对以1或2方式获取的内容进行过滤
     * 4.把处理内容保存到实体中
     *
     * @param source
     * @param process
     * @param result
     */
    private void doProcess(Source source, Process process, News result) {
        String sourceHtml = source.getSource().toString();
        //处理后的内容
        String processContent = "";

        //1.以正则方式获取内容
        String regex_filter = process.getProcess_regex_filter();
        if (!StringUtils.isEmpty(regex_filter)) {
            Pattern p = Pattern.compile(regex_filter);
            Matcher matcher = p.matcher(sourceHtml);//有缓存从缓存处理
            if (matcher.find()) {
                processContent = matcher.group(1);
            }
        }

        //2.以标签方式获取
        Process_Tag_Filter process_tag_filter = process.getProcess_tag_filter();
        if (process_tag_filter != null) {
            String key = process_tag_filter.getKey();
            String value = process_tag_filter.getValue();

            //不管id class name 统一采用属性方式获取
            List<Element> listElement = source.getAllElements(key, value, false);
            Integer pos = process_tag_filter.getPos();
            Integer postCount = 1;
            for (Element element : listElement) {
                if (pos == postCount) {
                    processContent = element.getContent().toString();
                    //进行获取子层级处理(5层)
                    Integer childrenLevel = process_tag_filter.getChildrenLevel();
                    if (!StringUtils.isEmpty(processContent) && childrenLevel != null && childrenLevel > 0) {
                        Source source1 = new Source(processContent);
                        if (childrenLevel == 1)
                            processContent = source1.getChildElements().get(0).getContent().toString();
                        else if (childrenLevel == 2)
                            processContent = source1.getChildElements().get(0).getChildElements().get(0).getContent().toString();
                        else if (childrenLevel == 3)
                            processContent = source1.getChildElements().get(0).getChildElements().get(0).getChildElements().get(0).getContent().toString();
                        else if (childrenLevel == 4)
                            processContent = source1.getChildElements().get(0).getChildElements().get(0).getChildElements().get(0).getChildElements().get(0).getContent().toString();
                        else if (childrenLevel == 5)
                            processContent = source1.getChildElements().get(0).getChildElements().get(0).getChildElements().get(0).getChildElements().get(0).getChildElements().get(0).getContent().toString();

                    }
                    break;
                }
                postCount++;
            }
        }

        //3.对以1或2方式获取的内容进行过滤
        if (process.getProcess_clean() != null)
            processContent = doProcessClean(processContent, process.getProcess_clean());

        //4.把处理内容保存到实体中（标题 内容 图片）
        String field = process.getField();
        if (field.equals("title"))
            result.setTitle(processContent);
        else if (field.equals("content")) {
            result.setContent(processContent);

            //处理内容的同时处理简介summary
            if (!StringUtils.isEmpty(processContent)) {
                Source sourceSummary = new Source(processContent);
                String processSummary = sourceSummary.getTextExtractor().toString();
                if (processSummary.length() > 150) processSummary = processSummary.substring(0, 150);
                if (processSummary.lastIndexOf("。") > 0) {
                    processSummary = processSummary.substring(0, processSummary.lastIndexOf("。") + 1);
                } else if (processSummary.lastIndexOf("！") > 0) {
                    processSummary = processSummary.substring(0, processSummary.lastIndexOf("！") + 1);
                }
                if (StringUtils.isEmpty(processSummary)) processSummary = result.getTitle();
                result.setSummary(processSummary);
            }
        } else if (field.equals("pictureUrl")) {
            //                //此内容会被下面的pictureUrl覆盖
//                StartTag startTag = sourceSummary.getFirstStartTag("img");
//                if (startTag != null) {
//                    String pictureUrl = startTag.getAttributeValue("src");
//                    result.setPictureUrl(pictureUrl != null && pictureUrl.length() <= 200 ? pictureUrl : "");
//                } else {
//                    result.setPictureUrl("");
//                }
            result.setPictureUrl(processContent); //todo 图片需要下载
        }
    }

    /**
     * 对内容进行清理
     * 只处理一级的标签 TODO: 2016/6/9  太单一
     *
     * @param content
     * @param clean_tags
     */
    private String doProcessClean(String content, List<Clean_Tag> clean_tags) {
        if (clean_tags != null && clean_tags.size() > 0) {
            Source source = new Source(content);
            StringBuilder strB = new StringBuilder();
            for (Element element : source.getChildElements()) {
                //默认不清理
                boolean isClearTag = false;
                for (Clean_Tag clean_tag : clean_tags) {
                    if ("attribute".equals(clean_tag.getType())) {
                        if (element.getAttributes().getValue(clean_tag.getKey()) != null &&
                                element.getAttributes().getValue(clean_tag.getKey()).equals(clean_tag.getValue())) {
                            isClearTag = true;
                            break;
                        }
                    }

                    if ("tag".equals(clean_tag.getType()) && element.getName().equals(clean_tag.getValue())) {
                        isClearTag = true;
                        break;
                    }
                }

                //不清理才添加到返回数据中
                if (!isClearTag && !element.isEmpty()) {
                    strB.append(element.toString());
                }
            }
            content = strB.toString();
        }
        return content;
    }


//    public void doPicture() {
//        List<News> blogs = newsRepository.findAll();
//        for (News news : blogs) {
//            String pictureUrl = "";
//            String content = news.getContent();
//
//            Source source = new Source(content);
//            StartTag startTag = source.getFirstStartTag("img");
//            if (startTag != null) {
//                pictureUrl = startTag.getAttributeValue("src");
//            }
//
//            newsRepository.updatePictureUrlById(news.getId(), pictureUrl);
//        }
//    }
}
