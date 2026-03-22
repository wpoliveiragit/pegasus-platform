package br.com.pegasus.api.products.domain.core;

import br.com.pegasus.api.products.domain.adapter.AppLoggerAdapter;
import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.exception.BusinessException;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;

public class ProductsServiceCore implements ProductsServicePort {

  private static class Const {
    private static final String CLASS_NAME = ProductsServiceCore.class.getSimpleName();
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

    private static final String LOG_CHECK_NAME_PARAMS = CLASS_NAME + "::checkName::params: {}";
    private static final String LOG_CHECK_NAME_RESPONSE = CLASS_NAME + "::checkName::response: VOID";
  }

  private final ProductsRepositoryAdapter repo;
  private final AppLoggerAdapter log;

  public ProductsServiceCore(ToolBoxAdapter toolBox) {
    this.repo = toolBox.getProductsRepository();
    this.log = toolBox.getAppLoggerAdapter(ProductsServiceCore.class);
  }

  @Override
  public ProductModel getOne(ProductModel inModel) {
    log.info(Const.LOG_GET_ONE_PARAMS, inModel);
    ProductModel resp = repo.findById(inModel)//
        .orElseThrow(() -> BusinessException.notFoundId(inModel.getId()));
    log.info(Const.LOG_GET_ONE_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductPageModel getAll(PaginationModel inModel) {
    log.info(Const.LOG_GET_ALL_PARAMS, inModel);
    ProductPageModel resp = repo.findAll(inModel);
    log.info(Const.LOG_GET_ALL_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info(Const.LOG_CREATE_PARAMS, inModel);
    checkName(inModel);
    ProductModel resp = repo.save(inModel);
    log.info(Const.LOG_CREATE_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.info(Const.LOG_UPDATE_PARAMS, inModel);
    ProductModel upModel = getOne(inModel);
    if (!upModel.getName().equals(inModel.getName())) {
      checkName(inModel);
    }
    ProductModel resp = repo.save(inModel);
    log.info(Const.LOG_UPDATE_RESPONSE, resp);
    return resp;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info(Const.LOG_DELETE_PARAMS, inModel);
    repo.deleteById(inModel);
    log.info(Const.LOG_DELETE_RESPONSE);
  }

  /**
   * Verifica se o nome existe no banco de dados, caso propsitivo uma exception de conflito será disparada.
   *
   * @param inModel o modelo com o nome a ser verificado
   */
  private void checkName(ProductModel inModel) {
    log.info(Const.LOG_CHECK_NAME_PARAMS, inModel);
    repo.findByName(inModel).ifPresent(e -> BusinessException.conflictName(inModel.getName()));
    log.info(Const.LOG_CHECK_NAME_RESPONSE);
  }

}
