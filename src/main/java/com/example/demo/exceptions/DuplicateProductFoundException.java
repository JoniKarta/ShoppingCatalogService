
package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicateProductFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateProductFoundException() {
		super();
	}

	public DuplicateProductFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateProductFoundException(String message) {
		super(message);
	}

	public DuplicateProductFoundException(Throwable cause) {
		super(cause);
	}
}
