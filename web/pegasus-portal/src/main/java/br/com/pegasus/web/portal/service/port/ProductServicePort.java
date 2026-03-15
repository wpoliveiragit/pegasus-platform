package br.com.pegasus.web.portal.service.port;

import br.com.pegasus.web.portal.model.ProductRequestModel;
import br.com.pegasus.web.portal.type.ProductResponseModel;

public interface ProductServicePort {
  ProductResponseModel findById(ProductRequestModel model);
}
