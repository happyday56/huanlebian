package com.lgh.huanlebian.model.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * 抓取目标列表过滤 直接取链接url
 * Created by Administrator on 2016/6/4.
 */
@Getter
@Setter
public class Target_Filter {

    /**
     * 如果链接没有root，只有相对地址，需添加
     */
    @XmlAttribute(name = "root")
    private String root;
    /**
     * 标签的属性 包含 id,class,name等(width，href，target也支持)
     */
    @XmlAttribute(name = "key")
    private String key;
    /**
     * 属性的值
     */
    @XmlAttribute(name = "value")
    private String value;

    /**
     * 扩展地址后缀
     */
    @XmlAttribute(name = "suffix")
    private String suffix;
}
