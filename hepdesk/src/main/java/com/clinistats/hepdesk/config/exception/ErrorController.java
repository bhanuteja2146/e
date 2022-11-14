package com.clinistats.hepdesk.config.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.clinistats.hepdesk.exception.ValidationException;
import com.clinistats.hepdesk.response.ErrorDetails;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(NoSuchElementException ex) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(code = HttpStatus.BAD_GATEWAY)
	public final ResponseEntity<ErrorDetails> handleValidationException(ValidationException ex) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), ex.getErrorCode());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorDetails errorData = new ErrorDetails(new Date(), "Malformed JSON request");
		errorData.setDebugMessage(ex.getLocalizedMessage());
		return new ResponseEntity<Object>(errorData, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>(0);
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			details.add(fieldError.getField() + " " + error.getDefaultMessage());
		}
		ErrorDetails error = new ErrorDetails(new Date(), String.join(",", details));
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<ErrorDetails> handleException(Exception ex) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
		log.error("error in processing request", ex);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
