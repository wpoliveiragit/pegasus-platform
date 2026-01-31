package br.com.pegasus.portal.app.controller;

import br.com.pegasus.portal.app.type.ProductRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class SitelController {

  private static final String SERVICE_A_API = "http://gateway:8080/service-a/hello";
  private final RestTemplate restTemplate = new RestTemplate();

  /* ========= HOME ========= */
  @GetMapping
  public String home() {
    return "index";
  }

  @GetMapping("/call-service")
  public String callServiceA(RedirectAttributes redirectAttributes) {
    System.out.println("pegasus-portal:in");
    String response = restTemplate.getForObject(SERVICE_A_API + "/hello", String.class);
    redirectAttributes.addFlashAttribute("response", response);
    return "redirect:/";
  }



}
