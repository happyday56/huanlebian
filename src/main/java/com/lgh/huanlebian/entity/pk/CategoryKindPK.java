package com.lgh.huanlebian.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lgh on 2016/5/27.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryKindPK implements Serializable {
    private Long category;

    private Long kind;
}
