package br.com.pegasus.api.service.a.repository;

import br.com.pegasus.api.service.a.model.PaginationModel;
import br.com.pegasus.api.service.a.model.ProductModel;
import br.com.pegasus.api.service.a.model.ProductPageModel;

import java.util.Optional;

public interface RepositoryAdapter {
  Optional<ProductModel> findById(ProductModel inModel);
  ProductPageModel findAll(PaginationModel inModel);
  ProductModel save(ProductModel inModel);
  void delete(ProductModel inModel);

}
