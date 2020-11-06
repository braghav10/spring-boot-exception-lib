package com.coop;

import lombok.Getter;

@Getter
public enum ErrorCodeSystem {

	DB("4001"),
	DROOLS("4002"),
	TOKEN_EXPIRED("4003");
	
	
	private String errorCode;

	private ErrorCodeSystem(String errorCode) {
		this.errorCode = errorCode;
	}

}
