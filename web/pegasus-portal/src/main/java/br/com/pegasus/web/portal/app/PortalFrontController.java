package br.com.pegasus.web.portal.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class PortalFrontController {

  private static final String SERVICE_A_API = "http://gateway:8080/service-a/hello";
  private final RestTemplate restTemplate = new RestTemplate();

  /* ========= HOME ========= */
  @GetMapping
  public String home() {
    return "index";
  }

  @GetMapping("/terms")
  public String terms() {
    return "terms";
  }

  @GetMapping("/license")
  public String license() {
    return "license";
  }

  }
