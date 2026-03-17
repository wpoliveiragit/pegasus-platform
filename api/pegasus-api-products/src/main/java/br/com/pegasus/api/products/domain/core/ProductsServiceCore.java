package br.com.pegasus.api.products.domain.core;

import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.exception.BusinessException;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
public class ProductsServiceCore implements ProductsServicePort {

  private static final String CLASS_NAME = ProductsServiceCore.class.getSimpleName();
  private final ProductsRepositoryAdapter repo;

  public ProductsServiceCore(ToolBoxAdapter toolBox) {
    this.repo = toolBox.getProductsRepository();
  }

  @Override
  public ProductModel getOne(ProductModel inModel) {
    log.info("{}::getOne::params: {}", CLASS_NAME, inModel);
    ProductModel resp = repo.findById(inModel).orElseThrow(() -> notFoundId(inModel.getId()));
    log.info("{}::getOne::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductPageModel getAll(PaginationModel inModel) {
    log.info("{}::getAll::params: {}", CLASS_NAME, inModel);
    ProductPageModel resp = repo.findAll(inModel);
    log.info("{}::getAll::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info("{}::create::params: {}", CLASS_NAME, inModel);
    checkName(inModel);
    ProductModel resp = repo.save(inModel);
    log.info("{}::create::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.info("{}::update::params: {}", CLASS_NAME, inModel);
    ProductModel upModel = getOne(inModel);
    if (!upModel.getName().equals(inModel.getName())) {
      checkName(inModel);
    }
    ProductModel resp = repo.save(inModel);
    log.info("{}::update::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info("{}::delete::params: {}", CLASS_NAME, inModel);
    repo.deleteById(inModel);
    log.info("{}::delete::response: void", CLASS_NAME);
  }

  /**
   * Verifica se o nome existe no banco de dados, caso propsitivo uma exception de conflito será disparada.
   *
   * @param inModel o modelo com o nome a ser verificado
   */
  private void checkName(ProductModel inModel) {
    log.info("{}::checkName::params: {}", CLASS_NAME, inModel);
    repo.findByName(inModel).ifPresent(e -> conflictName(inModel.getName()));
    log.info("{}::checkName::response: void", CLASS_NAME);
  }

  private void conflictName(String name) {
    throw new BusinessException("Existing name '" + name + "'", HttpStatus.CONFLICT);
  }

  private BusinessException notFoundId(Long id) {
    return new BusinessException("Product Not Found by id=" + id, HttpStatus.NOT_FOUND);
  }

}
