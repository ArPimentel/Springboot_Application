package com.arturPimentelApp.applicationspring.Exception;

public class CustomeFieldValidationException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4679966307323158365L;
	/**
	 * 
	 */
	
	private String fieldName;
	
	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
}