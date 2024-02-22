package com.seikim.httpclient.stopwatch;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class StopWatchAspect {
    private final ThreadLocal<StopWatch> stopWatchThreadLocal;

    protected StopWatchAspect() {
        stopWatchThreadLocal = new ThreadLocal<>();
    }

    @Pointcut("@annotation(com.seikim.httpclient.stopwatch.EnableStopWatch)")
    protected void targetAspect() {
    }

    @Around("targetAspect()")
    public Object timer(ProceedingJoinPoint joinPoint) {
        Object proceed = null;
        stopWatchThreadLocal.set(new StopWatch());
        StopWatch stopWatch = stopWatchThreadLocal.get();
        stopWatch.start();
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("{}", e.getMessage());
        }
        stopWatch.stop();
        log.info("Time : {}ms", TimeUnit.SECONDS.toSeconds(stopWatch.getTotalTimeMillis()));
        stopWatchThreadLocal.remove();
        return proceed;
    }
}
