package br.com.pegasus.api.products.infra.exception;

import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final TraceLoggerAdapter traceLog;

  public GlobalException(Throwable cause, HttpStatus httpStatus, TraceLoggerAdapter traceLog) {
    super(cause);
    this.httpStatus = httpStatus;
    this.traceLog = traceLog;
  }

  public GlobalException(String message, HttpStatus httpStatus, TraceLoggerAdapter traceLog) {
    super(message);
    this.httpStatus = httpStatus;
    this.traceLog = traceLog;
  }

  public static GlobalException internalServerError(TraceLoggerAdapter traceLog, Throwable cause ){
    return new GlobalException(cause, HttpStatus.INTERNAL_SERVER_ERROR, traceLog);
  }

}
