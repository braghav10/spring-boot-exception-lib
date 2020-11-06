package com.coop;

import lombok.Getter;

@Getter
public abstract class ServiceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	
	public ServiceException(String errroMessage) {
		super();
		//this.errorCode = errorCode;
		this.errorMessage = errroMessage;
	}
	
	public ServiceException(String errorCode, Throwable t) {
		super(t);
		this.errorCode = errorCode;
	}
	 abstract public ErrorCodeSystem getExtSystem();
	

}
