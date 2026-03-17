package br.com.pegasus.api.products.domain.adapter;

public interface TraceLoggerAdapter {

  void info(String message, Object... params);
}
