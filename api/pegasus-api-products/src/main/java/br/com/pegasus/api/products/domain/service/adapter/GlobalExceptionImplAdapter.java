package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.GlobalExceptionAdapter;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.infra.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionImplAdapter implements GlobalExceptionAdapter {

  @Override
  public RuntimeException conflictName(TraceLoggerAdapter traceLog, String name) {
    return new GlobalException("Existing name '" + name + "'", HttpStatus.CONFLICT, traceLog);
  }

  @Override
  public RuntimeException notFoundId(TraceLoggerAdapter traceLog, Long id) {
    return new GlobalException("Product Not Found by id=" + id, HttpStatus.NOT_FOUND, traceLog);
  }
}
