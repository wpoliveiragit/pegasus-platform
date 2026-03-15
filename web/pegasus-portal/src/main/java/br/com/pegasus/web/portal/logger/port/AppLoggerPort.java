package br.com.pegasus.web.portal.logger.port;

public interface AppLoggerPort {

  void info(String message, Object... params);
  void warn(String message, Object... params);
  void erro(String message, Object... params);
}
