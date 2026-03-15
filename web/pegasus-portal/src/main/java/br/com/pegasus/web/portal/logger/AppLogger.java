package br.com.pegasus.web.portal.logger;

import br.com.pegasus.web.portal.logger.port.AppLoggerPort;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AppLogger implements AppLoggerPort {

  @Override
  public void info(String message, Object... params) {
    log.info(message, params);
  }

  @Override
  public void warn(String message, Object... params) {
    log.warn(message, params);
  }

  @Override
  public void erro(String message, Object... params) {
    log.error(message, params);
  }
}
