package com.coop.common.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Aspect
@Order(value=1)
@Component
public class ExceptionAspect {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);
	
	
	@AfterReturning("execution(* com.coop.common.exception.GlobalExceptionAdvice.*(..))")
    public void exceptionclass() {  }
	
	@AfterReturning(pointcut="execution(* com.coop.common.exception.GlobalExceptionAdvice.*(..)) || execution(* com.coop.common.exception.GlobalErrorController.*(..))", returning="result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		Gson gson = new Gson();
		String response = gson.toJson(result);
		MDC.put("log_type", "A");
		MDC.put("apiResponse", response);
		logger.error("Response from exception aspect");
		MDC.clear();
	}

}
