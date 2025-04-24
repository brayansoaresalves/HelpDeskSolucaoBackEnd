package com.brayan.helpDesk.domain.exceptions;

public class DataIntegroVioladoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegroVioladoException(String message) {
		super(message);
	}

	public DataIntegroVioladoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	

}
