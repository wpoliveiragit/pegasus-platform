package br.com.pegasus.api.products.domain.adapter;

import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;

import java.util.Optional;

public interface ProductsRepositoryAdapter {
  Optional<ProductModel> findById(ProductModel inModel);
  Optional<ProductModel> findByName(ProductModel inModel);
  ProductPageModel findAll(PaginationModel inModel);
  ProductModel save(ProductModel inModel);
  void deleteById(ProductModel inModel);
}
