package br.com.pegasus.api.products.api.controller;

import br.com.pegasus.api.products.api.type.ResponseType;
import br.com.pegasus.api.products.api.type.product.ProductCreateRequestType;
import br.com.pegasus.api.products.api.type.product.ProductPageResponseType;
import br.com.pegasus.api.products.api.type.product.ProductResponseType;
import br.com.pegasus.api.products.api.type.product.ProductUpdateRequestType;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import br.com.pegasus.api.products.infra.logger.AppLogger;
import br.com.pegasus.api.products.infra.mapper.ProductMapper;
import br.com.pegasus.api.products.infra.util.HttpUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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

import java.net.URI;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

  private final AppLogger log = new AppLogger(ProductsController.class);
  private final ProductsServicePort productsService;

  @GetMapping({"/up", "/up/"})
  public ResponseEntity<ResponseType> up() {
    log.infoPattern("up", "params: VOID");
    Map<String, String> respType = Map.of("up", "OK");
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.infoPattern("up", "response: {}", resp.getBody());
    return HttpUtil.responseOk(respType);
  }

  @GetMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ResponseType> getOne(
      @PathVariable(value = "id") @Positive Long id) {

    log.infoPattern("getOne", "params: id:{}", id);
    ProductModel respModel = productsService.getOne(ProductModel.builder().id(id).build());
    ProductResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.infoPattern("getOne", "response: {}", resp.getBody());
    return resp;
  }

  @GetMapping({"", "/"})
  public ResponseEntity<ResponseType> getAll(
      @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
      @RequestParam(value = "size", defaultValue = "0") @Min(1) Integer size) {

    log.infoPattern("getAll", "params: page:{}, size:{}", page, size);
    ProductPageModel respModel = productsService.getAll(new PaginationModel(page, size));
    ProductPageResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.infoPattern("getAll", "response: {}", resp.getBody());
    return resp;
  }

  @PostMapping({"", "/"})
  public ResponseEntity<ResponseType> create(
      @Valid @RequestBody ProductCreateRequestType body) {

    log.infoPattern("create", "params: body:{}", body);
    ProductModel respModel = productsService.create(ProductMapper.toModel(body));
    ProductResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseCreate(respType, URI.create("/product/" + respType.getId()));
    log.infoPattern("create", "response: {}", resp.getBody());
    return resp;
  }

  @PutMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ResponseType> update(
      @PathVariable(value = "id") @Positive Long id,
      @Valid @RequestBody ProductUpdateRequestType body) {

    log.infoPattern("update", "params: id:{}, body:{}", id, body);
    ProductModel respModel = productsService.update(ProductMapper.toModel(id, body));
    ProductResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.infoPattern("update", "response: {}", resp.getBody());
    return resp;
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  public void delete(
      @PathVariable(value = "id") Long id) {

    log.infoPattern("delete", "params: id:{}", id);
    productsService.delete(ProductModel.builder().id(id).build());
    log.infoPattern("delete", "response: VOID");
  }

}
