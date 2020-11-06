package com.coop.common.exception;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coop.DroolsException;
import com.coop.ErrorConstants;
import com.coop.ServiceException;
import com.coop.common.vo.ErrorInfo;
import com.coop.common.vo.ServiceResponse;



@ControllerAdvice
@SuppressWarnings("rawtypes")
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {


	@ExceptionHandler(ServiceException.class)
	public @ResponseBody ResponseEntity<ServiceResponse> handleDemoException(HttpServletRequest request,
			ServiceException ex) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm:ss");
		// String correlationId = reqContext.get
		ErrorInfo errorInfo = ErrorInfo.builder().timeStamp(LocalDateTime.now().format(formatter))
				.correlationId(String.valueOf(request.getAttribute("correlationId")))
				.errorCode(ErrorConstants.FAIL_ERRORCODE)
				.errorMessage(ex.getErrorMessage())
				.jn(System.getProperty("server.id")).build();
		ServiceResponse response = new ServiceResponse<>();
		response.setErrorInfo(errorInfo);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(DroolsException.class)
	public @ResponseBody ResponseEntity<ServiceResponse> handleDroolsExtSystemException(HttpServletRequest request,
			DroolsException ex) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm:ss");
		// String correlationId = reqContext.get
		
		ErrorInfo errorInfo = ErrorInfo.builder().timeStamp(LocalDateTime.now().format(formatter))
				.correlationId(String.valueOf(request.getAttribute("correlationId")))
				.errorCode(ex.getExtSystem().getErrorCode())
				.errorMessage(ex.getErrorMessage())
				.jn(System.getProperty("server.id")).build();
		ServiceResponse response = new ServiceResponse<>();
		response.setErrorInfo(errorInfo);
		return new ResponseEntity(response, HttpStatus.OK);
	}


	@Override
    protected @ResponseBody ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm:ss");
		// String correlationId = reqContext.get
		ErrorInfo errorInfo = ErrorInfo.builder().timeStamp(LocalDateTime.now().format(formatter))
				//.correlationId(headers.get("correlationId"))).errorCode(ex.getErrorcode())
				.errorMessage(ex.getLocalizedMessage())
				.jn(System.getProperty("server.id")).build();
		ServiceResponse response = new ServiceResponse<>();
		response.setErrorInfo(errorInfo);
		return new ResponseEntity(response, HttpStatus.OK);
		
	}
	
}
