package com.lgh.huanlebian.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 * 耗时
 * Created by lgh on 2016/5/20.
 */
@Aspect
public class TakeTiming {

    Log log = LogFactory.getLog(TakeTiming.class);


    //    @PostConstruct
//    public void init(){
//        log.info("taketiming enter..............");
//    }

    @Pointcut("within(com.lgh.huanlebian.controller..*)")
    public void point() {

    }

    @Around("point()")
    public Object work(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date beginTime = new Date();
        Object object = proceedingJoinPoint.proceed();
        Date endTime = new Date();
        log.info(proceedingJoinPoint.getTarget().getClass().toString() + " use " + (endTime.getTime() - beginTime.getTime()) + " milliseconds");
        return object;
    }
}
