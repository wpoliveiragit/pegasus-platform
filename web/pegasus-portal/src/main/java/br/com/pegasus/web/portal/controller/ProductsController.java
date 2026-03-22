package br.com.pegasus.web.portal.controller;

import br.com.pegasus.web.portal.logger.AppLogger;
import br.com.pegasus.web.portal.model.ProductModel;
import br.com.pegasus.web.portal.model.RequestModel;
import br.com.pegasus.web.portal.service.port.ProductServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductsController {

  private final static AppLogger log = new AppLogger(ProductsController.class);
  private final ProductServicePort productService;

  public static final String responsePath = "redirect:/";

  @GetMapping("/call-service")
  public String callService(RedirectAttributes webModel) {
    log.infoPattern("callService", "params: VOID");
    webModel.addFlashAttribute("response", productService.callService(RequestModel.builder().build()));
    log.infoPattern("callService", "response: {}", webModel.getFlashAttributes());
    return responsePath;
  }

  @GetMapping("/find-by-id")
  public String findById(RedirectAttributes webModel,//
                         @RequestParam(value = "id") Long id) {

    log.infoPattern("findById", "params: id:{}", id, webModel);
    webModel.addFlashAttribute("selectedProduct", productService.findById(//
        RequestModel.builder().product(ProductModel.builder().id(id).build()).build()));
    log.infoPattern("findById", "response: {}", webModel.getFlashAttributes());
    return responsePath;
  }

  @GetMapping("/find-all")
  public String findAll(RedirectAttributes webModel,//
                        @RequestParam(value = "page", defaultValue = "0") Integer page,//
                        @RequestParam(value = "size", defaultValue = "6") Integer size) {

    log.infoPattern("findAll", "params: page:{}, size:{}", page, size);
    webModel.addFlashAttribute("pageResponse", productService.findAll(//
        RequestModel.builder().page(page).size(size).build()));
    log.infoPattern("findAll", "response: {}", webModel.getFlashAttributes());
    return responsePath;
  }

  @PostMapping("/create")
  public String create(RedirectAttributes webModel,//
                       @RequestParam(value = "name") String name,//
                       @RequestParam(value = "price") Float price,//
                       @RequestParam(value = "quantity") Integer quantity) {

    log.infoPattern("create", "params: name:{}, price:{}, quantity:{}", name, price, quantity);
    webModel.addFlashAttribute("createProduct", productService.create(RequestModel.builder()//
        .product(ProductModel.builder().name(name).price(price).quantity(quantity).build()).build()));
    log.infoPattern("create", "response: {}", webModel);
    return responsePath;
  }

  @PostMapping("/update")
  public String update(RedirectAttributes webModel,//
                       @RequestParam(value = "id") Long id,//
                       @RequestParam(value = "name") String name,//
                       @RequestParam(value = "price") Float price,//
                       @RequestParam(value = "quantity") Integer quantity) {

    log.infoPattern("update", "params: id:{}, name:{}, price:{}, quantity:{}", id, name, price, quantity);
    webModel.addFlashAttribute("updateProduct", productService.update(RequestModel.builder()//
        .product(ProductModel.builder().id(id).name(name).price(price).quantity(quantity).build()).build()));
    log.infoPattern("update", "response: {}", webModel);
    return responsePath;
  }

  @PostMapping("/delete")
  public String delete(RedirectAttributes webModel,//
                       @RequestParam(value = "id") Long id) {

    log.infoPattern("delete", "params: id:{}", id);
    webModel.addFlashAttribute("deleteProduct", productService.delete(//
        RequestModel.builder().product(ProductModel.builder().id(id).build()).build()));
    log.infoPattern("delete", "response: {}", webModel);
    return responsePath;
  }

}
