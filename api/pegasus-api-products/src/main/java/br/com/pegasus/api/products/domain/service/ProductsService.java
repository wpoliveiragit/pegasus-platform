package br.com.pegasus.api.products.domain.service;

import br.com.pegasus.api.products.api.controller.ProductsController;
import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.core.ProductsServiceCore;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ProductsService implements ProductsServicePort {

  private static final String CLASS_NAME = ProductsController.class.getSimpleName();
  private final ProductsServicePort core;

  public ProductsService(ToolBoxAdapter toolBox) {
    core = new ProductsServiceCore(toolBox);
  }

  @Override
  @Transactional(readOnly = true) //dependencia, spring data (não permite nenhum tipo de update no banco)
  public ProductModel getOne(ProductModel inModel) {
    log.info("{}::getOne::params: {}", CLASS_NAME, inModel);
    ProductModel resp = core.getOne(inModel);
    log.info("{}::getOne::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  @Transactional(readOnly = true)
  public ProductPageModel getAll(PaginationModel inModel) {
    log.info("{}::getAll::params: {}", CLASS_NAME, inModel);
    ProductPageModel resp = core.getAll(inModel);
    log.info("{}::getAll::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info("{}::create::params: {}", CLASS_NAME, inModel);
    ProductModel resp = core.create(inModel);
    log.info("{}::create::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.info("{}::update::params: {}", CLASS_NAME, inModel);
    ProductModel resp = core.update(inModel);
    log.info("{}::update::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info("{}::delete::params: {}", CLASS_NAME, inModel);
    core.delete(inModel);
    log.info("{}::delete::response: void", CLASS_NAME);
  }

}
