package com.brayan.helpDesk.domain.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.brayan.helpDesk.resources.exceptions.StandardError;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError() {
		super();

	}

	public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}
	
	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	public void addErrors(String fieldName, String mensagem) {
		this.errors.add(new FieldMessage(fieldName, mensagem));
	}
	
	

}
