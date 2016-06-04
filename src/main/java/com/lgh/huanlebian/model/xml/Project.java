package com.lgh.huanlebian.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 具体抓取的网站项目
 */
public class Project {

    /**
     * 是否启用 false不启用 暂时不处理这个
     */
    private boolean enabled;

    /**
     * 所属分类 必要的 插入数据库时使用
     */
    private Long category;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目的目标定义
     */
    private Target target;

    /**
     * 项目的处理方式定义
     */
    private List<Process> processes;


    public boolean getEnabled() {
        return enabled;
    }

    @XmlAttribute(name = "enabled")
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getCategory() {
        return category;
    }

    @XmlAttribute(name = "category")
    public void setCategory(Long category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    @XmlElementWrapper(name = "processes")
    @XmlElement(name = "process")
    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }


}
