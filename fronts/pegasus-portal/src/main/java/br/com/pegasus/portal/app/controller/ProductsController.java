package br.com.pegasus.portal.app.controller;

import br.com.pegasus.portal.app.type.ProductRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

  private final List<ProductRequest> products = new ArrayList<>();

  // simulação de dados
  public ProductsController() {
    products.add(new ProductRequest("Notebook", 3500f, 10));
    products.add(new ProductRequest("Mouse", 120f, 50));
    products.add(new ProductRequest("Teclado", 250f, 30));
  }

  /* ===== LIST ===== */
  @GetMapping
  public String list(Model model) {
    model.addAttribute("products", products);
    return "products";
  }

  /* ===== CREATE ===== */
  @PostMapping("/create")
  public String create(
      @RequestParam("name") String name,
      @RequestParam("price") Float price,
      @RequestParam("quantity") Integer quantity) {

    products.add(new ProductRequest(name, price, quantity));
    return "redirect:/products";
  }

  /* ===== DELETE (por índice) ===== */
  @PostMapping("/delete")
  public String delete(@RequestParam("index") int index) {
    if (index >= 0 && index < products.size()) {
      products.remove(index);
    }
    return "redirect:/products";
  }
}
