package com.lgh.huanlebian;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lgh on 2016/7/11.
 */
public class BaseTest {
    private static Log log = LogFactory.getLog(BaseTest.class);

    @Test
    public void removeHref() {
        String content = "<p>1多的地方地方？<a class=\"cmsLink\" href=\"http://baike.pcbaby.com.cn/xinshenger.html\" target=\"_blank\">新生儿</a>,2容易吐奶</p><p style=\"text-align: center\"><a href=\"http://yuer.pcbaby.com.cn/203/2033589_all.html\"><img alt=\"宝宝\" src=\"http://www1.pcbaby.com.cn/images/blank.gif\" #src=\"http://img0.pcbaby.com.cn/pcbaby/1412/29/2033589_580-5.jpg\"  style=\"border-bottom: #c6c6c6 1px solid; border-left: #c6c6c6 1px solid; border-top: #c6c6c6 1px solid; border-right: #c6c6c6 1px solid\" title=\"宝宝\" /></a></p><p style=\"text-align: center\">图片来源于华盖</p>";
    String regex = "<a.*?href=\"(?<url>.+?)\".*?>(?<content>.+?)</a>";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String url = matcher.group("url");
            String t = matcher.group("content");
            log.info("url:" + url);
            log.info("content:" + t);
            String replaced =  matcher.replaceAll(t);
            log.info("replaced:" + replaced);

        }
        content.replaceAll(regex,"bbb");

    }
}
