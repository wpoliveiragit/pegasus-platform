package br.com.pegasus.api.products.domain.service;

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

  private static class Const {
    private static final String CLASS_NAME = ProductsService.class.getSimpleName();

    private static final String LOG_GET_ONE_PARAMS = CLASS_NAME + "::getOne::params: {}";
    private static final String LOG_GET_ONE_RESPONSE = CLASS_NAME + "::getOne::response: {}";

    private static final String LOG_GET_ALL_PARAMS = CLASS_NAME + "::getAll::params: {}";
    private static final String LOG_GET_ALL_RESPONSE = CLASS_NAME + "::getAll::response: {}";

    private static final String LOG_CREATE_PARAMS = CLASS_NAME + "::create::params: {}";
    private static final String LOG_CREATE_RESPONSE = CLASS_NAME + "::create::response: {}";

    private static final String LOG_UPDATE_PARAMS = CLASS_NAME + "::update::params: {}";
    private static final String LOG_UPDATE_RESPONSE = CLASS_NAME + "::update::response: {}";

    private static final String LOG_DELETE_PARAMS = CLASS_NAME + "::delete::params: {}";
    private static final String LOG_DELETE_RESPONSE = CLASS_NAME + "::delete::response: VOID";
  }

  private final ProductsServicePort core;

  public ProductsService(ToolBoxAdapter toolBox) {
    core = new ProductsServiceCore(toolBox);
  }

  @Override
  @Transactional(readOnly = true) //dependencia, spring data (não permite nenhum tipo de update no banco)
  public ProductModel getOne(ProductModel inModel) {
    log.info(Const.LOG_GET_ONE_PARAMS, inModel);
    ProductModel resp = core.getOne(inModel);
    log.info(Const.LOG_GET_ONE_RESPONSE, resp);
    return resp;
  }

  @Override
  @Transactional(readOnly = true)
  public ProductPageModel getAll(PaginationModel inModel) {
    log.info(Const.LOG_GET_ALL_PARAMS, inModel);
    ProductPageModel resp = core.getAll(inModel);
    log.info(Const.LOG_GET_ALL_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info(Const.LOG_CREATE_PARAMS, inModel);
    ProductModel resp = core.create(inModel);
    log.info(Const.LOG_CREATE_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.info(Const.LOG_UPDATE_PARAMS, inModel);
    ProductModel resp = core.update(inModel);
    log.info(Const.LOG_UPDATE_RESPONSE, resp);
    return resp;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info(Const.LOG_DELETE_PARAMS, inModel);
    core.delete(inModel);
    log.info(Const.LOG_DELETE_RESPONSE);
  }

}
