package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lgh on 2016/8/2.
 */

@Getter
@Setter
public class Paging {
    /**
     * page index
     * from 0
     */
    private Integer page;

    /**
     * page size
     */
    private Integer size;


}
