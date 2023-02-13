package com.acq.collection.acqcollectionbook.config.aop;

import com.acq.collection.acqcollectionbook.common.log.AccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SystemAspect {
    private final AccessLogService accessLogService;


    @AfterThrowing(pointcut = "execution(* com.acq.collection.acqcollectionbook..*.*(..))", throwing = "ex")
    public void systemExceptionInfo(JoinPoint joinPoint, Throwable ex) {
        Signature signature = joinPoint.getSignature();

        String methodName = signature.getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        String stuff = signature.toString();
        String exceptionInfo = ex.toString();

        StringBuilder exceptionBuff;
        exceptionBuff = new StringBuilder();
        exceptionBuff.append("System Exception >>> METHOD : ").append(methodName).append('\n');
        exceptionBuff.append("System Exception >>> PARAMS : ").append(arguments).append('\n');
        exceptionBuff.append("System Exception >>> STUFF : ").append(stuff).append('\n');
        exceptionBuff.append("System Exception >>> ERROR INFO : ").append(exceptionInfo);

        log.error(exceptionBuff.toString());
    }

    @After("execution(* com.acq.collection.acqcollectionbook..*Controller.*(..)) && !execution(* com.acq.collection.acqcollectionbook.homepage.collection.CollectionController.*(..))")
    public void systemAccessLog(JoinPoint joinPoint) {

        Signature signature = joinPoint.getSignature();

        String methodName = signature.getName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if("true".equals(request.getHeader("ajax-request"))) return;
        this.accessLogService.setUserAccessLog(request, methodName);
    }
}
