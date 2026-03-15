package br.com.pegasus.web.portal.controller;

import br.com.pegasus.web.portal.logger.AppLogger;
import br.com.pegasus.web.portal.logger.port.AppLoggerPort;
import br.com.pegasus.web.portal.model.ProductRequestModel;
import br.com.pegasus.web.portal.service.ConstService;
import br.com.pegasus.web.portal.service.port.ProductServicePort;
import br.com.pegasus.web.portal.type.ProductCreateRequestType;
import br.com.pegasus.web.portal.type.ProductUpdateRequestType;
import br.com.pegasus.web.portal.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductsController {

  private static final AppLoggerPort log = new AppLogger();
  private static final String responsePath = "redirect:/";

  private final RestTemplate restTemplate = new RestTemplate();
  private final ProductServicePort productService;

  @GetMapping("/call-service")
  public String callService(RedirectAttributes webModel) {
    try {
      log.info("request to callService: URL: {}", ConstService.URL_UP);
      String response = restTemplate.getForObject(ConstService.URL_UP, String.class);
      log.info("response to callService: {}", response);
      webModel.addFlashAttribute("response", MethodUtil.toPrettyJson(response));
    } catch (Exception ex) {
      log.warn("callService request fail: {}", ex.getMessage());
      webModel.addFlashAttribute("response", ex.getMessage());
    }
    return responsePath;
  }

  @GetMapping("/find-by-id")
  public String findById(RedirectAttributes webModel,//
                         @RequestParam(value = "id") Long id) {
    log.info("ProductsController::findById::params: id: {} - webModel: {}", id, webModel);
    var inModel = new ProductRequestModel(id);
    webModel.addFlashAttribute("selectedProduct", productService.findById(inModel));
    log.info("ProductsController::findById::response: webModel: {}", webModel);
    return responsePath;
  }

  @GetMapping("/find-all")
  public String findAll(RedirectAttributes model,//
                        @RequestParam(value = "page", defaultValue = "0") Integer page,//
                        @RequestParam(value = "size", defaultValue = "6") Integer size) {
    try {
      log.info("request to findAll: URL:{} - page:{} - size:{}", ConstService.URL_PAGE_SIZE, page, size);
      ResponseEntity<Object> response = restTemplate.getForEntity(ConstService.URL_PAGE_SIZE, Object.class, page, size);
      String json = MethodUtil.toPrettyJson(response.getBody());
      model.addFlashAttribute("pageResponse", json);
      log.info("response to findAll: {}", json);
    } catch (Exception ex) {
      log.warn("fail request to findAll: {}", ex.getMessage());
      model.addFlashAttribute("pageResponse", ex.getMessage());
    }
    return responsePath;
  }

  @PostMapping("/create")
  public String create(RedirectAttributes model,//
                       @RequestParam(value = "name") String name,//
                       @RequestParam(value = "price") Float price,//
                       @RequestParam(value = "quantity") Integer quantity) {
    try {
      log.info("request to create: URL:{} - name:{} - price:{} - quantity:{}", ConstService.BASE_URL, name, price, quantity);
      ProductCreateRequestType request = new ProductCreateRequestType(name, price, quantity);
      ResponseEntity<Object> response = restTemplate.postForEntity(ConstService.BASE_URL, request, Object.class);
      String json = MethodUtil.toPrettyJson(response.getBody());
      log.info("response to create: {}", json);
      model.addFlashAttribute("createProduct", json);
    } catch (Exception ex) {
      log.warn("fail request to create: {}", ex.getMessage());
      model.addFlashAttribute("createProduct", ex.getMessage());
    }
    return responsePath;
  }

  @PostMapping("/update")
  public String update(RedirectAttributes model,//
                       @RequestParam(value = "id") Long id,//
                       @RequestParam(value = "name") String name,//
                       @RequestParam(value = "price") Float price,//
                       @RequestParam(value = "quantity") Integer quantity) {
    try {
      log.info("request to update: URL:{} - id:{} - name:{} - price:{} - quantity:{}", ConstService.URL_ID, id, name, price, quantity);
      ProductUpdateRequestType request = new ProductUpdateRequestType(name, price, quantity);
      ResponseEntity<Object> response = restTemplate.exchange(ConstService.URL_ID, HttpMethod.PUT, new HttpEntity<>(request), Object.class, id);
      String json = MethodUtil.toPrettyJson(response.getBody());
      log.info("response to update: {}", json);
      model.addFlashAttribute("updateProduct", json);
    } catch (Exception ex) {
      log.info("fail request to update: {}", ex.getMessage());
      model.addFlashAttribute("updateProduct", ex.getMessage());
    }
    return responsePath;
  }

  @PostMapping("/delete")
  public String delete(RedirectAttributes model,//
                       @RequestParam(value = "id") Long id) {
    try {
      log.info("request to delete: URL:{} - id:{}", ConstService.URL_ID, id);
      restTemplate.delete(ConstService.URL_ID, id);
      log.info("response to delete: {}", "sucess");
      model.addFlashAttribute("deleteProduct", "Produto deletado");
    } catch (Exception ex) {
      log.warn("fail request to delete: {}", ex.getMessage());
      model.addFlashAttribute("deleteProduct", ex.getMessage());
    }
    return responsePath;
  }

}
