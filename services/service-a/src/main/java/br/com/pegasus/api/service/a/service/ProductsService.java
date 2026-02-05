package br.com.pegasus.api.service.a.service;

import br.com.pegasus.api.service.a.model.PaginationModel;
import br.com.pegasus.api.service.a.model.ProductModel;
import br.com.pegasus.api.service.a.model.ProductPageModel;

public interface ProductsService {

  ProductModel getOne(ProductModel inModel);

  ProductPageModel getAll(PaginationModel inModel);

  ProductModel create(ProductModel inModel);

  ProductModel update(ProductModel inModel);

  void delete(ProductModel inModel);

}
