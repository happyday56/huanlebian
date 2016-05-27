package com.lgh.huanlebian.entity;

import com.lgh.huanlebian.entity.pk.CategoryKindPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 分类种类
 * Created by lgh on 2016/5/27.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CategoryKindPK.class)
public class CategoryKind {

    @Id
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Category category;

    @Id
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Kind kind;


    @Column(length = 20)
    private String title;

}
