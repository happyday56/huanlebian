package com.lgh.huanlebian.service;

import javax.transaction.Transactional;

/**
 * Created by lenovo on 2015/7/10.
 */
public interface SpliderService {

    @Transactional
    void start() throws Exception;

    @Transactional
    void handleSlide();
}
