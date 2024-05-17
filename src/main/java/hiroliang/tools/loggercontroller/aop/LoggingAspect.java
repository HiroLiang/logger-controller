package hiroliang.tools.loggercontroller.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* org.slf4j.Logger..*(..))")
    public void pointCut() {}

    @Before(value = "pointCut()")
    public void logWithDefaultMarker(final JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("正在調用 " + className + " - " + methodName + " 方法");
    }

}
