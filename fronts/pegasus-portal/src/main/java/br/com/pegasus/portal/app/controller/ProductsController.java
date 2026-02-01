package br.com.pegasus.portal.app.controller;

import br.com.pegasus.portal.app.type.ProductPageResponseType;
import br.com.pegasus.portal.app.type.PaginationResponse;
import br.com.pegasus.portal.app.type.ProductRequestType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/products")
public class ProductsController {

  private final List<ProductRequestType> products = new ArrayList<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  public ProductsController() {
    products.add(new ProductRequestType(idGenerator.getAndIncrement(), "Notebook", 3500f, 10));
    products.add(new ProductRequestType(idGenerator.getAndIncrement(), "Mouse", 120f, 50));
    products.add(new ProductRequestType(idGenerator.getAndIncrement(), "Teclado", 250f, 30));
  }

  /* ===== LIST ===== */
  @GetMapping
  public String list(
      Model model,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {

    int total = products.size();
    int pages = (int) Math.ceil((double) total / size);

    int from = Math.min(page * size, total);
    int to = Math.min(from + size, total);

    PaginationResponse pagination = new PaginationResponse();
    pagination.setPage(page);
    pagination.setSize(size);
    pagination.setElements((long) total);
    pagination.setPages(pages);
    pagination.setPrevious(page > 0);
    pagination.setNext(page < pages - 1);

    model.addAttribute(
        "pageResponse",
        new ProductPageResponseType(pagination, products.subList(from, to))
    );

    return "products";
  }

  /* ===== SEARCH ===== */
  @GetMapping("/search")
  public String search(@RequestParam(value = "id") Long id, Model model) {

    if(id != null){
    products.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .ifPresent(p -> model.addAttribute("selectedProduct", p));

    }

    // reaproveita a listagem
    return list(model, 0, 10);
  }

  /* ===== CREATE ===== */
  @PostMapping("/create")
  public String create(@RequestParam(value = "name") String name,
                       @RequestParam(value = "price") Float price,
                       @RequestParam(value = "quantity") Integer quantity) {

    products.add(new ProductRequestType(
        idGenerator.getAndIncrement(), name, price, quantity));

    return "redirect:/products";
  }

  /* ===== UPDATE ===== */
  @PostMapping("/update")
  public String update(@RequestParam(value = "id") Long id,
                       @RequestParam(value = "name") String name,
                       @RequestParam(value = "price") Float price,
                       @RequestParam(value = "quantity") Integer quantity) {

    products.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .ifPresent(p -> {
          p.setName(name);
          p.setPrice(price);
          p.setQuantity(quantity);
        });

    return "redirect:/products/search?id=" + id;
  }

  /* ===== DELETE ===== */
  @PostMapping("/delete")
  public String delete(@RequestParam(value = "id") Long id) {
    products.removeIf(p -> p.getId().equals(id));
    return "redirect:/products";
  }
}
