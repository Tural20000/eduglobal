package com.eduglobal.backend.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.exception.MyValidationException;

import lombok.Data;

@RestControllerAdvice
public class MyGlobalHandler {
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String MyHandlerException(MyNotFoundException ex) {
		return ex.getMessage();

	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<Field> handlerMyValidationException(MyValidationException ex) {
		BindingResult br = ex.getBr();

		List<FieldError> fieldErrors = br.getFieldErrors();
		List<Field> errors = new ArrayList<Field>();
		for (FieldError fe : fieldErrors) {
			Field f = new Field();
			f.setField(fe.getField());
			f.setMessage(fe.getDefaultMessage());
			errors.add(f);
		}
		return errors;

	}

}

@Data
class Field {
	private String field;
	private String message;
}