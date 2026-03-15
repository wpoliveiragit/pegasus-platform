package br.com.pegasus.web.portal.controller;

import br.com.pegasus.web.portal.logger.AppLogger;
import br.com.pegasus.web.portal.logger.port.AppLoggerPort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebController {
  private static final AppLoggerPort log = new AppLogger();
  private static final String WEB_HOME =  "index";
  private static final String WEB_TERMS =  "terms";
  private static final String WEB_LICENSE =  "license";

  @GetMapping
  public String home() {
    log.info("AppLoggerPort::home::execute");
    return WEB_HOME;
  }

  @GetMapping("/terms")
  public String terms() {
    log.info("AppLoggerPort::terms::execute");
    return WEB_TERMS;
  }

  @GetMapping("/license")
  public String license() {
    log.info("AppLoggerPort::license::execute");
    return WEB_LICENSE;
  }

}
