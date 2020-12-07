package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicateCategoryFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateCategoryFoundException() {
		super();
	}

	public DuplicateCategoryFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateCategoryFoundException(String message) {
		super(message);
	}

	public DuplicateCategoryFoundException(Throwable cause) {
		super(cause);
	}
}
