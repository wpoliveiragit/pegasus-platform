package br.com.pegasus.api.products.domain.service;

import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.core.ProductsServiceCore;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import br.com.pegasus.api.products.infra.logger.AppLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductsService implements ProductsServicePort {

  private final AppLogger log = new AppLogger(ProductsService.class);
  private final ProductsServicePort core;

  public ProductsService(ToolBoxAdapter toolBox) {
    core = new ProductsServiceCore(toolBox);
  }

  @Override
  @Transactional(readOnly = true) //dependencia, spring data (não permite nenhum tipo de update no banco)
  public ProductModel getOne(ProductModel inModel) {
    log.infoPattern("getOne","params: {}",inModel);
    ProductModel resp = core.getOne(inModel);
    log.infoPattern("getOne","response: {}", resp);
    return resp;
  }

  @Override
  @Transactional(readOnly = true)
  public ProductPageModel getAll(PaginationModel inModel) {
    log.infoPattern("getAll","params: {}",inModel);
    ProductPageModel resp = core.getAll(inModel);
    log.infoPattern("getAll","response: {}", resp);
    return resp;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.infoPattern("create","params: {}",inModel);
    ProductModel resp = core.create(inModel);
    log.infoPattern("create","response: {}", resp);
    return resp;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.infoPattern("update","params: {}",inModel);
    ProductModel resp = core.update(inModel);
    log.infoPattern("update","response: {}", resp);
    return resp;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.infoPattern("delete","params: {}",inModel);
    core.delete(inModel);
    log.infoPattern("getOne","response: VOID");
  }

}
