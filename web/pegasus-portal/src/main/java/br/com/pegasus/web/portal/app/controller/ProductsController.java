package br.com.pegasus.web.portal.app.controller;

import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.domain.port.ProductServicePort;
import br.com.pegasus.web.portal.domain.type.ProductCreateRequestType;
import br.com.pegasus.web.portal.domain.type.ProductUpdateRequestType;
import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductsController {

  private static final String BASE_URL = "http://gateway:8080/pegasus-api-products/products";
  private static final String URL_UP = BASE_URL + "/up";
  private static final String URL_ID = BASE_URL + "/{id}";
  private static final String URL_PAGE_SIZE = BASE_URL + "?page={page}&size={size}";

  private static final String responsePath = "redirect:/";

  private final RestTemplate restTemplate = new RestTemplate();
  private final List<ProductModel> products = new ArrayList<>();
  private final AtomicLong idGenerator = new AtomicLong(1);
  private final ProductServicePort productService;

  @GetMapping("/call-service")
  public String callService(RedirectAttributes model) {
    try {
      log.info("request to callService: URL: {}", URL_UP);
      String response = restTemplate.getForObject(URL_UP, String.class);
      log.info("response to callService: {}", response);
      model.addFlashAttribute("response", MethodUtil.toPrettyJson(response));
    } catch (Exception ex) {
      log.warn("callService request fail: {}", ex.getMessage());
      model.addFlashAttribute("response", ex.getMessage());
    }
    return responsePath;
  }

  @GetMapping("/find-by-id")
  public String findById(RedirectAttributes model,//
                         @RequestParam(value = "id") Long id) {
    try {
      log.info("request to findById: URL: {} - id: {}", URL_ID, id);
      ResponseEntity<Object> response = restTemplate.getForEntity(URL_ID, Object.class, id);
      String json = MethodUtil.toPrettyJson(response.getBody());
      log.info("response to findById: {}", json);
      model.addFlashAttribute("selectedProduct", json);
    } catch (Exception ex) {
      log.warn("fail request to findById: {}", ex.getMessage());
      model.addFlashAttribute("selectedProduct", ex.getMessage());
    }
    return responsePath;
  }

  @GetMapping("/find-all")
  public String findAll(RedirectAttributes model,//
                        @RequestParam(value = "page", defaultValue = "0") Integer page,//
                        @RequestParam(value = "size", defaultValue = "6") Integer size) {

    try {
      log.info("request to findAll: URL:{} - page:{} - size:{}", URL_PAGE_SIZE, page, size);
      ResponseEntity<Object> response = restTemplate.getForEntity(URL_PAGE_SIZE, Object.class, page, size);
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
      log.info("request to create: URL:{} - name:{} - price:{} - quantity:{}", BASE_URL, name, price, quantity);
      ProductCreateRequestType request = new ProductCreateRequestType(name, price, quantity);
      ResponseEntity<Object> response = restTemplate.postForEntity(BASE_URL, request, Object.class);
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
      log.info("request to update: URL:{} - id:{} - name:{} - price:{} - quantity:{}", URL_ID, id, name, price, quantity);
      ProductUpdateRequestType request = new ProductUpdateRequestType(name, price, quantity);
      ResponseEntity<Object> response = restTemplate.exchange(URL_ID, HttpMethod.PUT, new HttpEntity<>(request), Object.class, id);
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
      log.info("request to delete: URL:{} - id:{}", URL_ID, id);
      restTemplate.delete(URL_ID , id);
      log.info("response to delete: {}", "sucess");
      model.addFlashAttribute("deleteProduct", "Produto deletado");
    } catch (Exception ex) {
      log.warn("fail request to delete: {}", ex.getMessage());
      model.addFlashAttribute("deleteProduct", ex.getMessage());
    }
    return responsePath;
  }

}
