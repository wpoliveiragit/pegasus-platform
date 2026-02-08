package br.com.pegasus.api.products.service.impl;

import br.com.pegasus.api.products.exception.AppException;
import br.com.pegasus.api.products.exception.ConflictApiException;
import br.com.pegasus.api.products.exception.NotFountApiException;
import br.com.pegasus.api.products.model.PaginationModel;
import br.com.pegasus.api.products.model.ProductModel;
import br.com.pegasus.api.products.model.ProductPageModel;
import br.com.pegasus.api.products.repository.adapter.ProductRepositoryAdapter;
import br.com.pegasus.api.products.service.ProductsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ProductServiceImpl implements ProductsService {

  private final ProductRepositoryAdapter repo;

  public ProductServiceImpl(ProductRepositoryAdapter repo) {
    this.repo = repo;
  }

  @Transactional(readOnly = true) //dependencia, spring data (não permite nenhum tipo de update no banco)
  @Override
  public ProductModel getOne(ProductModel inModel) {
    return repo.findById(inModel).map(e -> {
      log.info("service::get-one: {}", e);
      return e;
    }).orElseThrow(() -> AppException.notFoundId(inModel.getId()));
  }

  @Transactional(readOnly = true)
  @Override
  public ProductPageModel getAll(PaginationModel inModel) {
    ProductPageModel outModel = repo.findAll(inModel);
    log.info("service::get-all: {}", outModel);
    return outModel;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    checkName(inModel);
    ProductModel outModel = repo.save(inModel);
    log.info("service::create: {}", outModel);
    return outModel;
  }

  @Override
  public ProductModel update(ProductModel inModel) {

    //Valida o nome
    ProductModel upModel = repo.findById(inModel).orElseThrow(() -> AppException.notFoundId(inModel.getId()));
    if (!upModel.getName().equals(inModel.getName())) {
      checkName(inModel);
    }

    log.info("service::update: item encontrado");
    ProductModel outModel = repo.save(inModel);
    log.info("service::update: {}", outModel);
    return outModel;
  }

  @Override
  public void delete(ProductModel inModel) {
    repo.deleteById(inModel);
    log.info("service::delete: item deletado");
  }

  /**
   * Verifica se o nome existe no banco de dados, caso propsitivo uma exception de conflito será disparada.
   *
   * @param inModel o modelo com o nome a ser verificado
   */
  private void checkName(ProductModel inModel) {
    repo.findByName(inModel).ifPresent(e -> {
      throw AppException.conflictName(inModel.getName());
    });
  }

}
