package br.com.pegasus.api.service.a.service.impl;

import br.com.pegasus.api.service.a.exception.NotFountApiException;
import br.com.pegasus.api.service.a.model.PaginationModel;
import br.com.pegasus.api.service.a.model.ProductModel;
import br.com.pegasus.api.service.a.model.ProductPageModel;
import br.com.pegasus.api.service.a.repository.RepositoryAdapter;
import br.com.pegasus.api.service.a.service.ProductsService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductsService {

  private final RepositoryAdapter repo;

  public ProductServiceImpl(RepositoryAdapter repo) {
    this.repo = repo;
  }

  //  @Transactional(readOnly = true) //dependencia, spring data (não permite nenhum tipo de update no banco)
  @Override
  public ProductModel getOne(ProductModel inModel) {
    return repo.findById(inModel).orElseThrow(() -> NotFountApiException.byId(inModel.getId()));
  }

  //  @Transactional(readOnly = true) //dependencia, spring data
  @Override
  public ProductPageModel getAll(PaginationModel inModel) {
    return repo.findAll(inModel);
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    return repo.save(inModel);
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    repo.findById(inModel).orElseThrow(() -> NotFountApiException.byId(inModel.getId()));
    return repo.save(inModel);
  }

  @Override
  public void delete(ProductModel inModel) {
    repo.delete(inModel);
  }

}
