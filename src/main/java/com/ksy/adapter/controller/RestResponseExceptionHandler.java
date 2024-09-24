package com.ksy.adapter.controller;

import com.ksy.adapter.model.ErrorModel;
import com.ksy.adapter.utils.ResponseFactory;
import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс RestResponseExceptionHandler
 */
@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseExceptionHandler {

    private final ResponseFactory responseFactory;

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorModel> handleNoResourceFoundException(NoResourceFoundException e) {
        return responseFactory.getException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorModel> handleUnexpectedTypeException(UnexpectedTypeException e) {
        return responseFactory.getException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> objectErrors = result.getAllErrors();
        ErrorModel err = new ErrorModel();
        objectErrors.forEach(objectError -> {
            if (objectError instanceof FieldError fr) {
                err.addMessage(fr.getField() + ": " + fr.getDefaultMessage());
            } else {
                err.addMessage(objectError.getObjectName() + ": " + objectError.getDefaultMessage());
            }
        });
        return responseFactory.getException(ex, err.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorModel> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        List<String> detailMessages = new ArrayList<>();
        e.getBeanResults()
                .forEach(result -> result.getFieldErrors()
                        .forEach((action) -> detailMessages.add(action.getField() + ": " + action.getDefaultMessage())));
        return responseFactory.getException(e, detailMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorModel> handleEmptyMessageException(IllegalArgumentException e) {
        return responseFactory.getException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorModel> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return responseFactory.getException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorModel> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return responseFactory.getException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorModel> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return responseFactory.getException(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleDefaultException(Exception e) {
        return responseFactory.getException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
