package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/8/2.
 */

@Getter
@Setter
public class Paging {

    /**
     * page index
     */
    private Integer pageNumber;

    /**
     * page size
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * page url
     * {number} replace
     */
    private String url;
}
