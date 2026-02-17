package br.com.pegasus.api.products.api.handler;

import br.com.pegasus.api.products.api.controller.ProductsController;
import br.com.pegasus.api.products.api.type.ExceptionResponseType;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.infra.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceHandler {

  private static final String NAME_CLASS = RestControllerAdviceHandler.class.getSimpleName();

  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ExceptionResponseType> globalException(GlobalException ex) {
    HttpStatus httpStatus = ex.getHttpStatus();
    String detail = ex.getMessage();

    //cria o objeto de retorno
    var type = new ExceptionResponseType();
    type.setCode(httpStatus.value());
    type.setMessage(httpStatus.getReasonPhrase());
    type.setDetail(detail);
    var response = new ResponseEntity<>(type, httpStatus);

    TraceLoggerAdapter traceLog = ex.getTraceLog();
    traceLog.addTrace(NAME_CLASS + "::globalException: " + type);
    if (HttpStatus.INTERNAL_SERVER_ERROR.value() == httpStatus.value()) {
      log.error(traceLog);
      type.setDetail(type.getMessage());
    } else {
      log.warn(traceLog);
    }
    return response;
  }

}