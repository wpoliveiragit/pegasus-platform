package br.com.pegasus.web.portal.controller;

import br.com.pegasus.web.portal.logger.AppLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebController {

  private final static AppLogger log = new AppLogger(WebController.class);

  @GetMapping
  public String home() {
    log.infoPattern("home", "execute: VOID");
    return "index";
  }

  @GetMapping("/terms")
  public String terms() {
    log.infoPattern("terms", "execute: VOID");
    return "terms";
  }

  @GetMapping("/license")
  public String license() {
    log.infoPattern("license", "execute: VOID");
    return "license";
  }

}
