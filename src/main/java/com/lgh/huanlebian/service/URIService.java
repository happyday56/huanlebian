package com.lgh.huanlebian.service;

/**
 * Created by Administrator on 2016/6/10.
 */
public interface URIService {
    String getCategoryURI(String one);

    String getCategoryURI(String one, String two);

    String getCategoryURI(String one, String two, String kind);

    String getNewURI(Long id);
}
