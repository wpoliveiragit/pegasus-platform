package br.com.pegasus.api.products.domain.port;

import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;

public interface ProductsServicePort {
  ProductModel getOne(TraceLoggerAdapter traceLog, ProductModel model);
  ProductPageModel getAll(TraceLoggerAdapter traceLog, PaginationModel model);
  ProductModel create(TraceLoggerAdapter traceLog, ProductModel model);
  ProductModel update(TraceLoggerAdapter traceLog, ProductModel model);
  void delete(TraceLoggerAdapter traceLog, ProductModel model);
}
