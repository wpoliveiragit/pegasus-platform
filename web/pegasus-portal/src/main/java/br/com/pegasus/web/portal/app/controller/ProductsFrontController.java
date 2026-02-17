package br.com.pegasus.web.portal.app.controller;

import br.com.pegasus.web.portal.domain.model.PageModel;
import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.domain.port.ProductServicePort;
import br.com.pegasus.web.portal.domain.type.ProductCreateRequestType;
import br.com.pegasus.web.portal.domain.type.ProductUpdateRequestType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsFrontController {

  private static final String baseUrl = "http://gateway:8080/pegasus-api-products/products";

  private final List<ProductModel> products = new ArrayList<>();
  private final AtomicLong idGenerator = new AtomicLong(1);
  private final RestTemplate restTemplate = new RestTemplate();
  private final ProductServicePort productService;

  @GetMapping
  public String list(
      Model model,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "6") Integer size) {

    var response = productService.getAll(new PageModel(page, size));
    //adicionar msg pelo status de retorno no site
    model.addAttribute("pageResponse", response);
    return "products";
  }

  @GetMapping("/search")
  public String search(
      Model model,
      @RequestParam(value = "id") Long id,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "6") Integer size) {

    this.getOneApi(id);

    products.stream()//
        .filter(p -> p.getId().equals(id))//
        .findFirst()//
        .ifPresent(p -> model.addAttribute("selectedProduct", p));


    productService.getOne(new ProductModel(id));

    // reaproveita a listagem SEM fixar paginação
    return list(model, page, size);
  }

  /* ===== CREATE ===== */
  @PostMapping("/create")
  public String create(
      @RequestParam(value = "name") String name,
      @RequestParam(value = "price") Float price,
      @RequestParam(value = "quantity") Integer quantity) {

    this.createApi(name, price, quantity);
    products.add(new ProductModel(idGenerator.getAndIncrement(), name, price, quantity));

    return "redirect:/products";
  }

  /* ===== UPDATE ===== */
  @PostMapping("/update")
  public String update(
      @RequestParam(value = "id") Long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "price") Float price,
      @RequestParam(value = "quantity") Integer quantity) {

    this.updateApi(id, name, price, quantity);

    products.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> {
      p.setName(name);
      p.setPrice(price);
      p.setQuantity(quantity);
    });

    return "redirect:/products/search?id=" + id + "&page=0&size=6";
  }

  /* ===== DELETE ===== */
  @PostMapping("/delete")
  public String delete(@RequestParam(value = "id") Long id) {
    deleteApi(id);
    products.removeIf(p -> p.getId().equals(id));

    return "redirect:/products";
  }

  private ResponseEntity<ProductModel> getOneApi(Long id) {
    ResponseEntity<ProductModel> response = restTemplate.getForEntity(//
        baseUrl + "/{id}", ProductModel.class, id);
    System.out.println("get-one: " + response.getBody());
    return response;
  }

  private ResponseEntity<ProductModel> createApi(String name, Float price, Integer quantity) {
    ProductCreateRequestType request = new ProductCreateRequestType();
    request.setName(name);
    request.setPrice(price);
    request.setQuantity(quantity);

    ResponseEntity<ProductModel> response = restTemplate.postForEntity(//
        baseUrl, new HttpEntity<>(request), ProductModel.class);
    System.out.println("create: " + response.getBody());
    return response;
  }

  private ResponseEntity<ProductModel> updateApi(Long id, String name, Float price, Integer quantity) {
    ProductUpdateRequestType request = new ProductUpdateRequestType();
    request.setName(name);
    request.setPrice(price);
    request.setQuantity(quantity);

    ResponseEntity<ProductModel> response = restTemplate.exchange(baseUrl + "/{id}",//
        HttpMethod.PUT, new HttpEntity<>(request), ProductModel.class, id);
    System.out.println("update: " + response.getBody());
    return response;
  }

  private void deleteApi(Long id) {
    restTemplate.delete(baseUrl + "/{id}", id);
    System.out.println("Delete id: " + id);
  }

}
