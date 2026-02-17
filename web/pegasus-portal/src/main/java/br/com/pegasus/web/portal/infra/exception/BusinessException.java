package br.com.pegasus.web.portal.infra.exception;

import org.springframework.web.ErrorResponse;

public class BusinessException extends RuntimeException {

  public BusinessException(Throwable ex) {
    super(ex);
    this.error = error;
  }

  public ErrorResponse getError() {
    return error;
  }
}
