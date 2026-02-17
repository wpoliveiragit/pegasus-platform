package br.com.pegasus.api.products.domain.adapter;

public interface TraceLoggerAdapter {
  void addTrace(String message, Object... args);
  void logInfo(Object obj);
}
