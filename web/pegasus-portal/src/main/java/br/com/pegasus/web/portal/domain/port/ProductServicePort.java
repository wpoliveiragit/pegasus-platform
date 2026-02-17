package br.com.pegasus.web.portal.domain.port;

import br.com.pegasus.web.portal.domain.model.PageModel;
import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.domain.type.ProductPageResponseType;
import br.com.pegasus.web.portal.domain.type.ProductResponseType;

public interface ProductServicePort {
  ProductPageResponseType getAll(PageModel model);
  ProductResponseType getOne(ProductModel model);
  ProductResponseType create(ProductModel model);
  ProductResponseType update(ProductModel model);
  void delete(ProductModel model);
}
