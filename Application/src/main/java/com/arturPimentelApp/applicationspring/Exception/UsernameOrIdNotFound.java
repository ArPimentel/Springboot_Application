package com.arturPimentelApp.applicationspring.Exception;

public class UsernameOrIdNotFound extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7390972301865717104L;

	/**
	 * 
	 */
	public UsernameOrIdNotFound() {
		super("Usuario o Id no encontrado");
	}

	public UsernameOrIdNotFound(String message) {
		super(message);
	}

}