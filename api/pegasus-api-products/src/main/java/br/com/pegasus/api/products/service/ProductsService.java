package br.com.pegasus.api.products.service;

import br.com.pegasus.api.products.model.PaginationModel;
import br.com.pegasus.api.products.model.ProductModel;
import br.com.pegasus.api.products.model.ProductPageModel;

public interface ProductsService {

  ProductModel getOne(ProductModel inModel);

  ProductPageModel getAll(PaginationModel inModel);

  ProductModel create(ProductModel inModel);

  ProductModel update(ProductModel inModel);

  void delete(ProductModel inModel);

}
