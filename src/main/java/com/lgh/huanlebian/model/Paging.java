package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    private List<Integer> pages;

    public List<Integer> getPages() {
        List<Integer> result = new ArrayList<>();
        Integer current = pageNumber - 3;
        while (current <= pageNumber + 3) {
            if (current >= 0 && current < totalPage)
                result.add(current);
            current++;
        }
        return result;
    }
}
