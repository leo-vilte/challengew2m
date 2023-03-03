package com.example.w2m.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Aspect
@Configuration
public class AspectConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(com.example.w2m.annotation.LoggingTime)")
    public void Around(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();

        try {
            joinPoint.proceed();
        } catch (Throwable e) {

            var timeTaken = System.currentTimeMillis() - startTime;
            logger.info("Call to {} take {} ms.", joinPoint, timeTaken);
            throw e;
        }

        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Call to {} take {} ms.", joinPoint, timeTaken);
    }
}
