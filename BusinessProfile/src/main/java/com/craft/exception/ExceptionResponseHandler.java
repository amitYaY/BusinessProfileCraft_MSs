package com.craft.exception;

import com.craft.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<Response> handleInvalidInputException(InvalidInputException ex) {
        log.error("Invalid Input Exception: ", ex.getMessage());
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.name());
        error.setDescription(ex.getMessage());
        error.setInfo(ex.getMessage());
        Response response = new Response();
        response.setStatus("BAD_REQUEST");
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<Response> handleException(BusinessException ex) {
        log.error("Exception: ", ex.getMessage());
        Response response = new Response();
        response.setStatus("BAD_REQUEST");
        response.setError(ex.getError());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.name());
        error.setDescription(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        error.setInfo(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        Response response = new Response();
        response.setStatus("BAD_REQUEST");
        response.setError(error);
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
