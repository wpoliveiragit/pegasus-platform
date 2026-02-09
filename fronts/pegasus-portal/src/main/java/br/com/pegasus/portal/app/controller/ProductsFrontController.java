package br.com.pegasus.portal.app.controller;

import br.com.pegasus.portal.app.type.PaginationType;
import br.com.pegasus.portal.app.type.ProductCreateRequestType;
import br.com.pegasus.portal.app.type.ProductPageResponseType;
import br.com.pegasus.portal.app.type.ProductType;
import br.com.pegasus.portal.app.type.ProductUpdateRequestType;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/products")
public class ProductsFrontController {

  private static final String baseUrl = "http://gateway:8080/pegasus-api-products/products";

  private final List<ProductType> products = new ArrayList<>();
  private final AtomicLong idGenerator = new AtomicLong(1);
  private final RestTemplate restTemplate;

  public ProductsFrontController(RestTemplateBuilder builder) {
    restTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(5))   // timeout conexão
        .setReadTimeout(Duration.ofSeconds(5))      // timeout leitura
        .build();

    Random random = new Random(1);
    IntStream.rangeClosed(1, 20)
        .forEach(i ->
            products.add(
                new ProductType(
                    idGenerator.getAndIncrement(),
                    "Item" + i,
                    random.nextFloat(10000),
                    random.nextInt(1000)
                )
            )
        );

    for (int i = 0; i < 20; i++) {
      products.add(new ProductType(idGenerator.getAndIncrement(), "Item" + i, 3500f, 10));
    }
  }

  @GetMapping
  public String list(
      Model model,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "6") Integer size) {

    model.addAttribute("pageResponse", this.getAllApi(page, size));
    return "products";
  }

  /* ===== SEARCH ===== */
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
    products.add(new ProductType(idGenerator.getAndIncrement(), name, price, quantity));

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

  private ProductPageResponseType getAllApi(Integer page, Integer size) {
    try {
      ProductPageResponseType response = restTemplate.getForEntity(
          baseUrl + "?page={page}&size={size}",
          ProductPageResponseType.class,
          page,
          size
      ).getBody();
      System.out.println("getAllApi::ok");
      return response;
    } catch (HttpClientErrorException | HttpServerErrorException ex) {
      System.out.println("getAllApi::fail");
      System.out.println("getAllApi::" + ex.getMessage());
      return new ProductPageResponseType(
          new PaginationType(1, 0, 0L, 0, false, false),
          new ArrayList<>()
      );
    }
  }

  private ResponseEntity<ProductType> getOneApi(Long id) {
    ResponseEntity<ProductType> response = restTemplate.getForEntity(//
        baseUrl + "/{id}", ProductType.class, id);
    System.out.println("get-one: " + response.getBody());
    return response;
  }

  private ResponseEntity<ProductType> createApi(String name, Float price, Integer quantity) {
    ProductCreateRequestType request = new ProductCreateRequestType();
    request.setName(name);
    request.setPrice(price);
    request.setQuantity(quantity);

    ResponseEntity<ProductType> response = restTemplate.postForEntity(//
        baseUrl, new HttpEntity<>(request), ProductType.class);
    System.out.println("create: " + response.getBody());
    return response;
  }

  private ResponseEntity<ProductType> updateApi(Long id, String name, Float price, Integer quantity) {
    ProductUpdateRequestType request = new ProductUpdateRequestType();
    request.setName(name);
    request.setPrice(price);
    request.setQuantity(quantity);

    ResponseEntity<ProductType> response = restTemplate.exchange(baseUrl + "/{id}",//
        HttpMethod.PUT, new HttpEntity<>(request), ProductType.class, id);
    System.out.println("update: " + response.getBody());
    return response;
  }

  private void deleteApi(Long id) {
    restTemplate.delete(baseUrl + "/{id}", id);
    System.out.println("Delete id: " + id);
  }

}
