package br.com.pegasus.api.products.domain.adapter;

public interface GlobalExceptionAdapter {
  RuntimeException conflictName(TraceLoggerAdapter traceLog, String name);
  RuntimeException notFoundId(TraceLoggerAdapter traceLog, Long id);
}
