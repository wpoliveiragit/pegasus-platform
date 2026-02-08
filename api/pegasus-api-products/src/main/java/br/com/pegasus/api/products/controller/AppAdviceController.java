package br.com.pegasus.api.products.controller;

import br.com.pegasus.api.products.exception.AppException;
import br.com.pegasus.api.products.type.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class AppAdviceController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseType> error(Exception ex) {
    log.error(ex.getMessage());
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    return createResponse(httpStatus, httpStatus.getReasonPhrase());
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ExceptionResponseType> warning(AppException ex) {
    log.warn(ex.getMessage());
    return createResponse(ex.getHttpStatus(), ex.getMessage());
  }

  public ResponseEntity<ExceptionResponseType> createResponse(HttpStatus httpStatus, String detail) {
    ExceptionResponseType response = ExceptionResponseType.builder()//
        .code(httpStatus.value())//
        .message(httpStatus.getReasonPhrase())//
        .detail(detail)//
        .build();
    return new ResponseEntity<>(response, httpStatus);
  }

}