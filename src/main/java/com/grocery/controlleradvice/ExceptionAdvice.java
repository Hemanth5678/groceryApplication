package com.grocery.controlleradvice;

import java.util.HashMap;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.grocery.exception.BadRequestException;
import com.grocery.exception.apierror.ApiError;


@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> alreadyRecordExistsExceptionHandler(BadRequestException e){
		
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Bad Request"+e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
	
//	@ExceptionHandler(IdNotFoundException.class)
//	public ResponseEntity<?>idNotFoundExceptionHandler(Exception e){
//		
//		HashMap<String, String> map = new HashMap<>();
//		map.put("message", "Id not found exception : "+e.getMessage());
//		return ResponseEntity.badRequest().body(map);
//	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
		apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(apiError);
		//return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError){  
		return new ResponseEntity<>(apiError,apiError.getHttpStatus());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolation() {
		return null;
	}
	
}
