package com.hajicor3.EstoqueApi.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Long id) {
		super("Recruso não encontrado no id " + id);
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
