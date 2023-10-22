// package com.ficrowe.spotiFi.configuration;
//
// import jakarta.servlet.http.HttpServletRequest;
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.stereotype.Component;
//
// @Aspect
// @Configuration
// public class RequestResponseLogger {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @Pointcut("within(org.springframework.stereotype.Controller)")
//    public void controller() {}
//
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
//    public void postMapping() {}
//
//    @Around("controller() && postMapping() && args(.., @RequestBody body, request)")
//    public void logPostRequests(ProceedingJoinPoint joinPoint, Object body, HttpServletRequest
// request) throws Throwable {
//        LOGGER.info(request.toString()); // You may log request parameters here.
//        LOGGER.debug(body.toString()); // You may do some reflection here
//
//        Object result;
//        try {
//            result = joinPoint.proceed();
//            LOGGER.debug(result.toString());
//        } catch (Throwable t) {
//        }
////        return result;
//    }
//
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public void getMapping() {}
//
//    @Around("controller() && getMapping() && args(.., @PathVariable pathVariable, request)")
//    public void logGetRequests(ProceedingJoinPoint joinPoint, String pathVariable,
// HttpServletRequest request) throws Throwable {
//        LOGGER.debug(request.toString()); // You may log request parameters here.
//        LOGGER.debug(pathVariable.toString()); // You may do some reflection here
//
//        Object result;
//        try {
//            result = joinPoint.proceed();
//            LOGGER.debug(result.toString());
//        } catch (Throwable t) {
//        }
////        return result;
//    }
// }
