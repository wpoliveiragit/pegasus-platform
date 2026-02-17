package br.com.pegasus.api.products.domain.adapter;

import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;

import java.util.Optional;

public interface ProductsRepositoryAdapter {
  Optional<ProductModel> findById(TraceLoggerAdapter traceLog, ProductModel inModel);
  Optional<ProductModel> findByName(TraceLoggerAdapter traceLog, ProductModel inModel);
  ProductPageModel findAll(TraceLoggerAdapter traceLog, PaginationModel inModel);
  ProductModel save(TraceLoggerAdapter traceLog, ProductModel inModel);
  void deleteById(TraceLoggerAdapter traceLog, ProductModel inModel);
}
