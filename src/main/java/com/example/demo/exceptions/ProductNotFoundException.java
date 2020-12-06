package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	public ProductNotFoundException(String message) {
		super(message);
	}
	
	public ProductNotFoundException() {}
}
