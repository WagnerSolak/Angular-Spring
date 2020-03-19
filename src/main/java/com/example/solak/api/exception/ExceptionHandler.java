package com.example.solak.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//ResponseEntityExceptionHandler captura exceções
@ControllerAdvice  // observa a app
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String messageForUser = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
		String messageForDevelopment = ex.getCause().toString();
		return handleExceptionInternal(ex, new Error(messageForDevelopment, messageForUser), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	public static class Error{
		private String messageForUser;
		private String messageForDevelopment;
		
		public Error(String messageForUser, String messageForDevelopment) {
			this.messageForUser = messageForUser;
			this.messageForDevelopment = messageForDevelopment;
		}
		
		public String getMessageForDevelopment() {
			return messageForDevelopment;
		}
		
		public String getMessageForUser() {
			return messageForUser;
		}
	}
}
