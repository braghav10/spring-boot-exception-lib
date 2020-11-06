package com.coop;

public class DroolsException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DroolsException(String errroMessage) {
		super(errroMessage);
	}

	@Override
	public ErrorCodeSystem getExtSystem() {
		return ErrorCodeSystem.DROOLS;
	}

}
