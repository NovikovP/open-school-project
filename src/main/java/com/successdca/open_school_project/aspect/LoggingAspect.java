package com.successdca.open_school_project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
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
    public Object logAround(ProceedingJoinPoint joinPoint)  {
        Object result = null;

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            logger.info("Method: {} called with args: {}", signature.getName(), Arrays.toString(joinPoint.getArgs()));

            result = joinPoint.proceed(); // вызов метода

            logger.info("Method: {} returned: {}", signature.getName(), result);
        } catch (Throwable e) {
            logger.info("Method throws exeption {}", e.getMessage());
        }

        return result;
    }

    // Логгирование перед работой метода c указанной аннотацией
    @Before("@annotation(com.successdca.open_school_project.aspect.annotation.LogAddedTask)")
    public void logAdedTask(JoinPoint joinPoint) {
        logger.info("aspect execution via annotation in : {}", joinPoint.getSignature().getName());
    }

    // Логгирование перед работой метода c указанной аннотацией
    @AfterReturning("@annotation(com.successdca.open_school_project.aspect.annotation.LogDeletedTask)")
    public void logDeletedTask(JoinPoint joinPoint) {
        logger.info("AfterReturning, aspect execution via annotation in : {}", joinPoint.getSignature().getName());
    }

    // Измерение времени выполнения метода с аннотацией через аспект
    @Around("@annotation(com.successdca.open_school_project.aspect.annotation.CalculatingExecutionTimeOfMethod)")
    public Object logCalculateExecution(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        long startTimeMillis = System.currentTimeMillis();

        Object result = null; // вызов метода
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        logger.info("Method: {} execution : {} millisecond", signature.getName(), System.currentTimeMillis() - startTimeMillis);

        return result;
    }

// TODO еще немного экспериментов с аспектами

    @Before("@annotation(com.successdca.open_school_project.aspect.annotation.LogBefore)")
    public void logBeforeNew(JoinPoint joinPoint) {
        log.info("Method {} executed", joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "@annotation(com.successdca.open_school_project.aspect.annotation.LogAfterThrowing)", throwing = "exception")
    public void logAfterThrowingNew(JoinPoint joinPoint, Throwable exception) {
        log.error("Method {} threw an exception: {}",
                joinPoint.getSignature().getName(), exception.getMessage());
    }

    @AfterReturning(value = "@annotation(com.successdca.open_school_project.aspect.annotation.LogAfterReturning)", returning = "result")
    public void logAfterReturningNew(JoinPoint joinPoint, Object result) {
        log.info("Method {} executed successfully. Result: {}", joinPoint.getSignature().getName(), result);
    }

    @Around("@annotation(com.successdca.open_school_project.aspect.annotation.LogAround)")
    public Object logAroundNew(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Execution of method '{}' started. Parameters: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();
            log.info("Execution of method '{}' completed successfully. Result: {}",
                    joinPoint.getSignature().getName(),
                    result);
        } catch (Throwable ex) {
            log.error("Execution of method '{}' failed. Exception: {} - {}",
                    joinPoint.getSignature().getName(),
                    ex.getClass().getSimpleName(),
                    ex.getMessage());
            throw ex;
        }
        return result;
    }
}
