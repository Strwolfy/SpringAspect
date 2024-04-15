package com.mikhail.timetracking.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTrackingAspect {

    private static final Logger logger = LoggerFactory.getLogger(TimeTrackingAspect.class);

    @Pointcut("@annotation(com.mikhail.timetracking.annotation.TrackTime)")
    public void trackTimeAnnotation() {}

    @Pointcut("@annotation(com.mikhail.timetracking.annotation.TrackAsyncTime)")
    public void trackAsyncTimeAnnotation() {}

    @Around("trackTimeAnnotation()")
    public Object trackTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return processJoinPoint(joinPoint);
    }

    @Around("trackAsyncTimeAnnotation()")
    public Object trackAsyncTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return processJoinPoint(joinPoint);
    }

    private Object processJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            logger.error("Exception in {}.{} with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), ex.getCause() != null ? ex.getCause() : "NULL");
            throw ex;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("Execution time of {}.{} : {} ms", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), (endTime - startTime));
        }
    }
}
