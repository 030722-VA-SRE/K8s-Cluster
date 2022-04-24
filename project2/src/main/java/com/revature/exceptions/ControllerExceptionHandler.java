package com.revature.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.micrometer.core.annotation.Counted;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Counted(value = "internalServerError.count", description = "Count of requests that return 500")
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e, 
                HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.error("Returned HTTP Status 500 due to the following exception:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}