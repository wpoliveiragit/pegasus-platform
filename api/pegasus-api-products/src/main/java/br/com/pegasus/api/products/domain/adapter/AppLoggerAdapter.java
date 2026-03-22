package br.com.pegasus.api.products.domain.adapter;

public interface AppLoggerAdapter {
  void info(String message, Object... params);
  void warn(String message, Object... params);
  void error(String message, Object... params);
  void infoPattern(String methodName, String patternParams, Object... args);
}
