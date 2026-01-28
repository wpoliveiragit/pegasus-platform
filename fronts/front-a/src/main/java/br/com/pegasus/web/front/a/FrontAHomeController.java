package br.com.pegasus.web.front.a;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/front-a")
public class FrontAHomeController {

  private final RestTemplate restTemplate = new RestTemplate();

  @GetMapping
  public String home(Model model) {
    return "index";
  }

  @GetMapping("/call-service")
  public String callServiceA(RedirectAttributes redirectAttributes) {
    System.out.println("front-a:in");
    String response = restTemplate.getForObject("http://gateway:8080/service-a/hello", String.class);
    redirectAttributes.addFlashAttribute("response", response);
    return "redirect:/front-a";
  }

}
