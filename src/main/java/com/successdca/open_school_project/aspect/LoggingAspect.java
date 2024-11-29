package com.successdca.open_school_project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Логгирование перед работой метода контроллера
    @Before("execution(* com.successdca.open_school_project.controller.TaskController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing: {}", joinPoint.getSignature().getName());
    }

    // Логгирование после работы метода контроллера
    @After("execution(* com.successdca.open_school_project.controller.TaskController.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Executed: {}", joinPoint.getSignature().getName());
    }

    // Логирование ошибок. Использование @AfterThrowing для логирования исключений в контроллере
    @AfterThrowing(pointcut = "execution(* com.successdca.open_school_project.controller.TaskController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in: {} with cause = {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    // Логирование входящих данных (например, тело запроса) в контроллере
    @Around("execution(* com.successdca.open_school_project.controller.TaskController.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("Method: {} called with args: {}", signature.getName(), Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed(); // вызов метода

        logger.info("Method: {} returned: {}", signature.getName(), result);
        return result;
    }


}
