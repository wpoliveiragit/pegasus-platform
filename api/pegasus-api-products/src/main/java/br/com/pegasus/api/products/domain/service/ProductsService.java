package br.com.pegasus.api.products.domain.service;

import br.com.pegasus.api.products.api.controller.ProductsController;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.domain.core.ProductsServiceCore;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import br.com.pegasus.api.products.domain.service.adapter.ToolBoxImplAdapter;
import org.springframework.stereotype.Service;

@Service
public class ProductsService implements ProductsServicePort {

  private static final String NAME_CLASS = ProductsController.class.getSimpleName();
  private final ProductsServicePort productsCore;

  public ProductsService(ToolBoxImplAdapter toolBox) {
    productsCore = new ProductsServiceCore(toolBox);
  }

  @Override
  public ProductModel getOne(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::get-one::in: {}", NAME_CLASS, model);
    ProductModel resp = productsCore.getOne(traceLog, model);
    traceLog.addTrace("{}::get-one::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductPageModel getAll(TraceLoggerAdapter traceLog, PaginationModel model) {
    traceLog.addTrace("{}::get-all::in: {}", NAME_CLASS, model);
    ProductPageModel resp = productsCore.getAll(traceLog, model);
    traceLog.addTrace("{}::get-all::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductModel create(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::create::in: {}", NAME_CLASS, model);
    ProductModel resp = productsCore.create(traceLog, model);
    traceLog.addTrace("{}::create::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductModel update(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::create::in: {}", NAME_CLASS, model);
    ProductModel resp = productsCore.update(traceLog, model);
    traceLog.addTrace("{}::create::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public void delete(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::create::in: {}", NAME_CLASS, model);
    productsCore.delete(traceLog, model);
    traceLog.addTrace("{}::create::out: void", NAME_CLASS);
  }

}
