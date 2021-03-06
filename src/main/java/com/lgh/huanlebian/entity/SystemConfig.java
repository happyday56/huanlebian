package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 系统配置
 * DatabaseVersion 数据库版本
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfig {
    @Id
    @Column(length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String valueForCode;

    @Column(length = 100)
    private String remark;
}