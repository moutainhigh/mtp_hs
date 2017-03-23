package com.core.util;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerAop {
	private static final Logger LOGGER = Logger.getLogger(LoggerAop.class);
	
	@Pointcut("execution(* com.muchinfo.mtp.bankservice.service..*.*(..))")
	public void pointcut(){
	}

	@Around("pointcut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long time = System.currentTimeMillis();
		LOGGER.info("开始执行方法:"+pjp.getTarget().getClass().getName()+"."+pjp.getSignature().getName());
		
		Object retVal = pjp.proceed();
		
		time = System.currentTimeMillis() - time;
		LOGGER.info("---------------------------------------------------------");
		LOGGER.info("结束执行方法:"+pjp.getTarget().getClass().getName()+"."+pjp.getSignature().getName()+ "运行时间: " + time + " ms");

		return retVal;
	}

//	@AfterThrowing(value="pointcut()", throwing = "e")
//	public void afterThrowing(Throwable e){
//		LOGGER.error("Aop中发现异常:" + ExceptionUtils.getStackTrace(e));
//	}
}