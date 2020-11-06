package com.coop.common.exception;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.coop.ErrorConstants;
import com.coop.common.vo.ErrorInfo;
import com.coop.common.vo.ServiceResponse;

@Controller
public class GlobalErrorController implements ErrorController {
	private  static final String FAIL_ERRORCODE = "520";

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	 @Autowired
	    private ErrorAttributes errorAttributes;

	@RequestMapping("/error")
	public @ResponseBody ResponseEntity<ServiceResponse> handleError(HttpServletRequest request) {

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		//Map<String, Object> map = getErrorAttributes(webRequest, false)	;
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		ServiceResponse response = new ServiceResponse<>();
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm:ss");
				// String correlationId = reqContext.get
				ErrorInfo errorInfo = ErrorInfo.builder().timeStamp(LocalDateTime.now().format(formatter))
						.correlationId(String.valueOf(request.getAttribute("correlationId")))
						.errorCode(FAIL_ERRORCODE)
						.errorMessage(exception.getMessage())
						.jn(System.getProperty("server.id")).build();

				response.setErrorInfo(errorInfo);
				return new ResponseEntity(response, HttpStatus.OK);
			}
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm:ss");
			// String correlationId = reqContext.get
			ErrorInfo errorInfo = ErrorInfo.builder().timeStamp(LocalDateTime.now().format(formatter))
					.correlationId(String.valueOf(request.getAttribute("correlationId")))
					.errorCode(ErrorConstants.FAIL_ERRORCODE).errorMessage(exception.getLocalizedMessage())
					.jn(System.getProperty("server.id")).build();
			response.setErrorInfo(errorInfo);
			return new ResponseEntity(response, HttpStatus.OK);
		}
		return new ResponseEntity(response, HttpStatus.OK);

	}
	
	private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }

}
