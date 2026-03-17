package br.com.pegasus.api.products.api.controller;

import br.com.pegasus.api.products.api.type.ProductCreateRequestType;
import br.com.pegasus.api.products.api.type.ProductPageResponseType;
import br.com.pegasus.api.products.api.type.ProductResponseType;
import br.com.pegasus.api.products.api.type.ProductUpdateRequestType;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import br.com.pegasus.api.products.infra.mapper.ProductMapper;
import br.com.pegasus.api.products.infra.util.HttpUtil;
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

import java.util.Map;

@Log4j2
@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
  private static final String CLASS_NAME = ProductsController.class.getSimpleName();

  private final ProductsServicePort productsService;

  @GetMapping({"/up", "/up/"})
  public ResponseEntity<Map<String, String>> up() {

    log.info("{}::up::params: void", CLASS_NAME);
    Map<String, String> reponse = Map.of("up", "OK");
    ResponseEntity<Map<String, String>> resp = HttpUtil.responseOK(reponse);
    log.info("{}::up::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @GetMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ProductResponseType> getOne(
      @PathVariable(value = "id") @Positive Long id) {

    log.info("{}::getOne::params: id:{}", CLASS_NAME, id);
    ProductModel outModel = productsService.getOne(new ProductModel(id));
    ProductResponseType response = ProductMapper.toType(outModel);
    ResponseEntity<ProductResponseType> resp = HttpUtil.responseOK(response);
    log.info("{}::getOne::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @GetMapping({"", "/"})
  public ResponseEntity<ProductPageResponseType> getAll(
      @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
      @RequestParam(value = "size", defaultValue = "0") @Min(1) Integer size) {

    log.info("{}::getAll::params: page:{} - size:{}", CLASS_NAME, page, size);
    ProductPageModel outModel = productsService.getAll(new PaginationModel(page, size));
    ProductPageResponseType response = ProductMapper.toType(outModel);
    ResponseEntity<ProductPageResponseType> resp = HttpUtil.responseOK(response);
    log.info("{}::getAll::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @PostMapping({"", "/"})
  public ResponseEntity<ProductResponseType> create(
      @Valid @RequestBody ProductCreateRequestType body) {

    log.info("{}::create::params: body:{}", CLASS_NAME, body);
    ProductModel outModel = productsService.create(ProductMapper.toModel(body));
    ProductResponseType response = ProductMapper.toType(outModel);
    ResponseEntity<ProductResponseType> resp = HttpUtil.responseCreate(response);
    log.info("{}::create::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @PutMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ProductResponseType> update(
      @PathVariable(value = "id") @Positive Long id,
      @Valid @RequestBody ProductUpdateRequestType body) {

    log.info("{}::update::params: id:{} - body:{}", CLASS_NAME, id, body);
    ProductModel outModel = productsService.update(ProductMapper.toModel(id, body));
    ProductResponseType response = ProductMapper.toType(outModel);
    ResponseEntity<ProductResponseType> resp = HttpUtil.responseOK(response);
    log.info("{}::update::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  public void delete(
      @PathVariable(value = "id") Long id) {

    log.info("{}::delete::params: id:{}", CLASS_NAME, id);
    productsService.delete(new ProductModel(id));
    log.info("{}::delete::response: void", CLASS_NAME);
  }

}
