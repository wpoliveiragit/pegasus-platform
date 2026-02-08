package br.com.pegasus.api.products.repository.adapter;

import br.com.pegasus.api.products.model.PaginationModel;
import br.com.pegasus.api.products.model.ProductModel;
import br.com.pegasus.api.products.model.ProductPageModel;

import java.util.Optional;

public interface ProductRepositoryAdapter {
  Optional<ProductModel> findById(ProductModel inModel);
  Optional<ProductModel> findByName(ProductModel inModel);
  ProductPageModel findAll(PaginationModel inModel);
  ProductModel save(ProductModel inModel);
  void deleteById(ProductModel inModel);
}
