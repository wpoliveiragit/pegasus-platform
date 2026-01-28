package br.com.pegasus.api.service.b;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {

  @GetMapping("/hello")
  public String hello() {
    System.out.println("in service-b");
    return "Resposta Service-b: OK";
  }

}
