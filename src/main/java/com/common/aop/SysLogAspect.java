package com.common.aop;

import com.alibaba.fastjson.JSON;
import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.mapper.SysLogMapper;
import com.pojo.SysLog;
import com.pojo.User;
import com.utils.SessionValues;
import com.utils.UserAgentUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统日志：切面处理类
 * @author Fang Ruichuan
 * @date 2021/12/2 10:32
 */
@Aspect
@Component
@SuppressWarnings("all")
public class SysLogAspect {
    private final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    private SysLogMapper sysLogMapper;

    // 定义切点
    @Pointcut("@annotation(com.common.anotation.OperationLog)")
    public void logPointCut() {

    }

    // 环绕通知
    @Around("logPointCut()")
    public Object saveSysLog(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("环绕通知开始");
        Date requestTime = new Date();
        long start = System.currentTimeMillis();
        SysLog sysLog = new SysLog();
        // 从切面织入点处通过反射机制获取织入点的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获得切入点所在的方法
        Method method = signature.getMethod();
        OperationLog log = method.getAnnotation(OperationLog.class);
        if (Objects.nonNull(log)) {
            sysLog.setMessage(log.message());
            sysLog.setOperation(log.operation());
        }
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        Object[] args = proceedingJoinPoint.getArgs();
        //序列化时过滤掉request和response
        List<Object> logArgs = Arrays.stream(args).filter(arg ->
                        (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        String params = JSON.toJSONString(logArgs);
        sysLog.setParams(params);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        sysLog.setRequestTime(requestTime);
        sysLog.setTotalTime(System.currentTimeMillis() - start);
        User loginAdmin = (User) request.getSession().getAttribute(SessionValues.LOGIN_ADMIN);

        sysLog.setUserId(Objects.nonNull(loginAdmin) ? loginAdmin.getId() : null);

        logger.info("=================================================================================");
        if (Objects.nonNull(loginAdmin)) {
            logger.info("操作用户ID: {}", loginAdmin.getId());
        } else {
            logger.info("正在执行登录操作");
        }
        logger.info("URL = {}", request.getRequestURL().toString());
        logger.info("HTTP_METHOD = {}", request.getMethod());
        logger.info("IP = {}", UserAgentUtil.getIpAddress(request));
        logger.info("args = {}", params);
        logger.info("==================================================================================");
        sysLog.setIp(UserAgentUtil.getIpAddress(request));
        sysLog.setRequestUrl(request.getRequestURL().toString());
        sysLog.setRequestWay(request.getMethod());
        sysLogMapper.save(sysLog);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            logger.info(proceed.toString());
            logger.info("环绕通知结束");
            return proceed;
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        return R.fail("系统异常");
    }
}
