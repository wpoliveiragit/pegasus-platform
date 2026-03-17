package br.com.pegasus.api.products.domain.port;

import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;

public interface ProductsServicePort {
  ProductModel getOne(ProductModel inModel);
  ProductPageModel getAll(PaginationModel inModel);
  ProductModel create(ProductModel inModel);
  ProductModel update(ProductModel inModel);
  void delete(ProductModel inModel);
}
