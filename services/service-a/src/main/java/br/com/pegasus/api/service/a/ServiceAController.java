package br.com.pegasus.api.service.a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAController {

  @GetMapping("/hello")
  public String hello() {
    System.out.println("in service-a");
    return "Resposta Service-a: OK";
  }

}
