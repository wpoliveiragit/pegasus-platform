package br.com.pegasus.api.products.controller;

import br.com.pegasus.api.products.mapper.ProductMapper;
import br.com.pegasus.api.products.model.PaginationModel;
import br.com.pegasus.api.products.model.ProductModel;
import br.com.pegasus.api.products.service.ProductsService;
import br.com.pegasus.api.products.type.ProductCreateRequestType;
import br.com.pegasus.api.products.type.ProductPageResponseType;
import br.com.pegasus.api.products.type.ProductResponseType;
import br.com.pegasus.api.products.type.ProductUpdateRequestType;
import br.com.pegasus.api.products.util.HttpUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ServiceAController {

  private final ProductsService productsService;

  @GetMapping({"/up", "/up/"})
  public String up() {
    log.info("up");
    return "{ up : OK }";
  }

  @GetMapping({"/{id}","/{id}/"})
  public ResponseEntity<ProductResponseType> getOne(
      @PathVariable(value = "id") @Positive Long id) {

    ProductResponseType response = ProductMapper.toType(productsService.getOne(new ProductModel(id)));
    log.info("controller::get-one: {}", response);
    return HttpUtil.responseOK(response);
  }

  @GetMapping({"", "/"})
  public ResponseEntity<ProductPageResponseType> getAll(
      @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
      @RequestParam(value = "size", defaultValue = "0") @Min(1) Integer size) {

    ProductPageResponseType response = ProductMapper.toType(productsService.getAll(new PaginationModel(page, size)));
    log.info("controller::get-all: {}", response);
    return HttpUtil.responseOK(response);
  }

  @PostMapping({"", "/"})
  public ResponseEntity<ProductResponseType> create(
      @Valid @RequestBody ProductCreateRequestType body) {

    ProductResponseType response = ProductMapper.toType(productsService.create(ProductMapper.toModel(body)));
    log.info("controller::create: {}", response);
    return HttpUtil.responseCreate(response);
  }

  @PutMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ProductResponseType> update(
      @PathVariable(value = "id") @Positive Long id,
      @Valid @RequestBody ProductUpdateRequestType body) {

    ProductResponseType response = ProductMapper.toType(productsService.update(ProductMapper.toModel(id, body)));
    log.info("controller::update: {}", response);
    return HttpUtil.responseOK(response);
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  public void delete(
      @PathVariable(value = "id") Long id) {

    productsService.delete(new ProductModel(id));
    log.info("delete ok");
  }

}
