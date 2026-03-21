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

import java.net.URI;
import java.util.Map;

@Log4j2
@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

  private static class Const {

    private static final String CLASS_NAME = ProductsController.class.getSimpleName();

    private static final String LOG_UP_PARAMS = CLASS_NAME + "::up::params: VOID";
    private static final String LOG_UP_RESPONSE = CLASS_NAME + "::up::response: {}";

    private static final String LOG_GET_ONE_PARAMS = CLASS_NAME + "::getOne::params: id:{}";
    private static final String LOG_GET_ONE_RESPONSE = CLASS_NAME + "::getOne::response: {}";

    private static final String LOG_GET_ALL_PARAMS = CLASS_NAME + "::getAll::params: page:{} - size:{}";
    private static final String LOG_GET_ALL_RESPONSE = CLASS_NAME + "::getAll::response: {}";

    private static final String LOG_CREATE_PARAMS = CLASS_NAME + "::create::params: body:{}";
    private static final String LOG_CREATE_RESPONSE = CLASS_NAME + "::create::response: {}";

    private static final String LOG_UPDATE_PARAMS = CLASS_NAME + "::update::params: id:{} - body:{}";
    private static final String LOG_UPDATE_RESPONSE = CLASS_NAME + "::update::response: {}";

    private static final String LOG_DELETE_PARAMS = CLASS_NAME + "::delete::params: id:{}";
    private static final String LOG_DELETE_RESPONSE = CLASS_NAME + "::delete::response: VOID";
  }

  private final ProductsServicePort productsService;

  @GetMapping({"/up", "/up/"})
  public ResponseEntity<ResponseType> up() {

    log.info(Const.LOG_UP_PARAMS);
    Map<String, String> respType = Map.of("up", "OK");
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.info(Const.LOG_UP_RESPONSE, resp.getBody());
    return HttpUtil.responseOk(respType);
  }

  @GetMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ResponseType> getOne(
      @PathVariable(value = "id") @Positive Long id) {

    log.info(Const.LOG_GET_ONE_PARAMS, id);
    ProductModel respModel = productsService.getOne(ProductModel.builder().id(id).build());
    ProductResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.info(Const.LOG_GET_ONE_RESPONSE, resp.getBody());
    return resp;
  }

  @GetMapping({"", "/"})
  public ResponseEntity<ResponseType> getAll(
      @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
      @RequestParam(value = "size", defaultValue = "0") @Min(1) Integer size) {

    log.info(Const.LOG_GET_ALL_PARAMS, page, size);
    ProductPageModel respModel = productsService.getAll(new PaginationModel(page, size));
    ProductPageResponseType respType = ProductMapper.toType(respModel);
    log.info(Const.LOG_GET_ALL_RESPONSE, respType);
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.info(Const.LOG_GET_ALL_RESPONSE, resp.getBody());
    return resp;
  }

  @PostMapping({"", "/"})
  public ResponseEntity<ResponseType> create(
      @Valid @RequestBody ProductCreateRequestType body) {

    log.info(Const.LOG_CREATE_PARAMS, body);
    ProductModel respModel = productsService.create(ProductMapper.toModel(body));
    ProductResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseCreate(respType, URI.create("/product/" + respType.getId()));
    log.info(Const.LOG_CREATE_RESPONSE, resp.getBody());
    return resp;
  }

  @PutMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ResponseType> update(
      @PathVariable(value = "id") @Positive Long id,
      @Valid @RequestBody ProductUpdateRequestType body) {

    log.info(Const.LOG_UPDATE_PARAMS, id, body);
    ProductModel respModel = productsService.update(ProductMapper.toModel(id, body));
    ProductResponseType respType = ProductMapper.toType(respModel);
    ResponseEntity<ResponseType> resp = HttpUtil.responseOk(respType);
    log.info(Const.LOG_UPDATE_RESPONSE, resp.getBody());
    return resp;
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  public void delete(
      @PathVariable(value = "id") Long id) {

    log.info(Const.LOG_DELETE_PARAMS, id);
    productsService.delete(ProductModel.builder().id(id).build());
    log.info(Const.LOG_DELETE_RESPONSE);
  }

}
