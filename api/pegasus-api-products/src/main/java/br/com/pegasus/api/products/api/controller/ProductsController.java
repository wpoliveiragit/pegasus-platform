package br.com.pegasus.api.products.api.controller;

import br.com.pegasus.api.products.api.type.ProductCreateRequestType;
import br.com.pegasus.api.products.api.type.ProductPageResponseType;
import br.com.pegasus.api.products.api.type.ProductResponseType;
import br.com.pegasus.api.products.api.type.ProductUpdateRequestType;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import br.com.pegasus.api.products.infra.logger.FactoryLogger;
import br.com.pegasus.api.products.infra.mapper.ProductMapper;
import br.com.pegasus.api.products.infra.util.HttpUtil;
import br.com.pegasus.api.products.infra.util.MethodUtil;
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
public class ProductsController {

  private static final String NAME_CLASS = ProductsController.class.getSimpleName();

  private final ProductsServicePort productsService;
  private final FactoryLogger factoryLogger;

  @GetMapping({"/up", "/up/"})
  public String up() {
    log.info("up");
    return "{ \"up\" : \"OK\" }";
  }

  @GetMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ProductResponseType> getOne(
      @PathVariable(value = "id") @Positive Long id) {

    ProductModel inModel = new ProductModel(id);
    TraceLoggerAdapter traceLog = factoryLogger.create();
    traceLog.addTrace("{}::get-one::in: {}", NAME_CLASS, inModel);

    ResponseEntity<ProductResponseType> resp = MethodUtil.callWithTrace(traceLog, () -> {
      ProductModel outModel = productsService.getOne(traceLog, inModel);
      ProductResponseType response = ProductMapper.toType(outModel);
      return HttpUtil.responseOK(response);
    });

    traceLog.addTrace("{}::create::out: {}", NAME_CLASS, resp.getBody());
    traceLog.logInfo(traceLog);
    return resp;
  }

  @GetMapping({"", "/"})
  public ResponseEntity<ProductPageResponseType> getAll(
      @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
      @RequestParam(value = "size", defaultValue = "0") @Min(1) Integer size) {

    PaginationModel inModel = new PaginationModel(page, size);
    TraceLoggerAdapter traceLog = factoryLogger.create();
    traceLog.addTrace("{}::get-all::in: {}", NAME_CLASS, inModel);

    ResponseEntity<ProductPageResponseType> resp = MethodUtil.callWithTrace(traceLog, () -> {
      ProductPageModel outModel = productsService.getAll(traceLog, inModel);
      ProductPageResponseType response = ProductMapper.toType(outModel);
      return HttpUtil.responseOK(response);
    });

    traceLog.addTrace("{}::get-all::out: {}", NAME_CLASS, resp.getBody());
    traceLog.logInfo(traceLog);
    return resp;
  }

  @PostMapping({"", "/"})
  public ResponseEntity<ProductResponseType> create(
      @Valid @RequestBody ProductCreateRequestType body) {

    TraceLoggerAdapter traceLog = factoryLogger.create();
    traceLog.addTrace("{}::create::in: {}", NAME_CLASS, body);

    ResponseEntity<ProductResponseType> resp = MethodUtil.callWithTrace(traceLog, () -> {
      ProductModel outModel = productsService.create(traceLog, ProductMapper.toModel(body));
      ProductResponseType response = ProductMapper.toType(outModel);
      return HttpUtil.responseCreate(response);
    });

    traceLog.addTrace("{}::create::out: {}", NAME_CLASS, resp.getBody());
    traceLog.logInfo(traceLog);
    return resp;
  }

  @PutMapping({"/{id}", "/{id}/"})
  public ResponseEntity<ProductResponseType> update(
      @PathVariable(value = "id") @Positive Long id,
      @Valid @RequestBody ProductUpdateRequestType body) {

    TraceLoggerAdapter traceLog = factoryLogger.create();
    traceLog.addTrace("{}::create::in: {}", NAME_CLASS, body);

    ResponseEntity<ProductResponseType> resp = MethodUtil.callWithTrace(traceLog, () -> {
      ProductModel outModel = productsService.update(traceLog, ProductMapper.toModel(id, body));
      ProductResponseType response = ProductMapper.toType(outModel);
      return HttpUtil.responseOK(response);
    });
    traceLog.addTrace("{}::create::out: {}", NAME_CLASS, resp.getBody());
    traceLog.logInfo(traceLog);
    return resp;
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  public void delete(
      @PathVariable(value = "id") Long id) {

    ProductModel inModel = new ProductModel(id);
    TraceLoggerAdapter traceLog = factoryLogger.create();
    traceLog.addTrace("{}::delete::in: {}", NAME_CLASS, inModel);
    MethodUtil.runWithTrace(traceLog, () -> productsService.delete(traceLog, new ProductModel(id)));
    traceLog.addTrace("{}::delete::out: void", NAME_CLASS);
    traceLog.logInfo(traceLog);
  }

}
